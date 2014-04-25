package com.mycomp.execspec.jiraplugin.dto.story.output;

import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

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

    private StoryDTO story;

    @XmlElementWrapper(name = "storyReports")
    private List<StoryHtmlReportDTO> storyReports;

    protected StoryPayload() {

    }

    public StoryPayload(StoryDTO story, List<StoryHtmlReportDTO> storyReports) {
        this.story = story;
        this.storyReports = storyReports;
    }

    public StoryDTO getStory() {
        return story;
    }

    public List<StoryHtmlReportDTO> getStoryReports() {
        return storyReports;
    }

}
