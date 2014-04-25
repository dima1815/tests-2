package com.mycomp.execspec.jiraplugin.dto.story.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ExamplesTableDTO {

    private String asString;
    private String headerSeparator;
    private String valueSeparator;
    private String ignorableSeparator;
    private ArrayList<String> headers;
    private ArrayList<ArrayList<String>> data;

    protected ExamplesTableDTO() {
        // used only by JaxB
    }

    public ExamplesTableDTO(String asString, String headerSeparator, String valueSeparator, String ignorableSeparator, ArrayList<String> headers, ArrayList<ArrayList<String>> data) {
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

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }
}
