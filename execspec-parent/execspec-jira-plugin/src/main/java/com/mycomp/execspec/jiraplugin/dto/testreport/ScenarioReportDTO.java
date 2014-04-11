package com.mycomp.execspec.jiraplugin.dto.testreport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 4/7/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioReportDTO {

    public String title;

    public TestStatus status;

    private List<StepReportDTO> stepTestReportModels = new ArrayList<StepReportDTO>();

    protected ScenarioReportDTO() {
    }

    public ScenarioReportDTO(String title) {

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public List<StepReportDTO> getStepTestReportModels() {
        return stepTestReportModels;
    }

    public void setStepTestReportDTOs(List<StepReportDTO> stepTestReportModels) {
        this.stepTestReportModels = stepTestReportModels;
    }
}
