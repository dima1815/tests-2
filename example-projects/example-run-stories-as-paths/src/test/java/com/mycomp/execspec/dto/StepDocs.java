package com.mycomp.execspec.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dmytro on 4/23/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StepDocs {

    private List<StepDoc> stepDocs;

    private StepDocs() {
    }

    public StepDocs(List<StepDoc> stepDocs) {
        this.stepDocs = stepDocs;
    }

    public List<StepDoc> getStepDocs() {
        return stepDocs;
    }
}
