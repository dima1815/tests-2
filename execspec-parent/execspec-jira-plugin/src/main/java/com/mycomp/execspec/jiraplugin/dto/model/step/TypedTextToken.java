package com.mycomp.execspec.jiraplugin.dto.model.step;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 3/2/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TypedTextToken implements Token {

    private final TokenKind kind;

    private final String asString;

    public TypedTextToken(TokenKind kind, String asString) {
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
