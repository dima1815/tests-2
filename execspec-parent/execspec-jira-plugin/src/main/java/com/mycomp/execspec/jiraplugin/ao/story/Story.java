package com.mycomp.execspec.jiraplugin.ao.story;

import com.mycomp.execspec.jiraplugin.ao.testreport.StoryReport;
import net.java.ao.Entity;
import net.java.ao.OneToMany;
import net.java.ao.Preload;

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
    String getNarrative();

    void setNarrative(String narrative);

    @OneToMany
    Scenario[] getScenarios();

    @OneToMany
    StoryReport[] getStoryTestReports();

}
