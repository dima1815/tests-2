package com.mycomp.execspec.jiraplugin.dto.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ExamplesTableModel {

    private final String asString;
    private final String headerSeparator;
    private final String valueSeparator;
    private final String ignorableSeparator;
    private final List<String> headers;
    private final List<List<String>> data;

    public ExamplesTableModel(String asString, String headerSeparator, String valueSeparator, String ignorableSeparator, List<String> headers, List<List<String>> data) {
        this.asString = asString;
        this.headerSeparator = headerSeparator;
        this.valueSeparator = valueSeparator;
        this.ignorableSeparator = ignorableSeparator;
        this.headers = headers;
        this.data = data;
    }

    public String getAsString() {
        return asString;
    }

    public String getHeaderSeparator() {
        return headerSeparator;
    }

    public String getValueSeparator() {
        return valueSeparator;
    }

    public String getIgnorableSeparator() {
        return ignorableSeparator;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<List<String>> getData() {
        return data;
    }
}
