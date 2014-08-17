package org.bitbucket.jbehaveforjira.plugin.service;

import com.atlassian.activeobjects.tx.Transactional;
import org.bitbucket.jbehaveforjira.plugin.dto.stepdoc.StepDocDTO;
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
