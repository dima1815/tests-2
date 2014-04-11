package com.mycomp.execspec.jiraplugin.dto.testreport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Dmytro on 4/7/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StepReportDTO {

    public String stepAsString;

    public TestStatus status;

    private String failureTrace;

    protected StepReportDTO() {

    }

    public StepReportDTO(String stepAsString, TestStatus status) {
        this.stepAsString = stepAsString;
        this.status = status;
    }

    public String getStepAsString() {
        return stepAsString;
    }

    public void setStepAsString(String stepAsString) {
        this.stepAsString = stepAsString;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public String getFailureTrace() {
        return failureTrace;
    }

    public void setFailureTrace(String failureTrace) {
        this.failureTrace = failureTrace;
    }
}
