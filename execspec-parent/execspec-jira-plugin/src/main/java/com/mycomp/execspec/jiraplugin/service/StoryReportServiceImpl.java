package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.ao.story.StoryDao;
import com.mycomp.execspec.jiraplugin.ao.testreport.ScenarioReport;
import com.mycomp.execspec.jiraplugin.ao.testreport.StepReport;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReport;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReportDao;
import com.mycomp.execspec.jiraplugin.dto.StoryReportDTOUtils;
import com.mycomp.execspec.jiraplugin.dto.testreport.ScenarioReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.StepReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryReportDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StoryReportServiceImpl implements StoryReportService {

    private StoryReportDao storyReportDao;

    private StoryDao storyDao;

    public StoryReportServiceImpl(IssueService is,
                                  JiraAuthenticationContext authenticationContext,
                                  StoryDao storyDao,
                                  StoryReportDao storyReportDao) {
        this.storyDao = storyDao;
        this.storyReportDao = storyReportDao;
    }

    @Override
    public void addStoryTestReport(String projectKey, String issueKey, StoryReportDTO storyReportDTO) {
        List<Story> byIssueKey = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
        if (byIssueKey.isEmpty()) {
            throw new IllegalArgumentException("Failed to set story test status, " +
                    "issue with key " + issueKey + " for project key - " + projectKey + " could not be found.");
        } else if (byIssueKey.size() > 1) {
            throw new RuntimeException("More than one story was found for issue key - " + issueKey);
        } else {
            Story story = byIssueKey.get(0);

            // first we delete any storyReport that exists for the same environment as in the received reportDTO
            StoryReport[] storyTestReports = story.getStoryTestReports();
            String reportedEnvironment = storyReportDTO.getEnvironment();
            if (storyTestReports.length > 0) {
                // find one for the reportedEnvironment if exists and delete it since it will be replaced by the one received
                for (StoryReport testReport : storyTestReports) {
                    String storedEnv = testReport.getEnvironment();
                    if (storedEnv.equals(reportedEnvironment)) {
                        storyReportDao.delete(testReport);
                        break;
                    }
                }
            }

            // now we createStoryReport a new report for the reported environment
            StoryReport storyTestReport = storyReportDao.createStoryReport();
            storyTestReport.setStory(story);
            storyTestReport.setEnvironment(reportedEnvironment);
            Long storyVersion = storyReportDTO.getStoryVersion();
            storyTestReport.setStoryVersion(storyVersion);
            storyTestReport.setStatus(storyReportDTO.getStatus().name());

            // create scenario reports
            List<ScenarioReportDTO> scenarioTestReportModels = storyReportDTO.getScenarioTestReportDTOs();
            ScenarioReport[] scenarioReports = new ScenarioReport[scenarioTestReportModels.size()];
            for (int i = 0; i < scenarioTestReportModels.size(); i++) {
                ScenarioReportDTO scenarioTestReportDTO = scenarioTestReportModels.get(i);
                ScenarioReport scenarioReport = storyReportDao.createScenarioReport(storyTestReport);
                StoryReportDTOUtils.fromDTOToModel(scenarioTestReportDTO, scenarioReport);

                // create step reports
                List<StepReportDTO> stepTestReportModels = scenarioTestReportDTO.getStepTestReportModels();
                StepReport[] stepReports = new StepReport[stepTestReportModels.size()];
                for (int k = 0; k < stepTestReportModels.size(); k++) {
                    StepReportDTO stepReportDTO = stepTestReportModels.get(k);
                    StepReport stepReport = storyReportDao.createStepReport(scenarioReport);
                    StoryReportDTOUtils.fromDTOtoModel(stepReportDTO, stepReport);
                    stepReports[k] = stepReport;
                    stepReport.save();
                }

//                scenarioReport.setStepReports(stepReports);
                scenarioReports[i] = scenarioReport;
                scenarioReport.save();
            }

//            storyTestReport.setScenarioReports(scenarioReports);
            storyTestReport.save();
        }
    }

    @Override
    public List<StoryReportDTO> findStoryTestReports(String projectKey, String issueKey) {
        List<Story> byIssueKey = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
        if (byIssueKey.isEmpty()) {
            throw new IllegalArgumentException("Failed to set story test status, " +
                    "issue with key " + issueKey + " for project key - " + projectKey + " could not be found.");
        } else if (byIssueKey.size() > 1) {
            throw new RuntimeException("More than one story was found for issue key - " + issueKey);
        } else {
            Story story = byIssueKey.get(0);

            StoryReport[] storyTestReports = story.getStoryTestReports();
            if (storyTestReports.length == 0) {
                // createStoryReport a new report
                return Collections.emptyList();
            } else {
                List<StoryReportDTO> storyReportDTOs = new ArrayList<StoryReportDTO>(storyTestReports.length);
                for (StoryReport storyTestReport : storyTestReports) {
                    StoryReportDTO storyReportDTO = StoryReportDTOUtils.fromModelToDTO(storyTestReport);
                    storyReportDTOs.add(storyReportDTO);
                }
                return storyReportDTOs;
            }
        }
    }

    @Override
    public void deleteForIssue(String projectKey, String issueKey) {

        List<Story> byIssueKey = storyDao.findByProjectAndIssueKey(projectKey, issueKey);
        if (byIssueKey.isEmpty()) {
            throw new IllegalArgumentException("Failed to set story test status, " +
                    "issue with key " + issueKey + " for project key - " + projectKey + " could not be found.");
        } else if (byIssueKey.size() > 1) {
            throw new RuntimeException("More than one story was found for issue key - " + issueKey);
        } else {
            Story story = byIssueKey.get(0);
            StoryReport[] storyTestReports = story.getStoryTestReports();
            if (storyTestReports.length != 0) {
                for (StoryReport storyTestReport : storyTestReports) {
                    storyReportDao.delete(storyTestReport);
                }
            }
        }
    }
}
