package com.mycomp.execspec.jiraplugin.dto.story.out.step;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StepDTO {

    private String asString;

    private List<BaseToken> tokens;

    protected StepDTO() {
    }

    public StepDTO(String asString, List<BaseToken> tokens) {
        this.asString = asString;
        this.tokens = tokens;
    }

    public String asString() {
        return asString;
    }

    public List<BaseToken> getTokens() {
        return tokens;
    }
}
