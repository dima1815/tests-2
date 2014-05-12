package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.mycomp.execspec.jiraplugin.ao.stepdoc.StepDoc;
import com.mycomp.execspec.jiraplugin.ao.stepdoc.StepDocDao;
import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.ao.story.StoryDao;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReportDao;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTOUtils;
import com.mycomp.execspec.jiraplugin.dto.story.StoryDTOUtils;
import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryReportDTOUtils;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StoryReportServiceImpl implements StoryReportService {

    private StoryReportDao storyReportDao;
    private StepDocDao stepDocDao;

    private StoryDao storyDao;

    public StoryReportServiceImpl(IssueService is,
                                  JiraAuthenticationContext authenticationContext,
                                  StoryDao storyDao,
                                  StoryReportDao storyReportDao,
                                  StepDocDao stepDocDao) {
        this.storyDao = storyDao;
        this.storyReportDao = storyReportDao;
        this.stepDocDao = stepDocDao;
    }

    public void addStoryTestReport(String projectKey, String issueKey, StoryHtmlReportDTO storyReportDTO) {

        List<Story> byIssueKey = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
        if (byIssueKey.isEmpty()) {
            throw new IllegalArgumentException("Failed to set story test status, " +
                    "issue with key " + issueKey + " for project key - " + projectKey + " could not be found.");
        } else if (byIssueKey.size() > 1) {
            throw new RuntimeException("More than one story was found for issue key - " + issueKey);
        } else {

            Story story = byIssueKey.get(0);

            // first we delete any storyReport that exists for the same environment as in the received reportDTO
            StoryHtmlReport[] storyHtmlReports = story.getStoryHtmlReports();
            String reportedEnvironment = storyReportDTO.getEnvironment();
            if (storyHtmlReports.length > 0) {
                // find one for the reportedEnvironment if exists and delete it since it will be replaced by the one received
                for (StoryHtmlReport testReport : storyHtmlReports) {
                    String storedEnv = testReport.getEnvironment();
                    if (storedEnv.equals(reportedEnvironment)) {
                        storyReportDao.delete(testReport);
                        break;
                    }
                }
            }

            // now we createStoryReport a new report for the reported environment
            StoryHtmlReport storyHtmlReport = storyReportDao.createStoryHtmlReport();
            storyHtmlReport.setStory(story);
            StoryReportDTOUtils.fromDTOToModel(storyReportDTO, storyHtmlReport);
            storyHtmlReport.save();
        }

    }

    @Override
    public List<StoryHtmlReportDTO> findStoryReports(String projectKey, String issueKey) {
        List<Story> byIssueKey = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
        if (byIssueKey.isEmpty()) {
            return Collections.emptyList();
        } else if (byIssueKey.size() > 1) {
            throw new RuntimeException("More than one story was found for issue key - " + issueKey);
        } else {
            Story story = byIssueKey.get(0);

            StoryHtmlReport[] storyTestReports = story.getStoryHtmlReports();
            if (storyTestReports.length == 0) {
                // createStoryReport a new report
                return Collections.emptyList();
            } else {
                List<StoryHtmlReportDTO> storyReportDTOs = new ArrayList<StoryHtmlReportDTO>(storyTestReports.length);
                for (StoryHtmlReport storyTestReport : storyTestReports) {
                    StoryHtmlReportDTO storyReportDTO = StoryReportDTOUtils.fromModelToDTO(storyTestReport);
                    storyReportDTOs.add(storyReportDTO);
                }
                return storyReportDTOs;
            }
        }
    }

    @Override
    public StoryDTO deleteForIssue(String projectKey, String issueKey) {

        List<Story> byIssueKey = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
        if (byIssueKey.isEmpty()) {
            throw new IllegalArgumentException("Failed to set story test status, " +
                    "issue with key " + issueKey + " for project key - " + projectKey + " could not be found.");
        } else if (byIssueKey.size() > 1) {
            throw new RuntimeException("More than one story was found for issue key - " + issueKey);
        } else {
            Story story = byIssueKey.get(0);
            StoryHtmlReport[] storyTestReports = story.getStoryHtmlReports();
            if (storyTestReports.length != 0) {
                for (StoryHtmlReport storyTestReport : storyTestReports) {
                    storyReportDao.delete(storyTestReport);
                }
            }
            byIssueKey = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
            Validate.isTrue(byIssueKey.size() == 1);
            story = byIssueKey.get(0);
            Validate.isTrue(story.getStoryHtmlReports().length == 0);
            List<StepDoc> stepDocs = stepDocDao.findAllForProject(projectKey);
            List<StepDocDTO> stepsForProject = StepDocDTOUtils.fromModelToDTO(stepDocs);
            StoryDTO storyDTO = StoryDTOUtils.toDTO(story, new ArrayList<StoryHtmlReportDTO>(0), stepsForProject);
            return storyDTO;
        }
    }
}
