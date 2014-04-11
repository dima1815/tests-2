package com.mycomp.execspec.jiraplugin.dto.story.out.wrapperpayloads;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Dmytro on 4/10/2014.
 */
@XmlRootElement(name = "story_and_version")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryAndVersionAsStringPayload {

    private String storyAsString;

    private Long storyVersion;

    private StoryAndVersionAsStringPayload() {
    }

    public StoryAndVersionAsStringPayload(String storyAsString, Long storyVersion) {
        this.storyAsString = storyAsString;
        this.storyVersion = storyVersion;
    }

    public String getStoryAsString() {
        return storyAsString;
    }

    public Long getStoryVersion() {
        return storyVersion;
    }
}
