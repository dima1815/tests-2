package com.mycomp.execspec.jiraplugin.dto;

import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.TestStatus;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StoryReportDTOUtils {

    public static void fromDTOToModel(StoryHtmlReportDTO storyHtmlReportDTO, StoryHtmlReport storyHtmlReport) {

        storyHtmlReport.setStatus(storyHtmlReportDTO.getStatus().name());
        storyHtmlReport.setStoryVersion(storyHtmlReportDTO.getStoryVersion());
        storyHtmlReport.setEnvironment(storyHtmlReportDTO.getEnvironment());
        storyHtmlReport.setHtmlReport(storyHtmlReportDTO.getHtmlReport());
    }

    public static StoryHtmlReportDTO fromModelToDTO(StoryHtmlReport storyHtmlReport) {

        String environment = storyHtmlReport.getEnvironment();
        String storyPath = storyHtmlReport.getStory().getIssueKey();
        Long storyVersion = storyHtmlReport.getStoryVersion();
        TestStatus status = TestStatus.valueOf(storyHtmlReport.getStatus());
        String htmlReport = storyHtmlReport.getHtmlReport();
        StoryHtmlReportDTO storyReportDTO = new StoryHtmlReportDTO(environment, storyPath, storyVersion, status, htmlReport);
        return storyReportDTO;
    }

}
