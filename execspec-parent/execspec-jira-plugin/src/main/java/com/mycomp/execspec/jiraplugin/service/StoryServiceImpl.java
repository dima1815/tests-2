package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.ao.story.StoryDao;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReportDao;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import com.mycomp.execspec.jiraplugin.dto.story.StoryDTOUtils;
import com.mycomp.execspec.jiraplugin.dto.story.input.SaveStoryDTO;
import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class StoryServiceImpl implements StoryService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IssueService is;
    private final JiraAuthenticationContext authenticationContext;
    private final StoryDao storyDao;
    private final StoryReportDao storyReportDao;
    private final StepDocsSerivce stepDocsSerivce;

    public StoryServiceImpl(StoryDao storyDao, StoryReportDao storyReportDao,
                            IssueService is,
                            JiraAuthenticationContext authenticationContext,
                            StepDocsSerivce stepDocsSerivce) {
        this.storyDao = storyDao;
        this.storyReportDao = storyReportDao;
        this.is = is;
        this.authenticationContext = authenticationContext;
        this.stepDocsSerivce = stepDocsSerivce;
    }

    @Override
    public void create(SaveStoryDTO storyDTO) {
        String issueKey = storyDTO.getIssueKey();
        User user = authenticationContext.getLoggedInUser();
        IssueService.IssueResult issue = is.getIssue(user, issueKey);
        Validate.notNull(issue, "Could not find issue for key - " + issueKey);
        this.createStory(storyDTO, issue);
    }

    private void createStory(SaveStoryDTO storyDTO, IssueService.IssueResult issue) {

        final Story story = storyDao.create();
        story.setVersion(1L);
        story.setIssueKey(storyDTO.getIssueKey());
        story.setProjectKey(storyDTO.getProjectKey());
        story.setAsString(storyDTO.getAsString());
        story.save();
    }

    @Override
    public void update(SaveStoryDTO saveStoryDTO) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<StoryDTO> all() {
        List<Story> all = storyDao.findAll();
        List<StepDocDTO> stepDocs = Collections.emptyList(); // TODO - should not be empty here?
        List<StoryDTO> storyModels = StoryDTOUtils.toDTO(all, stepDocs);
        return storyModels;
    }

    @Override
    public StoryDTO findByProjectAndIssueKey(String projectKey, String issueKey, List<StepDocDTO> stepDocs) {
        List<Story> byIssueKey = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
        if (byIssueKey.isEmpty()) {
            return null;
        } else if (byIssueKey.size() > 1) {
            throw new RuntimeException("More than one story was found for issue key - " + issueKey);
        } else {
            Story story = byIssueKey.get(0);
            StoryDTO storyModel = StoryDTOUtils.toDTO(story, stepDocs);
            return storyModel;
        }
    }

    @Override
    public List<StoryDTO> findByProjectKey(String projectKey) {
        List<Story> stories = storyDao.findByProjectKey(projectKey);
        List<StepDocDTO> stepDocs = stepDocsSerivce.findForProject(projectKey);
        List<StoryDTO> storyDTOs = StoryDTOUtils.toDTO(stories, stepDocs);
        return storyDTOs;
    }

    @Override
    public StoryDTO findById(Long storyId) {
        // safe downcast here
        if (storyId > new Long(Integer.MAX_VALUE)) {
            throw new RuntimeException("Story id value is greater than allowed");
        }
        Story story = storyDao.get(storyId.intValue());
        List<StepDocDTO> stepDocs = stepDocsSerivce.findForProject(story.getProjectKey());
        StoryDTO storyModel = StoryDTOUtils.toDTO(story, stepDocs);
        return storyModel;
    }

    @Override
    public void delete(Long storyId) {
        Story story = storyDao.get(storyId.intValue());
        deleteStory(story);
    }

    @Override
    public void delete(String projectKey, String issueKey) {
        List<Story> stories = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
        Validate.isTrue(stories.size() == 1);
        Story story = stories.get(0);
        deleteStory(story);
    }

    private void deleteStory(Story story) {

        // delete story reports
        StoryHtmlReport[] storyTestReports = story.getStoryHtmlReports();
        for (StoryHtmlReport storyTestReport : storyTestReports) {
            storyReportDao.delete(storyTestReport);
        }

        storyDao.delete(story);
    }
}
