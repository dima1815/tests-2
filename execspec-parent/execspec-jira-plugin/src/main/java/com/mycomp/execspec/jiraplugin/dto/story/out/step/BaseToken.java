package com.mycomp.execspec.jiraplugin.dto.story.out.step;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 3/5/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseToken {

    protected TokenKind kind;

    protected String asString;

    protected BaseToken() {
    }

    public BaseToken(TokenKind kind, String asString) {
        this.kind = kind;
        this.asString = asString;
    }

    public TokenKind getKind() {
        return kind;
    }

    public String getAsString() {
        return asString;
    }
}
