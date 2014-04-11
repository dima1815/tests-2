package com.mycomp.execspec.jiraplugin.ao.testreport;

import net.java.ao.Entity;
import net.java.ao.Preload;

/**
 * Created by Dmytro on 4/8/2014.
 */
@Preload
public interface StepReport extends Entity {

    String getStep();

    void setStep(String step);

    String getStatus();

    void setStatus(String testStatus);

    String getFailureTrace();

    void setFailureTrace(String failureTrace);

    ScenarioReport getScenarioReport();

    void setScenarioReport(ScenarioReport scenarioReport);

}
