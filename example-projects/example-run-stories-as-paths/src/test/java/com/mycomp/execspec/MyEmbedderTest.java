package com.mycomp.execspec;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.Test;

import java.util.List;

/**
 * Created by Dmytro on 2/15/14.
 */
public class MyEmbedderTest extends JUnitStories {

    private final String jiraBaseUrl = "http://localhost:2990/jira";

    private final String jiraProject = "TESTING";

    public MyEmbedderTest() {
//        StepMonitor delegate = configuration().stepMonitor();
//        ContextStepMonitor contextStepMonitor = new ContextStepMonitor(context, contextView, delegate);
//        super.configuration().useStepMonitor(contextStepMonitor);

        useConfiguration(new MostUsefulConfiguration());

        configuration().useStoryReporterBuilder(
                new StoryReporterBuilder() {
                    public StoryReporter reporterFor(String storyPath, org.jbehave.core.reporters.Format format) {
                        if (format.equals(org.jbehave.core.reporters.Format.HTML)) {
                            return new JiraHtmlOutput(jiraBaseUrl, jiraProject, "DEV");
                        } else {
                            return super.reporterFor(storyPath, format);
                        }
                    }
                }
//                        .withReporters(new CustomHtmlReporter(configuration().keywords()))
                        .withFailureTrace(true)
//                .withCrossReference(xref)
                        .withFormats(
//                                Format.CONSOLE,
                                Format.HTML
//                                Format.TXT,
//                                Format.XML,
//                                Format.STATS
                        )
        );

//        configuration().storyReporterBuilder().withFormats(
//                StoryReporterBuilder.Format.HTML,
//                StoryReporterBuilder.Format.TXT,
//                StoryReporterBuilder.Format.XML,
//                StoryReporterBuilder.Format.CONSOLE,
//                StoryReporterBuilder.Format.IDE_CONSOLE,
//                StoryReporterBuilder.Format.STATS);

//        CrossReference crossReference = new CrossReference();
//        configuration().storyReporterBuilder().withCrossReference(crossReference);

        JiraStoryLoader jiraLoader = new JiraStoryLoader();
        jiraLoader.setJiraBaseUrl(jiraBaseUrl);
        jiraLoader.setJiraProject(jiraProject);
        configuration().useStoryLoader(jiraLoader);


//        configuration().vi
//        JiraStoryReporter jiraStoryReporter = new JiraStoryReporter();
//        jiraStoryReporter.setJiraBaseUrl(jiraBaseUrl);
//        PrintStream printStream = null;
//        try {
//            printStream = new FilePrintStreamFactory.FilePrintStream(new File("story-report.txt"), false);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

//        HtmlOutput htmlReporter = new HtmlOutput(printStream);
//        DelegatingStoryReporter dsr = new DelegatingStoryReporter(
//                htmlReporter,
//                jiraStoryReporter);

//        configuration().storyReporterBuilder().withReporters(dsr);

//        configuration().useStepFinder(new ExtendedStepFinder());

        JiraStepDocReporter stepDocReporter = new JiraStepDocReporter(jiraBaseUrl, jiraProject);
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
//            List<StepCandidate> stepCandidates = candidateSteps.get(0).listCandidates();
//            StepCandidate stepCandidate = stepCandidates.get(0);
//            stepCandidate.get
            embedder.reportStepdocs(configuration(), candidateSteps);
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
