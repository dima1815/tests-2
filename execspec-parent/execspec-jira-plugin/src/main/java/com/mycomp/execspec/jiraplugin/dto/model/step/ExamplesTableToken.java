package com.mycomp.execspec.jiraplugin.dto.model.step;

import com.mycomp.execspec.jiraplugin.dto.model.ExamplesTableModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 4/1/2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ExamplesTableToken implements Token {

    private TokenKind kind = TokenKind.table;

    private final String asString;

    private final ExamplesTableModel examplesTableModel;

    public ExamplesTableToken(String asString, ExamplesTableModel examplesTableModel) {
        this.asString = asString;
        this.examplesTableModel = examplesTableModel;
    }

    @Override
    public TokenKind getKind() {
        return kind;
    }

    @Override
    public String getAsString() {
        return asString;
    }

    public ExamplesTableModel getExamplesTableModel() {
        return examplesTableModel;
    }
}
