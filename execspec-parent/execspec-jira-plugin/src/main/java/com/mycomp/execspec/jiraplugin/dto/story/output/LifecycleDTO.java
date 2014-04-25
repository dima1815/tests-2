package com.mycomp.execspec.jiraplugin.dto.story.output;

import com.mycomp.execspec.jiraplugin.dto.story.output.step.StepDTO;

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

    public String asString() {

        if (!this.beforeSteps.isEmpty() || !this.afterSteps.isEmpty()) {
            StringBuilder sb = new StringBuilder("Lifecycle:\n");

            if (!this.beforeSteps.isEmpty()) {
                sb.append("Before:\n");
                for (StepDTO beforeStep : beforeSteps) {
                    sb.append(beforeStep.asString());
                    sb.append("\n");
                }
            }

            if (!this.afterSteps.isEmpty()) {
                sb.append("After:\n");
                for (StepDTO afterStep : afterSteps) {
                    sb.append(afterStep.asString());
                    sb.append("\n");
                }
            }

            String asString = sb.toString();
            return asString;
        } else {
            return "";
        }
    }
}
