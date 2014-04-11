package com.mycomp.execspec.jiraplugin.dto.story.out;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 3/2/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NarrativeDTO {

    private String asString;
    private String label;
    private String inOrderTo;
    private String asA;
    private String iWantTo;
    private String soThat;

    public NarrativeDTO() {

    }

    public NarrativeDTO(String asString, String label, String inOrderTo, String asA, String iWantTo, String soThat) {
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
