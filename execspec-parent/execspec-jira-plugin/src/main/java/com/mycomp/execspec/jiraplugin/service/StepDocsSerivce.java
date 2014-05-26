package com.mycomp.execspec.jiraplugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import org.jbehave.core.steps.StepType;

import java.util.List;

/**
 * Created by Dmytro on 4/23/2014.
 */
@Transactional
public interface StepDocsSerivce {

    void createStepDocs(String projectKey, List<StepDocDTO> stepDocs);

    List<StepDocDTO> findForProject(String projectKey);

    List<StepDocDTO> findForProject(String projectKey, StepType stepType);
}
