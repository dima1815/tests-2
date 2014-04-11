package com.mycomp.execspec.jiraplugin.dto.story.out;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GivenStoryDTO {

    private String asString;
    private String path;

    public GivenStoryDTO() {

    }

    public GivenStoryDTO(String asString, String path) {
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
