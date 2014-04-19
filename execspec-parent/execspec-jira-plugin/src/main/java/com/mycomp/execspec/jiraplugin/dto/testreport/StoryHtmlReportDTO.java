package com.mycomp.execspec.jiraplugin.dto.testreport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model used by JiraStoryReporter JBehave implementation to report the story test status.
 * Created by Dmytro on 4/4/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryHtmlReportDTO {

    /**
     * Environment where story test was run, e.g. DEV, TEST, UAT, etc.
     */
    private String environment;

    private String storyPath;

    private Long storyVersion;

    public TestStatus status;

    private String htmlReport;

    protected StoryHtmlReportDTO() {
    }

    public StoryHtmlReportDTO(String environment, String storyPath,
                              Long storyVersion, TestStatus status, String htmlReport) {
        this.environment = environment;
        this.storyPath = storyPath;
        this.storyVersion = storyVersion;
        this.status = status;
        this.htmlReport = htmlReport;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getStoryPath() {
        return storyPath;
    }

    public void setStoryPath(String storyPath) {
        this.storyPath = storyPath;
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

    public String getHtmlReport() {
        return htmlReport;
    }

    public void setHtmlReport(String htmlReport) {
        this.htmlReport = htmlReport;
    }
}

