package com.mycomp.execspec.jiraplugin.dto.story.output;

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
public class StoryDTO {

    private String issueKey;

    private String projectKey;

    private Long version;

    private String path;

    private String description;

    private GivenStoriesDTO givenStories;

    private LifecycleDTO lifecycle;

    private MetaDTO meta;

    private NarrativeDTO narrative;

    private List<ScenarioDTO> scenarios;

    private String asString;


    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GivenStoriesDTO getGivenStories() {
        return givenStories;
    }

    public void setGivenStories(GivenStoriesDTO givenStories) {
        this.givenStories = givenStories;
    }

    public LifecycleDTO getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(LifecycleDTO lifecycle) {
        this.lifecycle = lifecycle;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public void setMeta(MetaDTO meta) {
        this.meta = meta;
    }

    public NarrativeDTO getNarrative() {
        return narrative;
    }

    public void setNarrative(NarrativeDTO narrative) {
        this.narrative = narrative;
    }

    public List<ScenarioDTO> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ScenarioDTO> scenarios) {
        this.scenarios = scenarios;
    }

    public String getAsString() {
        return asString;
    }

    public void setAsString(String asString) {
        this.asString = asString;
    }
}
