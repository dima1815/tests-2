package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.ao.story.StoryDao;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReportDao;
import com.mycomp.execspec.jiraplugin.dto.story.StoryDTOUtils;
import com.mycomp.execspec.jiraplugin.dto.story.StoryDTO;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StoryServiceImpl implements StoryService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IssueService is;
    private final JiraAuthenticationContext authenticationContext;
    private final StoryDao storyDao;
    private final StoryReportDao storyReportDao;
    private final StepDocsSerivce stepDocsSerivce;
    private StoryReportService storyReportService;

    public StoryServiceImpl(StoryDao storyDao, StoryReportDao storyReportDao,
                            IssueService is,
                            JiraAuthenticationContext authenticationContext,
                            StepDocsSerivce stepDocsSerivce,
                            StoryReportService storyReportService) {
        this.storyDao = storyDao;
        this.storyReportDao = storyReportDao;
        this.is = is;
        this.authenticationContext = authenticationContext;
        this.stepDocsSerivce = stepDocsSerivce;
        this.storyReportService = storyReportService;
    }

    @Override
    public synchronized StoryDTO saveOrUpdate(StoryDTO storyDTO) {

        String issueKey = storyDTO.getIssueKey();
        User user = authenticationContext.getLoggedInUser();
        IssueService.IssueResult issue = is.getIssue(user, issueKey);
        Validate.notNull(issue, "Could not find issue for key - " + issueKey);

        Long version = storyDTO.getVersion();
        if (version == null) {

            // create
            List<Story> byProjectAndIssueKey = storyDao.findByProjectAndIssueKey(storyDTO.getProjectKey(), storyDTO.getIssueKey());
            Validate.isTrue(byProjectAndIssueKey.isEmpty(), "Issue with key - " + storyDTO.getIssueKey() + " already has story attached");
            return this.create(storyDTO, user.getName());

        } else {

            // update
            List<Story> byProjectAndIssueKey = storyDao.findByProjectAndIssueKey(storyDTO.getProjectKey(), storyDTO.getIssueKey());
            Validate.isTrue(!byProjectAndIssueKey.isEmpty(), "Editing story failed - issue with key - " + storyDTO.getIssueKey() + " does not have any stories attached");
            Validate.isTrue(byProjectAndIssueKey.size() == 1);

            Story story = byProjectAndIssueKey.get(0);
            Long storedVersion = story.getVersion();
            Long receivedVersion = storyDTO.getVersion();
            if (!storedVersion.equals(receivedVersion)) {
                String lastEditedBy = story.getLastEditedBy();
                throw new RuntimeException("Story for issue - " + storyDTO.getIssueKey() + " has been modified by another user (" + lastEditedBy + ").");
            } else {
                return this.update(story, storyDTO, user.getName());
            }

        }
    }

    private StoryDTO create(StoryDTO storyDTO, String userName) {


        final Story story = storyDao.create();
        story.setVersion(1L);
        story.setIssueKey(storyDTO.getIssueKey());
        story.setProjectKey(storyDTO.getProjectKey());


        String asString = storyDTO.getAsString();
        if (asString != null) {
            // this will be the case when the client sends the story already as a string, e.g. from the
            // Raw editor rather than Rich editor
        } else {
            asString = StoryDTOUtils.asString(storyDTO);
        }
        story.setAsString(asString);

        story.setLastEditedBy(userName);
        story.save();

        StoryDTO byId = this.findById(story.getID());
        return byId;
    }

    private StoryDTO update(Story story, StoryDTO storyDTO, String userName) {

        long currentVersion = story.getVersion();
        long rolledVersion = currentVersion + 1;
        story.setVersion(rolledVersion);

        String asString = storyDTO.getAsString();
        if (asString != null) {
            // this will be the case when the client sends the story already as a string, e.g. from the
            // Raw editor rather than Rich editor
        } else {
            asString = StoryDTOUtils.asString(storyDTO);
        }
        story.setAsString(asString);

        story.setLastEditedBy(userName);
        story.save();

        StoryDTO byId = this.findById(story.getID());
        return byId;
    }

    @Override
    public StoryDTO findByProjectAndIssueKey(String projectKey, String issueKey) {

        List<Story> byIssueKey = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
        if (byIssueKey.isEmpty()) {
            return null;
        } else if (byIssueKey.size() > 1) {
            throw new RuntimeException("More than one story was found for issue key - " + issueKey);
        } else {

            Story story = byIssueKey.get(0);

            StoryDTO storyDTO = StoryDTOUtils.toDTO(story);
            return storyDTO;
        }
    }

    @Override
    public List<StoryDTO> findByProjectKey(String projectKey) {

        List<Story> stories = storyDao.findAll();

        List<StoryDTO> storyDTOs = new ArrayList<StoryDTO>(stories.size());
        for (Story story : stories) {
            StoryDTO storyDTO = StoryDTOUtils.toDTO(story);
            storyDTOs.add(storyDTO);
        }

        return storyDTOs;
    }

    @Override
    public StoryDTO findById(int storyId) {

        Story story = storyDao.get(storyId);

        StoryDTO storyModel = StoryDTOUtils.toDTO(story);
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
        for (Story story : stories) {
            deleteStory(story);
        }
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
