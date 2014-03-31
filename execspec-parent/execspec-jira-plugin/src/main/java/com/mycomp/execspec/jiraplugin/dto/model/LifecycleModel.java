package com.mycomp.execspec.jiraplugin.dto.model;

import com.mycomp.execspec.jiraplugin.dto.model.step.StepModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LifecycleModel {

    private final List<StepModel> beforeSteps;
    private final List<StepModel> afterSteps;

    public LifecycleModel(List<StepModel> beforeSteps, List<StepModel> afterSteps) {
        this.beforeSteps = beforeSteps;
        this.afterSteps = afterSteps;
    }

    public List<StepModel> getBeforeSteps() {
        return beforeSteps;
    }

    public List<StepModel> getAfterSteps() {
        return afterSteps;
    }
}
