package com.mycomp.execspec.jiraplugin.dto.story.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GivenStoryDTO {

    private String path;

    public GivenStoryDTO() {

    }

    public GivenStoryDTO(String path) {
        this.path = path;
    }

    public String asString() {
        return path;
    }

    public String getPath() {
        return path;
    }
}
