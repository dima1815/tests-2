package com.mycomp.execspec.jiraplugin.dto.payloads;

import com.mycomp.execspec.jiraplugin.dto.model.StoryModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Container for lists of StoryModel objects.
 *
 * @author stasyukd
 */
@XmlRootElement(name = "stories_payload")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryPayload {

    @XmlElement
    private final StoryModel story;

    public StoryPayload(StoryModel story) {
        this.story = story;
    }

    public StoryModel getStory() {
        return story;
    }

}
