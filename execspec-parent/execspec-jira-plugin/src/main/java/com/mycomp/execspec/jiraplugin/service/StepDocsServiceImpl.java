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

            // check if stepdoc has grouped pattern already set and if not calculate it and set it
            if (stepDocDTO.getGroupedRegExpPattern() == null) {
                setGroupedPatternOnStepDoc(stepDocDTO);
            }

            StepDoc stepDoc = stepDocDao.create();
            stepDoc.setProjectKey(projectKey);
            StepDocDTOUtils.fromDTOToModel(stepDocDTO, stepDoc);
            stepDoc.save();
        }

    }

    private void setGroupedPatternOnStepDoc(StepDocDTO stepDocDTO) {

        String resolvedPattern = stepDocDTO.getResolvedPattern();
        StringBuilder groupedPatternBuilder = new StringBuilder();
        List<Integer> parameterGroups = new ArrayList<Integer>();

        int pos = 0;
        int groupCount = 0;
        int indexOfOpenBrace = resolvedPattern.indexOf("(");
        while(indexOfOpenBrace != -1) {

            // we want to group everything before that opening brace
            groupedPatternBuilder.append("(");
            groupedPatternBuilder.append(resolvedPattern.substring(pos, indexOfOpenBrace));
            groupedPatternBuilder.append(")");
            groupCount++;

            int indexOfCloseBrace = resolvedPattern.indexOf(")", indexOfOpenBrace);
            Validate.isTrue(indexOfCloseBrace != -1, "Failed to find matching closing brace in pattern - " + resolvedPattern);
            pos = indexOfCloseBrace + 1;
            groupedPatternBuilder.append(resolvedPattern.substring(indexOfOpenBrace, pos));
            groupCount++;
            parameterGroups.add(groupCount);

            if (pos >= resolvedPattern.length()) {
                // we have reached the end of the pattern
                break;
            } else {
                indexOfOpenBrace = resolvedPattern.indexOf("(", pos);
                if (indexOfOpenBrace != -1) {
                    // we simply iterate again
                    continue;
                } else {
                    // there are no more parameter groups so we simply append any string into last group
                    groupedPatternBuilder.append("(");
                    groupedPatternBuilder.append(resolvedPattern.substring(pos));
                    groupedPatternBuilder.append(")");
                    groupCount++;
                }
            }
        }

        String groupedPattern = groupedPatternBuilder.toString();
        if (groupedPattern.isEmpty()) {
            // reg exp pattern did not contain any groups
            groupedPattern = "(" + resolvedPattern + ")";
        }
        stepDocDTO.setGroupedRegExpPattern(groupedPattern);
        stepDocDTO.setParameterGroups(parameterGroups);
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
