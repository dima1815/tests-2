package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;

import java.util.List;

@Transactional
public interface StoryService {

    StoryDTO findByProjectAndIssueKey(String projectKey, String issueKey);

    List<StoryDTO> findByProjectKey(String projectKey);

    StoryDTO findById(int storyId);

    void delete(Long storyId);

    void delete(String projectKey, String issueKey);

    StoryDTO saveOrUpdate(StoryDTO storyDTO);
}


