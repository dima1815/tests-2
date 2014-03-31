package com.mycomp.execspec.jiraplugin.dto;

import com.mycomp.execspec.jiraplugin.ao.Scenario;
import com.mycomp.execspec.jiraplugin.ao.Story;
import com.mycomp.execspec.jiraplugin.dto.model.*;
import com.mycomp.execspec.jiraplugin.dto.model.step.StepModel;
import com.mycomp.execspec.jiraplugin.dto.model.step.Token;
import com.mycomp.execspec.jiraplugin.dto.model.step.TokenKind;
import com.mycomp.execspec.jiraplugin.dto.model.step.TypedTextToken;
import org.jbehave.core.model.*;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCandidate;

import java.util.*;

/**
 * Contains utility methods useful while working with model objects.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
public class ModelUtils {

    public static List<StoryModel> toModels(List<? extends Story> stories) {

        List<StoryModel> storyModels = new ArrayList<StoryModel>(stories.size());
        for (Story story : stories) {
            StoryModel storyModel = toModel(story);
            storyModels.add(storyModel);
        }
        return storyModels;
    }

    public static StoryModel toModel(Story story) {

        String issueKey = story.getIssueKey();
        String projectKey = story.getProjectKey();
        Scenario[] scenariosObjs = story.getScenarios();

        StringBuilder storyAsString = new StringBuilder();
        storyAsString.append(story.getNarrative());
        storyAsString.append("\n");
        storyAsString.append("\n");

        for (Scenario scenariosObj : scenariosObjs) {
            String text = scenariosObj.getText();
            storyAsString.append(text);
            storyAsString.append("\n");
            storyAsString.append("\n");
        }
        String strStory = storyAsString.toString();

        RegexStoryParser parser = new RegexStoryParser();
        org.jbehave.core.model.Story jbehaveStory = parser.parseStory(strStory);


        NarrativeModel narrative = fromJBehaveNarrative(story.getNarrative(), jbehaveStory.getNarrative());
        MetaModel meta = fromJBehaveMeta(jbehaveStory.getMeta());
        GivenStoriesModel givenStories = fromJBehaveGivenStories(jbehaveStory.getGivenStories());
        LifecycleModel lifecycle = fromJBehaveLifeCycle(jbehaveStory.getLifecycle());
        List<ScenarioModel> scenarios = fromJBehaveScenarios(jbehaveStory.getScenarios());
        StoryModel storyModel = new StoryModel(projectKey, issueKey, narrative, meta, givenStories, lifecycle, scenarios);

        return storyModel;
    }

    private static List<ScenarioModel> fromJBehaveScenarios(List<org.jbehave.core.model.Scenario> scenarios) {
        throw new RuntimeException("Not implemented");
    }

    private static LifecycleModel fromJBehaveLifeCycle(Lifecycle lifecycle) {
        List<StepModel> beforeSteps;
        {
            List<String> jbehaveBeforeSteps = lifecycle.getBeforeSteps();
            beforeSteps = new ArrayList<StepModel>(jbehaveBeforeSteps.size());
            for (String jbehaveBeforeStep : jbehaveBeforeSteps) {
                String asString = jbehaveBeforeStep;
                List<Token> stepTokens = parseStepIntoTokens(jbehaveBeforeStep);
                StepModel stepModel = new StepModel(asString, stepTokens);
                beforeSteps.add(stepModel);
            }
        }
        List<StepModel> afterSteps;
        {
            List<String> jbehaveAfterSteps = lifecycle.getAfterSteps();
            afterSteps = new ArrayList<StepModel>(jbehaveAfterSteps.size());
            for (String jbehaveAfterStep : jbehaveAfterSteps) {
                String asString = jbehaveAfterStep;
                List<Token> stepTokens = parseStepIntoTokens(jbehaveAfterStep);
                StepModel stepModel = new StepModel(asString, stepTokens);
                afterSteps.add(stepModel);
            }
        }
        LifecycleModel lifecycleModel = new LifecycleModel(beforeSteps, afterSteps);
        return lifecycleModel;
    }

    private static List<Token> parseStepIntoTokens(String jbehaveBeforeStep) {

        StepCandidate stepCandidate = null;
        Map<String, String> parameters = new HashMap<String, String>();

        stepCandidate.matches(jbehaveBeforeStep);
        Step matchedStep = stepCandidate.createMatchedStep(jbehaveBeforeStep, parameters);


        List<Token> tokens = new ArrayList<Token>();
        {
            String[] keywords = {"Given", "When", "Then"};
            String[] lines = jbehaveBeforeStep.split("\\n");
            for (String line : lines) {
                boolean startsWithKeyword = false;
                for (String keyword : keywords) {
                    if (line.startsWith(keyword)) {
                        startsWithKeyword = true;
                        tokens.add(new TypedTextToken(TokenKind.keyword, keyword));
                        String remainder = line.substring(keyword.length());
                        String tokenText = remainder + "\n";
                        tokens.add(new TypedTextToken(TokenKind.text, tokenText));
                        break;
                    }
                }
                if (!startsWithKeyword) {
                    if (line.startsWith("!--")) {
                        String tokenText = line + "\n";
                        tokens.add(new TypedTextToken(TokenKind.comment, line));
                    } else if (line.startsWith("")) {
                    } else {

                    }
                }
            }
        }
        return tokens;
    }

    private static GivenStoriesModel fromJBehaveGivenStories(GivenStories givenStories) {
        String asString = givenStories.asString();
        List<GivenStory> stories = givenStories.getStories();
        List<GivenStoryModel> givenStoriesList = new ArrayList<GivenStoryModel>(stories.size());
        for (GivenStory story : stories) {
            String storyModelAsString = story.asString();
            String path = story.getPath();
            GivenStoryModel givenStoryModel = new GivenStoryModel(storyModelAsString, path);
            givenStoriesList.add(givenStoryModel);
        }
        GivenStoriesModel givenStoriesModel = new GivenStoriesModel(asString, givenStoriesList);
        return givenStoriesModel;
    }

    private static MetaModel fromJBehaveMeta(Meta meta) {
        Properties properties = new Properties();
        Set<String> propertyNames = meta.getPropertyNames();
        for (String propertyName : propertyNames) {
            String property = meta.getProperty(propertyName);
            properties.put(propertyName, property);
        }
        MetaModel metaModel = new MetaModel(properties);
        return metaModel;
    }

    private static NarrativeModel fromJBehaveNarrative(String asString, Narrative narrative) {

        String label = "Narrative:";
        String inOrderTo = narrative.inOrderTo();
        String asA = narrative.asA();
        String iWantTo = narrative.iWantTo();
        String soThat = narrative.soThat();
        NarrativeModel nm = new NarrativeModel(asString, label, inOrderTo, asA, iWantTo, soThat);
        return nm;
    }


    public static String asTextStory(StoryModel storyModel) {

        StringBuilder sb = new StringBuilder();
        final String LINE_BREAK = "\n";

        // narrative
        NarrativeModel narrative = storyModel.getNarrative();
        String narrativeText = narrative.getAsString();
        sb.append(narrativeText);
        sb.append(LINE_BREAK);

        // scenarios
        List<ScenarioModel> scenarios = storyModel.getScenarios();
        for (ScenarioModel scenario : scenarios) {
            String scenarioText = scenario.getAsString();
            sb.append(scenarioText);
            sb.append(LINE_BREAK);
        }

        return sb.toString();
    }
}
