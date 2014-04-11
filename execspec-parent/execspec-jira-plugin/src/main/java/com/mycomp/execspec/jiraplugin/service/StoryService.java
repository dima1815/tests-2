package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import com.mycomp.execspec.jiraplugin.dto.story.in.SaveStoryDTO;
import com.mycomp.execspec.jiraplugin.dto.story.out.StoryDTO;

import java.util.List;

@Transactional
public interface StoryService {

    void create(SaveStoryDTO storyDTO);

    void update(SaveStoryDTO storyDTO);

    List<StoryDTO> all();

    StoryDTO findByProjectAndIssueKey(String projectKey, String issueKey);

    StoryDTO findById(Long storyId);

    void delete(Long storyId);

    void delete(String projectKey, String issueKey);
}


