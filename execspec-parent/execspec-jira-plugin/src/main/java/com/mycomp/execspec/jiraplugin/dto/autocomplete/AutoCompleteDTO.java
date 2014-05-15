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

    private int insertPosition;

    protected AutoCompleteDTO() {
    }

    public AutoCompleteDTO(String suggestion, int insertPosition) {
        this.insertPosition = insertPosition;
        this.suggestion = suggestion;
    }

    public int getInsertPosition() {
        return insertPosition;
    }

    public String getSuggestion() {
        return suggestion;
    }
}
