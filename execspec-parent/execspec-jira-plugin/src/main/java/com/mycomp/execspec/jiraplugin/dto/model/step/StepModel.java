package com.mycomp.execspec.jiraplugin.dto.model.step;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StepModel {

    private final String asString;
    private final List<Token> tokens;

    public StepModel(String asString, List<Token> tokens) {
        this.asString = asString;
        this.tokens = tokens;
    }

    public String getAsString() {
        return asString;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
