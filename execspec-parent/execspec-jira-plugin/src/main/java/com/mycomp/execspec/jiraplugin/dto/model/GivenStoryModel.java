package com.mycomp.execspec.jiraplugin.dto.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GivenStoryModel {

    private final String asString;
    private final String path;

    public GivenStoryModel(String asString, String path) {
        this.asString = asString;
        this.path = path;
    }

    public String getAsString() {
        return asString;
    }

    public String getPath() {
        return path;
    }
}
