package com.mycomp.execspec.jiraplugin.ao.testreport;

import com.mycomp.execspec.jiraplugin.ao.story.Story;
import net.java.ao.Entity;
import net.java.ao.Preload;

/**
 * Represents story test report, received as from the JBehave story run.
 */
@Preload
public interface StoryHtmlReport extends Entity {

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

    String getHtmlReport();

    void setHtmlReport(String htmlReport);

    Integer getTotalScenarios();

    void setTotalScenarios(Integer totalScenarios);

    Integer getTotalScenariosPassed();

    void setTotalScenariosPassed(Integer totalScenariosPassed);

    Integer getTotalScenariosFailed();

    void setTotalScenariosFailed(Integer totalScenariosFailed);

    Integer getTotalScenariosPending();

    void setTotalScenariosPending(Integer totalScenariosPending);

    Integer getTotalScenariosSkipped();

    void setTotalScenariosSkipped(Integer totalScenariosSkipped);

    Integer getTotalScenariosNotPerformed();

    void setTotalScenariosNotPerformed(Integer totalScenariosNotPerformed);
}
