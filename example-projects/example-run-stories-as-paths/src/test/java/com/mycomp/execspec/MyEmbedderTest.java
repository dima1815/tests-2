package com.mycomp.execspec;

import org.jbehave.core.context.Context;
import org.jbehave.core.context.ContextView;
import org.jbehave.core.context.JFrameContextView;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.*;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.StepCandidate;
import org.junit.Test;

import java.util.List;

/**
 * Created by Dmytro on 2/15/14.
 */
public class MyEmbedderTest extends JUnitStories {

    private final String jiraBaseUrl = "http://localhost:2990/jira";

    public MyEmbedderTest() {
        Context context = new Context();
        Format contextFormat = new ContextOutput(context);
        ContextView contextView = new JFrameContextView().sized(640, 120);
//        StepMonitor delegate = configuration().stepMonitor();
//        ContextStepMonitor contextStepMonitor = new ContextStepMonitor(context, contextView, delegate);
//        super.configuration().useStepMonitor(contextStepMonitor);
        configuration().storyReporterBuilder().withFormats(
                StoryReporterBuilder.Format.HTML,
                StoryReporterBuilder.Format.TXT,
                StoryReporterBuilder.Format.XML,
                StoryReporterBuilder.Format.CONSOLE,
                StoryReporterBuilder.Format.IDE_CONSOLE,
                StoryReporterBuilder.Format.STATS);
        CrossReference crossReference = new CrossReference();
        configuration().storyReporterBuilder().withCrossReference(crossReference);

        JiraStoryLoader jiraLoader = new JiraStoryLoader();
        jiraLoader.setJiraBaseUrl(jiraBaseUrl + "/rest/story-res/1.0/story/find");
        configuration().useStoryLoader(jiraLoader);

        StoryReporter jiraStoryReporter = new JiraStoryReporter();
        configuration().storyReporterBuilder().withReporters(jiraStoryReporter);

        JiraStepDocReporter stepDocReporter = new JiraStepDocReporter();
        configuration().useStepdocReporter(stepDocReporter);

    }

    @Test
    public void run() throws Throwable {
        Embedder embedder = configuredEmbedder();
        try {
            embedder.runStoriesAsPaths(storyPaths());
        } finally {
            embedder.generateCrossReference();

            List<CandidateSteps> candidateSteps = embedder.stepsFactory().createCandidateSteps();
            List<StepCandidate> stepCandidates = candidateSteps.get(0).listCandidates();
            System.out.println("stepCandidates = " + stepCandidates);

//            List<CandidateSteps> candidateSteps = embedder.stepsFactory().createCandidateSteps();
//            embedder.reportStepdocs(configuration(), candidateSteps);
//            embedder.reportStepdocs();
//            embedder.reportMatchingStepdocs("Given *");
//            embedder.reportMatchingStepdocs("When *");
//            embedder.reportMatchingStepdocs("Then *");
        }
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new MyStepsMain(), new MyStepsMain2());
    }

    @Override
    protected List<String> storyPaths() {

        JiraStoryFinder storyFinder = new JiraStoryFinder();

        List<String> includes = null;
        List<String> excludes = null;
        List<String> paths = null;
        String projectKey = "TESTING";
        paths = storyFinder.findPaths(jiraBaseUrl, projectKey, includes, excludes);

        System.out.println("Found paths are:\n" + paths);

        return paths;
    }
}
