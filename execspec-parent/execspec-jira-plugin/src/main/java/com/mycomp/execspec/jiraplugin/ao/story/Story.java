package com.mycomp.execspec.jiraplugin.ao.story;

import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
import net.java.ao.Entity;
import net.java.ao.OneToMany;
import net.java.ao.Preload;

/**
 * TODO - renae this class to be different to JBehave's Story class, e.g. JiraStory
 */
@Preload
public interface Story extends Entity {

    //    @NotNull
    String getIssueKey();

    void setIssueKey(String issueKey);

    Long getIssueId();

    void setIssueLong();

    Long getVersion();

    void setVersion(Long version);

    //    @NotNull
    String getProjectKey();

    void setProjectKey(String projectKey);

    //    @NotNull
    String getAsString();

    void setAsString(String asString);

    String getLastEditedBy();

    void setLastEditedBy(String lastEditedBy);

    @OneToMany
    StoryHtmlReport[] getStoryHtmlReports();
}
