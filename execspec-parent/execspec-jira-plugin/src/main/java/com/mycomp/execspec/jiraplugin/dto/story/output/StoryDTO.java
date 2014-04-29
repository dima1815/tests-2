package com.mycomp.execspec.jiraplugin.dto.story.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
    private String asString;
    private String asHTML;

    protected StoryDTO() {
    }

    public StoryDTO(String projectKey, String issueKey, Long version, String asString, String asHTML) {
        this.projectKey = projectKey;
        this.issueKey = issueKey;
        this.version = version;
        this.path = issueKey;
        this.asString = asString;
        this.asHTML = asHTML;
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

    public Long getVersion() {
        return version;
    }

    public String getAsString() {
        return asString;
    }

    public String getAsHTML() {
        return asHTML;
    }

}
