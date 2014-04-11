package com.mycomp.execspec.jiraplugin.ao.testreport;

import com.mycomp.execspec.jiraplugin.ao.story.Story;
import net.java.ao.Entity;
import net.java.ao.OneToMany;
import net.java.ao.Preload;

/**
 * Represents story test report, received as from the JBehave story run.
 */
@Preload
public interface StoryReport extends Entity {

    //    @NotNull
    Story getStory();

    void setStory(Story story);

    /**
     * Environment where the story test was run, e.g. DEV, TEST, UAT, etc.
     *
     * @return
     */
    String getEnvironment();

    void setEnvironment(String environment);

    String getStatus();

    void setStatus(String testStatus);

    /**
     * Version of the Story entity at which the story was execute and hence to which report relates.
     *
     * @return
     */
    Long getStoryVersion();

    void setStoryVersion(Long storyVersion);

    @OneToMany
    ScenarioReport[] getScenarioReports();

}
