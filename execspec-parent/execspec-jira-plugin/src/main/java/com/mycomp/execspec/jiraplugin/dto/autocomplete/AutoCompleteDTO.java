package com.mycomp.execspec.jiraplugin.dto.autocomplete;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Dmytro on 4/23/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AutoCompleteDTO {

    private String suggestion;

    private String completePart;

    protected AutoCompleteDTO() {
    }

    public AutoCompleteDTO(String suggestion, String completePart) {
        this.suggestion = suggestion;
        this.completePart = completePart;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public String getCompletePart() {
        return completePart;
    }
}
