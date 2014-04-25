package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import com.mycomp.execspec.jiraplugin.dto.story.input.SaveStoryDTO;
import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;

import java.util.List;

@Transactional
public interface StoryService {

    void create(SaveStoryDTO storyDTO);

    void update(SaveStoryDTO storyDTO);

    List<StoryDTO> all();

    StoryDTO findByProjectAndIssueKey(String projectKey, String issueKey,
                                      List<StepDocDTO> stepDocs);

    List<StoryDTO> findByProjectKey(String projectKey);

    StoryDTO findById(Long storyId);

    void delete(Long storyId);

    void delete(String projectKey, String issueKey);
}


