package com.mycomp.execspec.jiraplugin.dto.story.out;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GivenStoriesDTO {

    private String asString;
    private List<GivenStoryDTO> givenStories;

    public GivenStoriesDTO() {
    }

    public GivenStoriesDTO(String asString, List<GivenStoryDTO> givenStories) {
        this.asString = asString;
        this.givenStories = givenStories;
    }

    public String getAsString() {
        return asString;
    }

    public List<GivenStoryDTO> getGivenStories() {
        return givenStories;
    }
}
