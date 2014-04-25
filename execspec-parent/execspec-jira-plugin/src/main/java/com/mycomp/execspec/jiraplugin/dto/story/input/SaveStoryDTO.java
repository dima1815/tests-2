package com.mycomp.execspec.jiraplugin.dto.story.input;

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
public class SaveStoryDTO {

    private Long id;

    private String issueKey;

    private String projectKey;

    private String narrative;

    private List<String> scenarios;

    public String getIssueKey() {
        return issueKey;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public String getNarrative() {
        return narrative;
    }

    public List<String> getScenarios() {
        return scenarios;
    }

    public Long getId() {
        return id;
    }
}
