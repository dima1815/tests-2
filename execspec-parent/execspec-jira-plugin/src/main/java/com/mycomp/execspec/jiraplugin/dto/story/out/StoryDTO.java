package com.mycomp.execspec.jiraplugin.dto.story.out;

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
    // combination of ('project key' + '/' + 'issueKey')
    private String path;
    private Long version;
    private NarrativeDTO narrative;
    private MetaDTO meta;
    private GivenStoriesDTO givenStories;
    private LifecycleDTO lifecycle;
    private List<ScenarioDTO> scenarios;

    protected StoryDTO() {
    }

    public StoryDTO(String projectKey, String issueKey, Long version, NarrativeDTO narrative, MetaDTO meta, GivenStoriesDTO givenStories, LifecycleDTO lifecycle, List<ScenarioDTO> scenarios) {
        this.projectKey = projectKey;
        this.issueKey = issueKey;
        this.version = version;
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

    public NarrativeDTO getNarrative() {
        return narrative;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public GivenStoriesDTO getGivenStories() {
        return givenStories;
    }

    public LifecycleDTO getLifecycle() {
        return lifecycle;
    }

    public List<ScenarioDTO> getScenarios() {
        return scenarios;
    }

    public Long getVersion() {
        return version;
    }


    public String asString() {

        StringBuilder sb = new StringBuilder();

        String narrativeAsString = this.getNarrative().getAsString();
        sb.append(narrativeAsString);
        sb.append("\n\n");

        String metaAsString = this.getMeta().asString();
        if (!metaAsString.isEmpty()) {
            sb.append(metaAsString);
        }

        String givenStoriesAsString = this.getGivenStories().asString();
        sb.append(givenStoriesAsString);

        String lifecycleAsString = this.getLifecycle().asString();
        sb.append(lifecycleAsString);

        List<ScenarioDTO> scenarios = this.getScenarios();
        for (ScenarioDTO scenario : scenarios) {
            sb.append("\n");
            String scenarioAsString = scenario.asString();
            sb.append(scenarioAsString);
        }

        String asString = sb.toString();
        return asString;
    }
}
