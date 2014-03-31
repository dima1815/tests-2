package com.mycomp.execspec.jiraplugin.dto.model.step;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 3/5/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TableRow {

    private final List<Token> tokens;

    public TableRow(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
