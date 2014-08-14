package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.plugin.webresource.JiraWebResourceManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.webresource.WebResourceUrlProvider;
import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.ao.story.StoryDao;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReportDao;
import com.mycomp.execspec.jiraplugin.dto.story.StoryDTOUtils;
import org.apache.commons.lang.Validate;
import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    private JiraWebResourceManager jiraWebResourceManager;

    private WebResourceUrlProvider webResourceUrlProvider;

    public StoryServiceImpl(StoryDao storyDao, StoryReportDao storyReportDao,
                            IssueService is,
                            JiraAuthenticationContext authenticationContext,
                            StepDocsSerivce stepDocsSerivce,
                            StoryReportService storyReportService,
                            JiraWebResourceManager jiraWebResourceManager,
                            WebResourceUrlProvider webResourceUrlProvider) {
        this.storyDao = storyDao;
        this.storyReportDao = storyReportDao;
        this.is = is;
        this.authenticationContext = authenticationContext;
        this.stepDocsSerivce = stepDocsSerivce;
        this.storyReportService = storyReportService;
        this.jiraWebResourceManager = jiraWebResourceManager;
        this.webResourceUrlProvider = webResourceUrlProvider;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("groovy/test.groovy");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        try {
            line = br.readLine();
            while(line != null){
                System.out.println("line = " + line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    public synchronized JiraStory saveOrUpdate(JiraStory storyDTO) {

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

    private JiraStory create(JiraStory storyDTO, String userName) {


        final Story story = storyDao.create();
        story.setVersion(1L);
        story.setIssueKey(storyDTO.getIssueKey());
        story.setProjectKey(storyDTO.getProjectKey());
        story.setAsString(storyDTO.getAsString());
        story.setLastEditedBy(userName);
        story.save();

        JiraStory byId = this.findById(story.getID());
        return byId;
    }

    private JiraStory update(Story story, JiraStory storyDTO, String userName) {

        long currentVersion = story.getVersion();
        long rolledVersion = currentVersion + 1;
        story.setVersion(rolledVersion);
        story.setAsString(storyDTO.getAsString());

        story.setLastEditedBy(userName);
        story.save();

        JiraStory byId = this.findById(story.getID());
        return byId;
    }

    @Override
    public JiraStory findByProjectAndIssueKey(String projectKey, String issueKey) {

        List<Story> byIssueKey = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
        if (byIssueKey.isEmpty()) {
            return null;
        } else if (byIssueKey.size() > 1) {
            throw new RuntimeException("More than one story was found for issue key - " + issueKey);
        } else {

            Story story = byIssueKey.get(0);

            JiraStory storyDTO = StoryDTOUtils.toDTO(story);
            return storyDTO;
        }
    }

    @Override
    public List<JiraStory> findByProjectKey(String projectKey) {

        List<Story> stories = storyDao.findAll();

        List<JiraStory> storyDTOs = new ArrayList<JiraStory>(stories.size());
        for (Story story : stories) {
            JiraStory storyDTO = StoryDTOUtils.toDTO(story);
            storyDTOs.add(storyDTO);
        }

        return storyDTOs;
    }

    @Override
    public JiraStory findById(int storyId) {

        Story story = storyDao.get(storyId);

        JiraStory storyModel = StoryDTOUtils.toDTO(story);
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
