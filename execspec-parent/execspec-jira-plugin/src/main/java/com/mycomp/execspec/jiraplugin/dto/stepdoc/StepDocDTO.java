package com.mycomp.execspec.jiraplugin.dto.stepdoc;

import org.jbehave.core.steps.StepType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Dmytro on 4/23/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StepDocDTO {

    private StepType stepType;
    private String startingWord;
    private String pattern;
    private String regExpPattern;

    protected StepDocDTO() {
    }

    public StepDocDTO(StepType stepType, String startingWord, String pattern, String regExpPattern) {
        this.stepType = stepType;
        this.startingWord = startingWord;
        this.pattern = pattern;
        this.regExpPattern = regExpPattern;
    }

    public StepType getStepType() {
        return stepType;
    }

    public String getStartingWord() {
        return startingWord;
    }

    public String getPattern() {
        return pattern;
    }

    public String getRegExpPattern() {
        return regExpPattern;
    }
}
