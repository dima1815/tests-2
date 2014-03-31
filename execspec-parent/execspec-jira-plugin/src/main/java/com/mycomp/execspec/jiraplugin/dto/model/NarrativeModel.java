package com.mycomp.execspec.jiraplugin.dto.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 3/2/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NarrativeModel {

    private final String asString;
    private final String label;
    private final String inOrderTo;
    private final String asA;
    private final String iWantTo;
    private final String soThat;

    public NarrativeModel(String asString, String label, String inOrderTo, String asA, String iWantTo, String soThat) {
        this.asString = asString;
        this.label = label;
        this.inOrderTo = inOrderTo;
        this.asA = asA;
        this.iWantTo = iWantTo;
        this.soThat = soThat;
    }

    public String getAsString() {
        return asString;
    }

    public String getLabel() {
        return label;
    }

    public String getInOrderTo() {
        return inOrderTo;
    }

    public String getAsA() {
        return asA;
    }

    public String getiWantTo() {
        return iWantTo;
    }

    public String getSoThat() {
        return soThat;
    }
}
