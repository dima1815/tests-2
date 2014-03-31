package com.mycomp.execspec.jiraplugin.dto.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * DTO class for Story instances.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryModel {

    private final String issueKey;
    private final String projectKey;
    // combination of ('project key' + '/' + 'issueKey')
    private final String path;
    private final NarrativeModel narrative;
    private final MetaModel meta;
    private final GivenStoriesModel givenStories;
    private final LifecycleModel lifecycle;
    private final List<ScenarioModel> scenarios;

    public StoryModel(String projectKey, String issueKey, NarrativeModel narrative, MetaModel meta, GivenStoriesModel givenStories, LifecycleModel lifecycle, List<ScenarioModel> scenarios) {
        this.projectKey = projectKey;
        this.issueKey = issueKey;
        this.path = projectKey + "/" + issueKey;
        this.narrative = narrative;
        this.meta = meta;
        this.givenStories = givenStories;
        this.lifecycle = lifecycle;
        this.scenarios = scenarios;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public String getPath() {
        return path;
    }

    public NarrativeModel getNarrative() {
        return narrative;
    }

    public MetaModel getMeta() {
        return meta;
    }

    public GivenStoriesModel getGivenStories() {
        return givenStories;
    }

    public LifecycleModel getLifecycle() {
        return lifecycle;
    }

    public List<ScenarioModel> getScenarios() {
        return scenarios;
    }
}
