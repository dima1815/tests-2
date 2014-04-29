package com.mycomp.execspec.jiraplugin.dto.story.input;

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
public class SaveStoryDTO {

    private String issueKey;

    private String projectKey;

    private String asString;

    public String getIssueKey() {
        return issueKey;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public String getAsString() {
        return asString;
    }
}
