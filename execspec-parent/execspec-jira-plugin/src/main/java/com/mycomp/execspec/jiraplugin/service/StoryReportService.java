package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryReportDTO;

import java.util.List;

/**
 * Created by Dmytro on 4/8/2014.
 */
@Transactional
public interface StoryReportService {

    void addStoryTestReport(String projectKey, String issueKey, StoryReportDTO storyReportDTO);

    List<StoryReportDTO> findStoryTestReports(String projectKey, String issueKey);

    void deleteForIssue(String projectKey, String issueKey);
}
