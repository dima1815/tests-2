package com.mycomp.execspec;

import com.mycomp.execspec.jiraplugin.dto.testreport.ScenarioReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.StepReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.TestStatus;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.jbehave.core.model.*;
import org.jbehave.core.reporters.StoryReporter;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmytro on 2/26/14.
 */
public class JiraStoryReporter implements StoryReporter {

    private String jiraBaseUrl;

    private String addTestReportPath = "rest/story-res/1.0/story-test/add";

    private ThreadLocal<StoryReportDTO> threadLocalState = new ThreadLocal<StoryReportDTO>();

    private void sendTestState() {

        StoryReportDTO storyTestReportModel = threadLocalState.get();
        String currentStoryPath = storyTestReportModel.getStoryPath();

        if (currentStoryPath.equals("BeforeStories") || currentStoryPath.equals("AfterStories")) {
            // we do not report anything on Before or After type stories
        } else {

            setStoryTestStatus();

            String loginParams = "?os_username=admin&os_password=admin";
            String postUrl = jiraBaseUrl
                    + "/" + addTestReportPath + "/"
                    + currentStoryPath
                    + loginParams
                    + "&status=Passed";

            Client client = Client.create();
            WebResource res = client.resource(postUrl);

            String response = res.accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .post(String.class, storyTestReportModel);

        }
    }

    @Override
    public void storyNotAllowed(Story story, String filter) {
        log("-> storyNotAllowed, story - " + story);
    }

    @Override
    public void storyCancelled(Story story, StoryDuration storyDuration) {
        log("-> storyCancelled, story - " + story + ", storyDuration - " + storyDuration);
    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {

        log("-> beforeStory, story - " + story + ", givenStory - " + givenStory);

        StoryReportDTO state = new StoryReportDTO(story.getPath(), "DEV");
        threadLocalState.set(state);

        String path = story.getPath();
        if (path.equals("BeforeStories") || path.equals("AfterStories")) {
            // we do not set anything for Before or After type stories
        } else {
            Meta meta = story.getMeta();
            String versionInJiraStr = meta.getProperty("version-in-jira");
            Long storyVersion = Long.parseLong(versionInJiraStr);
            state.setStoryVersion(storyVersion);
        }
    }

    @Override
    public void afterStory(boolean givenStory) {

        sendTestState();
    }

    private void setStoryTestStatus() {

        StoryReportDTO storyReportDTO = threadLocalState.get();
        List<ScenarioReportDTO> scenarioTestReportModels = storyReportDTO.getScenarioTestReportDTOs();

        // set story test status, assume passed unless at least one of the scenarios is failed or pending
        storyReportDTO.setStatus(TestStatus.PASSED);
        for (ScenarioReportDTO scenarioTestReportModel : scenarioTestReportModels) {
            TestStatus scenarioStatus = scenarioTestReportModel.getStatus();
            switch (scenarioStatus) {
                case FAILED:
                    storyReportDTO.setStatus(TestStatus.FAILED);
                    return;
                case PENDING:
                    storyReportDTO.setStatus(TestStatus.PENDING);
                    return;
            }
        }
    }

    @Override
    public void narrative(Narrative narrative) {
        log("-> narrative, narrative - " + narrative);
    }

    @Override
    public void lifecyle(Lifecycle lifecycle) {
        log("-> lifecyle, lifecycle - " + lifecycle);
    }

    @Override
    public void scenarioNotAllowed(Scenario scenario, String filter) {
        log("-> scenarioNotAllowed, scenario - " + scenario);
    }

    @Override
    public void beforeScenario(String scenarioTitle) {

        StoryReportDTO model = threadLocalState.get();
        List<ScenarioReportDTO> scenarioTestReportModels = model.getScenarioTestReportDTOs();

        ScenarioReportDTO scenarioTestReportModel = new ScenarioReportDTO(scenarioTitle);
        scenarioTestReportModels.add(scenarioTestReportModel);
    }

    @Override
    public void scenarioMeta(Meta meta) {
        log("-> scenarioMeta, meta - " + meta);
    }

    @Override
    public void afterScenario() {

        StoryReportDTO model = threadLocalState.get();
        ScenarioReportDTO lastExecutedScenario = model.getScenarioTestReportDTOs().get(model.getScenarioTestReportDTOs().size() - 1);

        // set scenario status
        lastExecutedScenario.setStatus(TestStatus.PASSED);
        List<StepReportDTO> stepTestReportModels = lastExecutedScenario.getStepTestReportModels();
        for (StepReportDTO stepTestReportModel : stepTestReportModels) {
            TestStatus stepStatus = stepTestReportModel.getStatus();
            switch (stepStatus) {
                case FAILED:
                    lastExecutedScenario.setStatus(TestStatus.FAILED);
                    return;
                case PENDING:
                    lastExecutedScenario.setStatus(TestStatus.PENDING);
                    return;
            }
        }

    }

    @Override
    public void givenStories(GivenStories givenStories) {
        log("-> givenStories, givenStories - " + givenStories);
    }

    @Override
    public void givenStories(List<String> storyPaths) {
        log("-> givenStories, storyPaths - " + storyPaths);
    }

    @Override
    public void beforeExamples(List<String> steps, ExamplesTable table) {
        log("-> beforeExamples, steps - " + steps + ", table - " + table);
    }

    @Override
    public void example(Map<String, String> tableRow) {
        log("-> example, tableRow - " + tableRow);
    }

    @Override
    public void afterExamples() {
        log("-> afterExamples");
    }

    @Override
    public void beforeStep(String step) {
        log("-> beforeStep, step - " + step);
    }

    @Override
    public void successful(String step) {

        StoryReportDTO model = threadLocalState.get();
        ScenarioReportDTO scenarioTestReportModel = model.getScenarioTestReportDTOs().get(model.getScenarioTestReportDTOs().size() - 1);
        List<StepReportDTO> stepReports = scenarioTestReportModel.getStepTestReportModels();

        StepReportDTO stepReport = new StepReportDTO(step, TestStatus.PASSED);
        stepReports.add(stepReport);
    }

    @Override
    public void ignorable(String step) {

        StoryReportDTO model = threadLocalState.get();
        ScenarioReportDTO scenarioTestReportModel = model.getScenarioTestReportDTOs().get(model.getScenarioTestReportDTOs().size() - 1);
        List<StepReportDTO> stepReports = scenarioTestReportModel.getStepTestReportModels();

        StepReportDTO stepReport = new StepReportDTO(step, TestStatus.IGNORED);
        stepReports.add(stepReport);

    }

    @Override
    public void pending(String step) {

        StoryReportDTO model = threadLocalState.get();
        ScenarioReportDTO scenarioTestReportModel = model.getScenarioTestReportDTOs().get(model.getScenarioTestReportDTOs().size() - 1);
        List<StepReportDTO> stepReports = scenarioTestReportModel.getStepTestReportModels();

        StepReportDTO stepReport = new StepReportDTO(step, TestStatus.PENDING);
        stepReports.add(stepReport);
    }

    @Override
    public void notPerformed(String step) {

        StoryReportDTO model = threadLocalState.get();
        ScenarioReportDTO scenarioTestReportModel = model.getScenarioTestReportDTOs().get(model.getScenarioTestReportDTOs().size() - 1);
        List<StepReportDTO> stepReports = scenarioTestReportModel.getStepTestReportModels();

        StepReportDTO stepReport = new StepReportDTO(step, TestStatus.NOT_PERFORMED);
        stepReports.add(stepReport);
    }

    @Override
    public void failed(String step, Throwable cause) {

        StoryReportDTO model = threadLocalState.get();
        ScenarioReportDTO scenarioTestReportModel = model.getScenarioTestReportDTOs().get(model.getScenarioTestReportDTOs().size() - 1);
        List<StepReportDTO> stepReports = scenarioTestReportModel.getStepTestReportModels();

        StepReportDTO stepReport = new StepReportDTO(step, TestStatus.FAILED);
        stepReports.add(stepReport);
    }

    @Override
    public void failedOutcomes(String step, OutcomesTable table) {
        log("-> failedOutcomes, step - " + step + ", table - " + table);
    }

    @Override
    public void restarted(String step, Throwable cause) {
        log("-> restarted, step - " + step + ", cause - " + cause);
    }

    @Override
    public void dryRun() {
        log("-> dryRun");
    }

    @Override
    public void pendingMethods(List<String> methods) {
        log("-> pendingMethods, methods - " + methods);
    }

    private void log(String msg) {
        System.out.println(msg);
    }

    public String getJiraBaseUrl() {
        return jiraBaseUrl;
    }

    public void setJiraBaseUrl(String jiraBaseUrl) {
        this.jiraBaseUrl = jiraBaseUrl;
    }
}
