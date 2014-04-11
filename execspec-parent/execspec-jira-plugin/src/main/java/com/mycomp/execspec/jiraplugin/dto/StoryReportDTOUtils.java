package com.mycomp.execspec.jiraplugin.dto;

import com.mycomp.execspec.jiraplugin.ao.testreport.ScenarioReport;
import com.mycomp.execspec.jiraplugin.ao.testreport.StepReport;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReport;
import com.mycomp.execspec.jiraplugin.dto.testreport.ScenarioReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.StepReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.TestStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StoryReportDTOUtils {

    public static void fromDTOToModel(ScenarioReportDTO scenarioTestReportModel, ScenarioReport scenarioReport) {

        String title = scenarioTestReportModel.getTitle();
        scenarioReport.setScenarioTitle(title);
    }

    public static void fromDTOtoModel(StepReportDTO stepReportDTO, StepReport stepReport) {

        stepReport.setStep(stepReportDTO.getStepAsString());
        stepReport.setStatus(stepReportDTO.getStatus().name());
        stepReport.setFailureTrace(stepReportDTO.getFailureTrace());
    }

    public static StoryReportDTO fromModelToDTO(StoryReport storyTestReport) {

        String storyPath = storyTestReport.getStory().getProjectKey() + "/" + storyTestReport.getStory().getIssueKey();
        StoryReportDTO storyReportDTO = new StoryReportDTO(storyPath, storyTestReport.getEnvironment());
        Long storyVersion = storyTestReport.getStoryVersion();
        storyReportDTO.setStoryVersion(storyVersion);
        String status = storyTestReport.getStatus();
        TestStatus storyReportStatus = TestStatus.valueOf(status);

        storyReportDTO.setStatus(storyReportStatus);
        storyTestReport.setStoryVersion(storyTestReport.getStoryVersion());

        // set scenarios
        ScenarioReport[] scenarioTestReports = storyTestReport.getScenarioReports();
        List<ScenarioReportDTO> scenarioTestReportsDTOs = new ArrayList<ScenarioReportDTO>(scenarioTestReports.length);
        for (ScenarioReport scenarioTestReport : scenarioTestReports) {
            ScenarioReportDTO scenarioReportDTO = fromModelToDTO(scenarioTestReport);
            scenarioTestReportsDTOs.add(scenarioReportDTO);
        }
        storyReportDTO.setScenarioTestReportDTOs(scenarioTestReportsDTOs);

        return storyReportDTO;
    }

    private static ScenarioReportDTO fromModelToDTO(ScenarioReport scenarioTestReport) {

        ScenarioReportDTO scenarioReportDTO = new ScenarioReportDTO(scenarioTestReport.getScenarioTitle());
        String status = scenarioTestReport.getStatus();
        scenarioReportDTO.setStatus(TestStatus.valueOf(status));

        StepReport[] stepTestReports = scenarioTestReport.getStepReports();
        List<StepReportDTO> stepTestReportDTOs = new ArrayList<StepReportDTO>(stepTestReports.length);
        for (StepReport stepTestReport : stepTestReports) {
            StepReportDTO stepReportDTO = fromModelToDTO(stepTestReport);
            stepTestReportDTOs.add(stepReportDTO);
        }
        scenarioReportDTO.setStepTestReportDTOs(stepTestReportDTOs);

        return scenarioReportDTO;
    }

    private static StepReportDTO fromModelToDTO(StepReport stepTestReport) {

        String stepAsString = stepTestReport.getStep();
        TestStatus testStatus = TestStatus.valueOf(stepTestReport.getStatus());
        StepReportDTO stepReportDTO = new StepReportDTO(stepAsString, testStatus);

        String failureTrace = stepTestReport.getFailureTrace();
        stepReportDTO.setFailureTrace(failureTrace);

        return stepReportDTO;
    }
}
