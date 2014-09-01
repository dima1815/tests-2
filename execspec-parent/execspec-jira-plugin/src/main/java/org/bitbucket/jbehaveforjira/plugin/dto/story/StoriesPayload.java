package org.bitbucket.jbehaveforjira.plugin.dto.story;

import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stories_payload")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoriesPayload {

    @XmlElement
    private List<JiraStory> stories;

    /**
     * Constructor for use via reflection.
     */
    protected StoriesPayload() {
    }

    public StoriesPayload(List<JiraStory> stories) {
        this.stories = stories;
    }

    public List<JiraStory> getStories() {
        return stories;
    }

}
