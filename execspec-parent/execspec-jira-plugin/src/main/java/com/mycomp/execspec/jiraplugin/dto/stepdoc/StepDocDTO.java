package com.mycomp.execspec.jiraplugin.dto.stepdoc;

import org.jbehave.core.steps.StepType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

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
    private String groupedRegExpPattern;
    private List<Integer> parameterGroups;

    protected StepDocDTO() {
    }

    public StepDocDTO(StepType stepType, String startingWord, String pattern, String regExpPattern, String groupedRegExpPattern, List<Integer> parameterGroups) {
        this.stepType = stepType;
        this.startingWord = startingWord;
        this.pattern = pattern;
        this.regExpPattern = regExpPattern;
        this.groupedRegExpPattern = groupedRegExpPattern;
        this.parameterGroups = parameterGroups;
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

    public String getGroupedRegExpPattern() {
        return groupedRegExpPattern;
    }

    public List<Integer> getParameterGroups() {
        return parameterGroups;
    }

    public void setGroupedRegExpPattern(String groupedRegExpPattern) {
        this.groupedRegExpPattern = groupedRegExpPattern;
    }

    public void setParameterGroups(List<Integer> parameterGroups) {
        this.parameterGroups = parameterGroups;
    }

    @Override
    public String toString() {
        return "StepDocDTO{" +
                "stepType=" + stepType +
                ", startingWord='" + startingWord + '\'' +
                ", pattern='" + pattern + '\'' +
                ", regExpPattern='" + regExpPattern + '\'' +
                ", groupedRegExpPattern='" + groupedRegExpPattern + '\'' +
                ", parameterGroups=" + parameterGroups +
                '}';
    }
}
