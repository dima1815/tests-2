package com.mycomp.execspec.jiraplugin.dto.storyreport;

import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStoryHtml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dmytro on 4/10/2014.
 */
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
