package com.mycomp.execspec;

import org.jbehave.core.model.*;
import org.jbehave.core.reporters.StoryReporter;

import java.util.List;
import java.util.Map;

/**
 * Created by Dmytro on 2/26/14.
 */
public class JiraStoryReporter implements StoryReporter {


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
    }

    @Override
    public void afterStory(boolean givenStory) {
        log("-> afterStory, givenStory - " + givenStory);
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
        log("-> beforeScenario, scenarioTitle - " + scenarioTitle);
    }

    @Override
    public void scenarioMeta(Meta meta) {
        log("-> scenarioMeta, meta - " + meta);
    }

    @Override
    public void afterScenario() {
        log("-> afterScenario");
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
        log("-> successful, step - " + step);
    }

    @Override
    public void ignorable(String step) {
        log("-> ignorable, step - " + step);
    }

    @Override
    public void pending(String step) {
        log("-> pending, step - " + step);
    }

    @Override
    public void notPerformed(String step) {
        log("-> notPerformed, step - " + step);
    }

    @Override
    public void failed(String step, Throwable cause) {
        log("-> failed, step - " + step + ", cause - " + cause);
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
}
