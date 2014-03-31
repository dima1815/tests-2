package com.mycomp.execspec.jiraplugin.dto.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GivenStoriesModel {

    private final String asString;
    private final List<GivenStoryModel> givenStories;

    public GivenStoriesModel(String asString, List<GivenStoryModel> givenStories) {
        this.asString = asString;
        this.givenStories = givenStories;
    }

    public String getAsString() {
        return asString;
    }

    public List<GivenStoryModel> getGivenStories() {
        return givenStories;
    }
}
