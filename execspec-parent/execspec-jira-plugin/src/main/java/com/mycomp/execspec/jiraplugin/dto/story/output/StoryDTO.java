package com.mycomp.execspec.jiraplugin.dto.story.output;

import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;

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
    private String path;
    private Long version;
    private String asString;
    private String asHTML;

    private List<StoryHtmlReportDTO> storyReports;

    protected StoryDTO() {
    }

    public StoryDTO(String projectKey, String issueKey, Long version, String asString, String asHTML,
                    List<StoryHtmlReportDTO> storyReports) {
        this.projectKey = projectKey;
        this.issueKey = issueKey;
        this.version = version;
        this.path = issueKey;
        this.asString = asString;
        this.asHTML = asHTML;
        this.storyReports = storyReports;
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

    public List<StoryHtmlReportDTO> getStoryReports() {
        return storyReports;
    }
}
