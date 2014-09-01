package org.bitbucket.jbehaveforjira.plugin.ao.story;

import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
import net.java.ao.Entity;
import net.java.ao.OneToMany;
import net.java.ao.Preload;

@Preload
public interface Story extends Entity {

    String getIssueKey();

    void setIssueKey(String issueKey);

    Long getIssueId();

    void setIssueId(Long issueId);

    Long getVersion();

    void setVersion(Long version);

    String getProjectKey();

    void setProjectKey(String projectKey);

    String getAsString();

    void setAsString(String asString);

    String getLastEditedBy();

    void setLastEditedBy(String lastEditedBy);

    @OneToMany
    StoryReport[] getStoryReports();
    StoryHtmlReport[] getStoryHtmlReports();
}
