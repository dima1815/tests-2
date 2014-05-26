package com.mycomp.execspec.jiraplugin.service;

import com.mycomp.execspec.jiraplugin.ao.stepdoc.StepDoc;
import com.mycomp.execspec.jiraplugin.ao.stepdoc.StepDocDao;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTOUtils;
import org.apache.commons.lang.Validate;
import org.jbehave.core.steps.StepType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 4/23/2014.
 */
public class StepDocsServiceImpl implements StepDocsSerivce {

    private final StepDocDao stepDocDao;

    public StepDocsServiceImpl(StepDocDao stepDocDao) {
        this.stepDocDao = stepDocDao;
    }

    @Override
    public void createStepDocs(String projectKey, List<StepDocDTO> stepDocs) {

        Validate.notEmpty(projectKey);

        // replace all existing ones for the project
        List<StepDoc> allForProject = this.stepDocDao.findAllForProject(projectKey);
        for (StepDoc stepDoc : allForProject) {
            stepDocDao.delete(stepDoc);
        }

        for (StepDocDTO stepDocDTO : stepDocs) {
            StepDoc stepDoc = stepDocDao.create();
            stepDoc.setProjectKey(projectKey);
            StepDocDTOUtils.fromDTOToModel(stepDocDTO, stepDoc);
            String regExpPattern = stepDocDTO.getRegExpPattern();
            stepDoc.setRegExpPattern(regExpPattern);
            stepDoc.save();
        }

    }

    @Override
    public List<StepDocDTO> findForProject(String projectKey) {

        List<StepDoc> allForProject = this.stepDocDao.findAllForProject(projectKey);
        return toDTOs(allForProject);
    }

    @Override
    public List<StepDocDTO> findForProject(String projectKey, StepType stepType) {
        List<StepDoc> allForProject = this.stepDocDao.findAllForProject(projectKey, stepType);
        return toDTOs(allForProject);
    }

    private List<StepDocDTO> toDTOs(List<StepDoc> allForProject) {
        List<StepDocDTO> stepDocDTOs = new ArrayList<StepDocDTO>(allForProject.size());

        for (StepDoc stepDoc : allForProject) {
            StepDocDTO stepDocDTO = StepDocDTOUtils.fromModelToDTO(stepDoc);
            stepDocDTOs.add(stepDocDTO);
        }

        return stepDocDTOs;
    }

}
