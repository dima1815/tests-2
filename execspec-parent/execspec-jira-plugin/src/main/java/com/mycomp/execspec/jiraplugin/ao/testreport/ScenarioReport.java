package com.mycomp.execspec.jiraplugin.ao.testreport;

import net.java.ao.Entity;
import net.java.ao.OneToMany;
import net.java.ao.Preload;

@Preload
public interface ScenarioReport extends Entity {

    String getScenarioTitle();

    void setScenarioTitle(String scenarioTitle);

    //    @NotNull
    StoryReport getStoryReport();

    void setStoryReport(StoryReport storyReport);

    String getStatus();

    void setStatus(String testStatus);

    @OneToMany
    StepReport[] getStepReports();

}
