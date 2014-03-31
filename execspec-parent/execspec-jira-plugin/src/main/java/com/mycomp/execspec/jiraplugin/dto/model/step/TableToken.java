package com.mycomp.execspec.jiraplugin.dto.model.step;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 3/5/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TableToken implements Token {

    private final TokenKind kind = TokenKind.table;
    private final String asString;
    private final List<String> headers;
    private final List<TableRow> rows;

    public TableToken(String asString, List<String> headers, List<TableRow> rows) {
        this.asString = asString;
        this.headers = headers;
        this.rows = rows;
    }

    @Override
    public TokenKind getKind() {
        return this.kind;
    }

    @Override
    public String getAsString() {
        return this.asString;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<TableRow> getRows() {
        return rows;
    }
}
