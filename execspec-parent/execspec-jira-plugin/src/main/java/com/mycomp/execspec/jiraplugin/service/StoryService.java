package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStory;

import java.util.List;

@Transactional
public interface StoryService {

    JiraStory findByProjectAndIssueKey(String projectKey, String issueKey);

    List<JiraStory> findByProjectKey(String projectKey);

    JiraStory findById(int storyId);

    void delete(Long storyId);

    void delete(String projectKey, String issueKey);

    JiraStory saveOrUpdate(JiraStory storyDTO);
}


