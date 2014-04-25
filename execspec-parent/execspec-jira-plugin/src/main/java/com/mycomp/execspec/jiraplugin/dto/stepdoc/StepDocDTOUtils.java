package com.mycomp.execspec.jiraplugin.dto.stepdoc;

import com.mycomp.execspec.jiraplugin.ao.stepdoc.StepDoc;
import org.jbehave.core.steps.StepType;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StepDocDTOUtils {

    public static void fromDTOToModel(StepDocDTO stepDocDTO, StepDoc stepDoc) {

        stepDoc.setPattern(stepDocDTO.getPattern());
        stepDoc.setStartingWord(stepDocDTO.getStartingWord());
        stepDoc.setStepType(stepDocDTO.getStepType().name());
    }

    public static StepDocDTO fromModelToDTO(StepDoc stepDoc) {

        StepType stepType = StepType.valueOf(stepDoc.getStepType());
        String startingWord = stepDoc.getStartingWord();
        String pattern = stepDoc.getPattern();
        String regExpPattern = stepDoc.getRegExpPattern();
        StepDocDTO stepDocDTO = new StepDocDTO(stepType, startingWord, pattern, regExpPattern);
        return stepDocDTO;
    }

}
