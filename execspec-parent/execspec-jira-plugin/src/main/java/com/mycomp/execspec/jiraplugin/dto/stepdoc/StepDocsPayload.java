package com.mycomp.execspec.jiraplugin.dto.stepdoc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dmytro on 4/23/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StepDocsPayload {

    private List<StepDocDTO> stepDocs;

    private StepDocsPayload() {
    }

    public StepDocsPayload(List<StepDocDTO> stepDocs) {
        this.stepDocs = stepDocs;
    }

    public List<StepDocDTO> getStepDocs() {
        return stepDocs;
    }
}
