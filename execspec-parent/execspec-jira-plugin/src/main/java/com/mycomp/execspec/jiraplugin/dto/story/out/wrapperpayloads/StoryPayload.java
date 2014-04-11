package com.mycomp.execspec.jiraplugin.dto.story.out.wrapperpayloads;

import com.mycomp.execspec.jiraplugin.dto.story.out.StoryDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Container for lists of StoryDTO objects.
 * <p/>
 * TODO - should not need this wrapper class, use StoryDTO directly instead.
 *
 * @author stasyukd
 */
@XmlRootElement(name = "story_payload")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryPayload {

    @XmlElement
    private StoryDTO story;

    protected StoryPayload() {

    }

    public StoryPayload(StoryDTO story) {
        this.story = story;
    }

    public StoryDTO getStory() {
        return story;
    }

}
