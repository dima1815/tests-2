package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.mycomp.execspec.jiraplugin.ao.story.Scenario;
import com.mycomp.execspec.jiraplugin.ao.story.ScenarioDao;
import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.ao.story.StoryDao;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReportDao;
import com.mycomp.execspec.jiraplugin.dto.StoryDTOUtils;
import com.mycomp.execspec.jiraplugin.dto.story.in.SaveStoryDTO;
import com.mycomp.execspec.jiraplugin.dto.story.out.StoryDTO;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StoryServiceImpl implements StoryService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IssueService is;
    private final JiraAuthenticationContext authenticationContext;
    private StoryDao storyDao;
    private ScenarioDao scenarioDao;
    private StoryReportDao storyReportDao;

    public StoryServiceImpl(StoryDao storyDao, ScenarioDao scenarioDao,
                            StoryReportDao storyReportDao,
                            IssueService is,
                            JiraAuthenticationContext authenticationContext) {
        this.storyDao = storyDao;
        this.scenarioDao = scenarioDao;
        this.storyReportDao = storyReportDao;
        this.is = is;
        this.authenticationContext = authenticationContext;
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
        log.debug("$$$ Found issue is - " + issue);
        log.debug("$$$ Found issue key is - " + issue.getIssue().getKey());

        final Story story = storyDao.create();
        story.setVersion(1L);
        story.setNarrative(storyDTO.getNarrative());
        story.setIssueKey(storyDTO.getIssueKey());
        story.setProjectKey(storyDTO.getProjectKey());

        story.save();

        // save the scenarios too
        List<String> scenarios = storyDTO.getScenarios();
        for (String strScenario : scenarios) {
            Scenario scenario = scenarioDao.create();
            scenario.setText(strScenario);
            scenario.setStory(story);
            scenario.save();
        }

    }

    @Override
    public void update(SaveStoryDTO saveStoryDTO) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<StoryDTO> all() {
        List<Story> all = storyDao.findAll();
        List<StoryDTO> storyModels = StoryDTOUtils.toDTO(all);
        return storyModels;
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
            StoryDTO storyModel = StoryDTOUtils.toDTO(story);
            return storyModel;
        }
    }

    @Override
    public List<StoryDTO> findByProjectKey(String projectKey) {
        List<Story> stories = storyDao.findByProjectKey(projectKey);
        List<StoryDTO> storyDTOs = StoryDTOUtils.toDTO(stories);
        return storyDTOs;
    }

    @Override
    public StoryDTO findById(Long storyId) {
        // safe downcast here
        if (storyId > new Long(Integer.MAX_VALUE)) {
            throw new RuntimeException("Story id value is greater than allowed");
        }
        Story story = storyDao.get(storyId.intValue());
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

        // delete story scenarios
        Scenario[] scenarios = story.getScenarios();
        for (Scenario scenario : scenarios) {
            scenarioDao.delete(scenario);
        }

        storyDao.delete(story);
    }
}
