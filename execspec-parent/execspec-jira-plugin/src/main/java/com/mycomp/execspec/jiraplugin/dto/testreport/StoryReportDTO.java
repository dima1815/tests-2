package com.mycomp.execspec.jiraplugin.dto.testreport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Model used by JiraStoryReporter JBehave implementation to report the story test status.
 * Created by Dmytro on 4/4/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryReportDTO {

    private String storyPath;

    /**
     * Environment where story test was run, e.g. DEV, TEST, UAT, etc.
     */
    private String environment;

    private Long storyVersion;

    public TestStatus status;

    private List<ScenarioReportDTO> scenarioTestReportDTOs = new ArrayList<ScenarioReportDTO>();

    protected StoryReportDTO() {
    }

    public StoryReportDTO(String storyPath, String environment) {
        this.storyPath = storyPath;
        this.environment = environment;
    }

    public String getStoryPath() {
        return storyPath;
    }

    public void setStoryPath(String storyPath) {
        this.storyPath = storyPath;
    }

    public List<ScenarioReportDTO> getScenarioTestReportDTOs() {
        return scenarioTestReportDTOs;
    }

    public void setScenarioTestReportDTOs(List<ScenarioReportDTO> scenarioTestReportDTOs) {
        this.scenarioTestReportDTOs = scenarioTestReportDTOs;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Long getStoryVersion() {
        return storyVersion;
    }

    public void setStoryVersion(Long storyVersion) {
        this.storyVersion = storyVersion;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }
}

