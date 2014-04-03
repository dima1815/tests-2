package com.mycomp.execspec.jiraplugin.dto;

import com.mycomp.execspec.jiraplugin.ao.Scenario;
import com.mycomp.execspec.jiraplugin.ao.Story;
import com.mycomp.execspec.jiraplugin.dto.model.*;
import com.mycomp.execspec.jiraplugin.dto.model.step.*;
import org.apache.commons.lang.Validate;
import org.jbehave.core.model.*;
import org.jbehave.core.parsers.RegexStoryParser;

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

        List<ScenarioModel> models = new ArrayList<ScenarioModel>(scenarios.size());

        for (org.jbehave.core.model.Scenario scenario : scenarios) {

            String asString = "[TODO]";
            String title = scenario.getTitle();

            // Meta
            Meta meta = scenario.getMeta();
            Properties properties = new Properties();
            Set<String> propertyNames = meta.getPropertyNames();
            for (String propertyName : propertyNames) {
                String property = meta.getProperty(propertyName);
                properties.put(propertyName, property);
            }
            MetaModel metaModel = new MetaModel(properties);

            // GivenStories
            GivenStories givenStories = scenario.getGivenStories();
            List<GivenStory> stories = givenStories.getStories();
            List<GivenStoryModel> storiesModel = new ArrayList<GivenStoryModel>(stories.size());
            for (GivenStory story : stories) {
                String givenStoryAsString = story.asString();
                String path = story.getPath();
                GivenStoryModel givenStoryModel = new GivenStoryModel(givenStoryAsString, path);
                storiesModel.add(givenStoryModel);
            }
            GivenStoriesModel givenStoriesModel = new GivenStoriesModel(asString, storiesModel);

            // Steps
            List<String> steps = scenario.getSteps();
            List<StepModel> stepsModel = new ArrayList<StepModel>(steps.size());
            for (String step : steps) {
                List<Token> tokens = parseStepIntoTokens(step);
                StepModel stepModel = new StepModel(step, tokens);
                stepsModel.add(stepModel);
            }

            ExamplesTable examplesTable = scenario.getExamplesTable();
            String examplesTableAsString = examplesTable.asString();
            String headerSeparator = examplesTable.getHeaderSeparator();
            String valueSeparator = examplesTable.getValueSeparator();
            String ignorableSeparator = "NONE";
            List<String> headers = examplesTable.getHeaders();

            List<Map<String, String>> data = examplesTable.getRows();
            List<List<String>> dataAsList = new ArrayList<List<String>>(data.size());
            for (Map<String, String> rowDataMap : data) {
                List<String> rowDataList = new ArrayList<String>(rowDataMap.size());
                for (String header : headers) {
                    String value = rowDataMap.get(header);
                    rowDataList.add(value);
                }
                dataAsList.add(rowDataList);
            }

            ExamplesTableModel examplesTableModel = new ExamplesTableModel(examplesTableAsString, headerSeparator, valueSeparator,
                    ignorableSeparator, headers, dataAsList);

            ScenarioModel scenarioModel = new ScenarioModel(asString, title, metaModel, givenStoriesModel, stepsModel, examplesTableModel);
            models.add(scenarioModel);
        }

        return models;
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

    private static List<Token> parseStepIntoTokens(String stepAsString) {

//        Map<String, String> parameters = new HashMap<String, String>();
//
//        StepCandidate stepCandidate = null;
//        boolean matches = stepCandidate.matches(jbehaveBeforeStep);
//        Step matchedStep = stepCandidate.createMatchedStep(jbehaveBeforeStep, parameters);

        List<Token> tokens = new ArrayList<Token>();

        String[] keywords = {"Given", "When", "Then", "And"};
        for (String keyword : keywords) {
            if (stepAsString.startsWith(keyword)) {
                tokens.add(new TypedTextToken(TokenKind.keyword, keyword));
                stepAsString = stepAsString.substring(keyword.length());
                break;
            }
        }

        String stepText = "";
        List<String> tableLines = new ArrayList<String>();

        // extract any table parameters
        String[] lines = stepAsString.split("\\n");
        for (String line : lines) {
            if (line.startsWith("|")) {
                if (!stepText.isEmpty()) {
                    tokens.add(new TypedTextToken(TokenKind.text, stepText));
                    stepText = "";
                }
                // found a table line
                tableLines.add(line);
            } else {
                if (!tableLines.isEmpty()) {
                    // table ended we need to parse it
                    ExamplesTableToken tableToken = parseTableLinesIntoToken(tableLines);
                    tokens.add(tableToken);
                    tableLines.clear();
                }
                stepText += line + "\n";
            }
        }

        if (!stepText.isEmpty()) {
            tokens.add(new TypedTextToken(TokenKind.text, stepText));
        } else if (!tableLines.isEmpty()) {
            // last step line was a table line
            ExamplesTableToken tableToken = parseTableLinesIntoToken(tableLines);
            tokens.add(tableToken);
        }

        return tokens;
    }

    private static ExamplesTableToken parseTableLinesIntoToken(List<String> tableLines) {

        String asString = "TODO";

        String headerSeparator = "|";
        String valueSeparator = "|";
        String ignorableSeparator = "NONE";

        String headerLine = tableLines.get(0);
        Validate.isTrue(headerLine.startsWith("|"));
        Validate.isTrue(headerLine.length() > 1);
        // trim the leading '|' so that it doesn't end up in tokens
        headerLine = headerLine.substring(1);
        List<String> headers = Arrays.asList(headerLine.split("\\" + headerSeparator));
        List<List<String>> data = new ArrayList<List<String>>(tableLines.size() - 1);
        for (int i = 1; i < tableLines.size(); i++) {
            String tableRow = tableLines.get(i);
            Validate.isTrue(tableRow.startsWith("|"));
            Validate.isTrue(tableRow.length() > 1);
            tableRow = tableRow.substring(1);
            String[] values = tableRow.split("\\" + valueSeparator);
            List<String> rowValues = Arrays.asList(values);
            data.add(rowValues);
        }

        ExamplesTableModel exampleTablesModel = new ExamplesTableModel(asString, headerSeparator, valueSeparator, ignorableSeparator, headers, data);
        ExamplesTableToken examplesTableToken = new ExamplesTableToken(asString, exampleTablesModel);

        return examplesTableToken;
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
