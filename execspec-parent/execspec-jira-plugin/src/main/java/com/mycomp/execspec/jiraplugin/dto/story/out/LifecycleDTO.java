package com.mycomp.execspec.jiraplugin.dto.story.out;

import com.mycomp.execspec.jiraplugin.dto.story.out.step.StepDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LifecycleDTO {

    private List<StepDTO> beforeSteps;
    private List<StepDTO> afterSteps;

    public LifecycleDTO() {

    }

    public LifecycleDTO(List<StepDTO> beforeSteps, List<StepDTO> afterSteps) {
        this.beforeSteps = beforeSteps;
        this.afterSteps = afterSteps;
    }

    public List<StepDTO> getBeforeSteps() {
        return beforeSteps;
    }

    public List<StepDTO> getAfterSteps() {
        return afterSteps;
    }
}
