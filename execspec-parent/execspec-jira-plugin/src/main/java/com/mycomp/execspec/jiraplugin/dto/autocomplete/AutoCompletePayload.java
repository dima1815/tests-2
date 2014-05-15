package com.mycomp.execspec.jiraplugin.dto.autocomplete;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dmytro on 4/23/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AutoCompletePayload {

    private List<AutoCompleteDTO> entries;

    private AutoCompletePayload() {
    }

    public AutoCompletePayload(List<AutoCompleteDTO> entries) {
        this.entries = entries;
    }

    public List<AutoCompleteDTO> getEntries() {
        return entries;
    }
}
