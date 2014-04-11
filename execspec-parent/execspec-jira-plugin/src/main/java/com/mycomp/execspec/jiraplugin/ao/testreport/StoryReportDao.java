package com.mycomp.execspec.jiraplugin.ao.testreport;

import com.atlassian.activeobjects.external.ActiveObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

public final class StoryReportDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ActiveObjects ao;

    public StoryReportDao(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }

    public StoryReport createStoryReport() {
        return ao.create(StoryReport.class);
    }

    public ScenarioReport createScenarioReport(StoryReport storyTestReport) {
        ScenarioReport scenarioReport = ao.create(ScenarioReport.class);
        scenarioReport.setStoryReport(storyTestReport);
        return scenarioReport;
    }

    public StepReport createStepReport(ScenarioReport scenarioReport) {
        StepReport stepReport = ao.create(StepReport.class);
        stepReport.setScenarioReport(scenarioReport);
        return stepReport;
    }

    public void delete(StoryReport storyTestReport) {
        ScenarioReport[] scenarioTestReports = storyTestReport.getScenarioReports();
        for (ScenarioReport scenarioTestReport : scenarioTestReports) {
            StepReport[] stepTestReports = scenarioTestReport.getStepReports();
            for (StepReport stepTestReport : stepTestReports) {
                ao.delete(stepTestReport);
            }
            ao.delete(scenarioTestReport);
        }
        ao.delete(storyTestReport);
    }

    public StoryReport get(Integer id) {
        return ao.get(StoryReport.class, id);
    }

    public List<StoryReport> findAll() {
        return newArrayList(ao.find(StoryReport.class));
    }

}
