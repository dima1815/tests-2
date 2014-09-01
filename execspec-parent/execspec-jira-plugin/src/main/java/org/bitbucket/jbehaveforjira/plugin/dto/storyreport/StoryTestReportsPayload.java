package org.bitbucket.jbehaveforjira.plugin.dto.storyreport;

import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStoryHtml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stories_payload")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryTestReportsPayload {

    private List<JiraStoryHtml> storyTestReports;

    protected StoryTestReportsPayload() {
    }

    public StoryTestReportsPayload(List<JiraStoryHtml> storyTestReports) {
        this.storyTestReports = storyTestReports;
    }

    public List<JiraStoryHtml> getStoryTestReports() {
        return storyTestReports;
    }
}
