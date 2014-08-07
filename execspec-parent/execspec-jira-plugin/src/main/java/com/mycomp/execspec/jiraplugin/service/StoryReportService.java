package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStoryHtml;

import java.util.List;

/**
 * Created by Dmytro on 4/8/2014.
 */
@Transactional
public interface StoryReportService {

    void addStoryTestReport(String projectKey, String issueKey, JiraStoryHtml storyReportDTO);

    List<JiraStoryHtml> findStoryReports(String projectKey, String issueKey);

    void deleteForIssue(String projectKey, String issueKey);
}
