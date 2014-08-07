package com.mycomp.execspec.jiraplugin.dto.storyreport;

import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
import org.bitbucket.jbehaveforjira.javaclient.util.TestStatus;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StoryReportDTOUtils {

    public static void fromDTOToModel(JiraStoryHtmlReport storyHtmlReportDTO, StoryHtmlReport storyHtmlReport) {

        storyHtmlReport.setStatus(storyHtmlReportDTO.getStatus().name());
        storyHtmlReport.setStoryVersion(storyHtmlReportDTO.getStoryVersion());
        storyHtmlReport.setEnvironment(storyHtmlReportDTO.getEnvironment());
        storyHtmlReport.setHtmlReport(storyHtmlReportDTO.getHtmlReport());

        storyHtmlReport.setTotalScenarios(storyHtmlReportDTO.getTotalScenarios());
        storyHtmlReport.setTotalScenariosPassed(storyHtmlReportDTO.getTotalScenariosPassed());
        storyHtmlReport.setTotalScenariosFailed(storyHtmlReportDTO.getTotalScenariosFailed());
        storyHtmlReport.setTotalScenariosPending(storyHtmlReportDTO.getTotalScenariosPending());
        storyHtmlReport.setTotalScenariosSkipped(storyHtmlReportDTO.getTotalScenariosSkipped());
        storyHtmlReport.setTotalScenariosNotPerformed(storyHtmlReportDTO.getTotalScenariosNotPerformed());

    }

    public static JiraStoryHtmlReport fromModelToDTO(StoryHtmlReport storyHtmlReport) {

        String environment = storyHtmlReport.getEnvironment();
        String storyPath = storyHtmlReport.getStory().getIssueKey();
        Long storyVersion = storyHtmlReport.getStoryVersion();
        TestStatus status = TestStatus.valueOf(storyHtmlReport.getStatus());
        String htmlReport = storyHtmlReport.getHtmlReport();
        JiraStoryHtmlReport storyReportDTO = new JiraStoryHtmlReport(environment, storyPath, storyVersion, status, htmlReport);

        storyReportDTO.setTotalScenarios(storyHtmlReport.getTotalScenarios());
        storyReportDTO.setTotalScenariosPassed(storyHtmlReport.getTotalScenariosPassed());
        storyReportDTO.setTotalScenariosFailed(storyHtmlReport.getTotalScenariosFailed());
        storyReportDTO.setTotalScenariosPending(storyHtmlReport.getTotalScenariosPending());
        storyReportDTO.setTotalScenariosSkipped(storyHtmlReport.getTotalScenariosSkipped());
        storyReportDTO.setTotalScenariosNotPerformed(storyHtmlReport.getTotalScenariosNotPerformed());

        return storyReportDTO;
    }

}
