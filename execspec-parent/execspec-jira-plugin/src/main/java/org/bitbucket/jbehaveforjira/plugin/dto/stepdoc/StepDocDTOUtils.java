package org.bitbucket.jbehaveforjira.plugin.dto.stepdoc;

import com.mycomp.execspec.jiraplugin.ao.stepdoc.StepDoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StepDocDTOUtils {

    public static void fromDTOToModel(StepDocDTO stepDocDTO, StepDoc stepDoc) {

        stepDoc.setStartingWord(stepDocDTO.getStartingWord());
        stepDoc.setPattern(stepDocDTO.getPattern());
        stepDoc.setRegExpPattern(stepDocDTO.getResolvedPattern());
        stepDoc.setGroupedRegExpPattern(stepDocDTO.getGroupedRegExpPattern());

        List<Integer> parameterGroups = stepDocDTO.getParameterGroups();
        if (parameterGroups != null && !parameterGroups.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < parameterGroups.size(); i++) {
                Integer parameterGroup = parameterGroups.get(i);
                sb.append(parameterGroup.toString());
                if (i < parameterGroups.size()-1) {
                    sb.append(",");
                }
            }
            String parameterGroupsAsString = sb.toString();
            stepDoc.setParameterGroups(parameterGroupsAsString);
        }
    }

    public static StepDocDTO fromModelToDTO(StepDoc stepDoc) {

        String startingWord = stepDoc.getStartingWord();
        String pattern = stepDoc.getPattern();
        String regExpPattern = stepDoc.getRegExpPattern();
        String groupedRegExpPattern = stepDoc.getGroupedRegExpPattern();
        String parameterGroupsStr = stepDoc.getParameterGroups();
        List<Integer> parameterGroups;
        if (parameterGroupsStr != null) {
            String[] strGroups = parameterGroupsStr.split(",");
            parameterGroups = new ArrayList<Integer>(strGroups.length);
            for (String strGroup : strGroups) {
                Integer intGroup = Integer.parseInt(strGroup);
                parameterGroups.add(intGroup);
            }
        } else {
            parameterGroups = Collections.emptyList();
        }

        StepDocDTO stepDocDTO = new StepDocDTO(startingWord, pattern, regExpPattern, groupedRegExpPattern, parameterGroups);
        return stepDocDTO;
    }

    public static List<StepDocDTO> fromModelToDTO(List<StepDoc> stepDocs) {

        List<StepDocDTO> results = new ArrayList<StepDocDTO>(stepDocs.size());
        for (StepDoc stepDoc : stepDocs) {
            StepDocDTO stepDocDTO = fromModelToDTO(stepDoc);
            results.add(stepDocDTO);
        }

        return results;
    }
}
