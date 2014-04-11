package com.mycomp.execspec.jiraplugin.dto;

import com.mycomp.execspec.jiraplugin.ao.story.Scenario;
import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.dto.story.out.*;
import com.mycomp.execspec.jiraplugin.dto.story.out.step.ExamplesTableTokenDTO;
import com.mycomp.execspec.jiraplugin.dto.story.out.step.StepDTO;
import com.mycomp.execspec.jiraplugin.dto.story.out.step.TokenKind;
import org.apache.commons.lang.Validate;
import org.jbehave.core.model.*;
import org.jbehave.core.parsers.RegexStoryParser;

import java.util.*;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StoryDTOUtils {

    public static List<StoryDTO> toDTO(List<? extends Story> stories) {

        List<StoryDTO> storyModels = new ArrayList<StoryDTO>(stories.size());
        for (Story story : stories) {
            StoryDTO storyModel = toDTO(story);
            storyModels.add(storyModel);
        }
        return storyModels;
    }

    public static StoryDTO toDTO(Story story) {

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

        NarrativeDTO narrative = fromJBehaveNarrative(story.getNarrative(), jbehaveStory.getNarrative());
        MetaDTO meta = fromJBehaveMeta(jbehaveStory.getMeta());
        GivenStoriesDTO givenStories = fromJBehaveGivenStories(jbehaveStory.getGivenStories());
        LifecycleDTO lifecycle = fromJBehaveLifeCycle(jbehaveStory.getLifecycle());
        List<ScenarioDTO> scenarios = fromJBehaveScenarios(jbehaveStory.getScenarios());

        StoryDTO storyModel = new StoryDTO(projectKey, issueKey, story.getVersion(), narrative, meta, givenStories, lifecycle, scenarios);

        return storyModel;
    }

    private static List<ScenarioDTO> fromJBehaveScenarios(List<org.jbehave.core.model.Scenario> scenarios) {

        List<ScenarioDTO> models = new ArrayList<ScenarioDTO>(scenarios.size());

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
            MetaDTO metaModel = new MetaDTO(properties);

            // GivenStories
            GivenStories givenStories = scenario.getGivenStories();
            List<GivenStory> stories = givenStories.getStories();
            List<GivenStoryDTO> storiesModel = new ArrayList<GivenStoryDTO>(stories.size());
            for (GivenStory story : stories) {
                String givenStoryAsString = story.asString();
                String path = story.getPath();
                GivenStoryDTO givenStoryModel = new GivenStoryDTO(givenStoryAsString, path);
                storiesModel.add(givenStoryModel);
            }
            GivenStoriesDTO givenStoriesModel = new GivenStoriesDTO(asString, storiesModel);

            // Steps
            List<String> steps = scenario.getSteps();
            List<StepDTO> stepsModel = new ArrayList<StepDTO>(steps.size());
            for (String step : steps) {
                List<IToken> tokens = parseStepIntoTokens(step);
                StepDTO stepModel = new StepDTO(step, tokens);
                stepsModel.add(stepModel);
            }

            ExamplesTable examplesTable = scenario.getExamplesTable();
            String examplesTableAsString = examplesTable.asString();
            String headerSeparator = examplesTable.getHeaderSeparator();
            String valueSeparator = examplesTable.getValueSeparator();
            String ignorableSeparator = "NONE";
            ArrayList<String> headers = new ArrayList<String>(examplesTable.getHeaders());

            List<Map<String, String>> data = examplesTable.getRows();
            ArrayList<ArrayList<String>> dataAsList = new ArrayList<ArrayList<String>>(data.size());
            for (Map<String, String> rowDataMap : data) {
                ArrayList<String> rowDataList = new ArrayList<String>(rowDataMap.size());
                for (String header : headers) {
                    String value = rowDataMap.get(header);
                    rowDataList.add(value);
                }
                dataAsList.add(rowDataList);
            }

            ExamplesTableDTO examplesTableModel = new ExamplesTableDTO(examplesTableAsString, headerSeparator, valueSeparator,
                    ignorableSeparator, headers, dataAsList);

            ScenarioDTO scenarioModel = new ScenarioDTO(asString, title, metaModel, givenStoriesModel, stepsModel, examplesTableModel);
            models.add(scenarioModel);
        }

        return models;
    }

    private static LifecycleDTO fromJBehaveLifeCycle(Lifecycle lifecycle) {
        List<StepDTO> beforeSteps;
        {
            List<String> jbehaveBeforeSteps = lifecycle.getBeforeSteps();
            beforeSteps = new ArrayList<StepDTO>(jbehaveBeforeSteps.size());
            for (String jbehaveBeforeStep : jbehaveBeforeSteps) {
                String asString = jbehaveBeforeStep;
                List<IToken> stepTokens = parseStepIntoTokens(jbehaveBeforeStep);
                StepDTO stepModel = new StepDTO(asString, stepTokens);
                beforeSteps.add(stepModel);
            }
        }
        List<StepDTO> afterSteps;
        {
            List<String> jbehaveAfterSteps = lifecycle.getAfterSteps();
            afterSteps = new ArrayList<StepDTO>(jbehaveAfterSteps.size());
            for (String jbehaveAfterStep : jbehaveAfterSteps) {
                String asString = jbehaveAfterStep;
                List<IToken> stepTokens = parseStepIntoTokens(jbehaveAfterStep);
                StepDTO stepModel = new StepDTO(asString, stepTokens);
                afterSteps.add(stepModel);
            }
        }
        LifecycleDTO lifecycleModel = new LifecycleDTO(beforeSteps, afterSteps);
        return lifecycleModel;
    }

    private static List<IToken> parseStepIntoTokens(String stepAsString) {

//        Map<String, String> parameters = new HashMap<String, String>();
//
//        StepCandidate stepCandidate = null;
//        boolean matches = stepCandidate.matches(jbehaveBeforeStep);
//        Step matchedStep = stepCandidate.createMatchedStep(jbehaveBeforeStep, parameters);

        List<IToken> tokens = new ArrayList<IToken>();

        String[] keywords = {"Given", "When", "Then", "And"};
        for (String keyword : keywords) {
            if (stepAsString.startsWith(keyword)) {
                tokens.add(new TypedTextTokenDTO(TokenKind.keyword, keyword));
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
                    tokens.add(new TypedTextTokenDTO(TokenKind.text, stepText));
                    stepText = "";
                }
                // found a table line
                tableLines.add(line);
            } else {
                if (!tableLines.isEmpty()) {
                    // table ended we need to parse it
                    ExamplesTableTokenDTO tableToken = parseTableLinesIntoToken(tableLines);
                    tokens.add(tableToken);
                    tableLines.clear();
                }
                stepText += line + "\n";
            }
        }

        if (!stepText.isEmpty()) {
            tokens.add(new TypedTextTokenDTO(TokenKind.text, stepText));
        } else if (!tableLines.isEmpty()) {
            // last step line was a table line
            ExamplesTableTokenDTO tableToken = parseTableLinesIntoToken(tableLines);
            tokens.add(tableToken);
        }

        return tokens;
    }

    private static ExamplesTableTokenDTO parseTableLinesIntoToken(List<String> tableLines) {

        String asString = "TODO";

        String headerSeparator = "|";
        String valueSeparator = "|";
        String ignorableSeparator = "NONE";

        String headerLine = tableLines.get(0);
        Validate.isTrue(headerLine.startsWith("|"));
        Validate.isTrue(headerLine.length() > 1);
        // trim the leading '|' so that it doesn't end up in tokens
        headerLine = headerLine.substring(1);
        ArrayList<String> headers = new ArrayList<String>(Arrays.asList(headerLine.split("\\" + headerSeparator)));
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>(tableLines.size() - 1);
        for (int i = 1; i < tableLines.size(); i++) {
            String tableRow = tableLines.get(i);
            Validate.isTrue(tableRow.startsWith("|"));
            Validate.isTrue(tableRow.length() > 1);
            tableRow = tableRow.substring(1);
            String[] values = tableRow.split("\\" + valueSeparator);
            ArrayList<String> rowValues = new ArrayList<String>(Arrays.asList(values));
            data.add(rowValues);
        }

        ExamplesTableDTO exampleTablesModel = new ExamplesTableDTO(asString, headerSeparator, valueSeparator, ignorableSeparator, headers, data);
        ExamplesTableTokenDTO examplesTableToken = new ExamplesTableTokenDTO(asString, exampleTablesModel);

        return examplesTableToken;
    }

    private static GivenStoriesDTO fromJBehaveGivenStories(GivenStories givenStories) {
        String asString = givenStories.asString();
        List<GivenStory> stories = givenStories.getStories();
        List<GivenStoryDTO> givenStoriesList = new ArrayList<GivenStoryDTO>(stories.size());
        for (GivenStory story : stories) {
            String storyModelAsString = story.asString();
            String path = story.getPath();
            GivenStoryDTO givenStoryModel = new GivenStoryDTO(storyModelAsString, path);
            givenStoriesList.add(givenStoryModel);
        }
        GivenStoriesDTO givenStoriesModel = new GivenStoriesDTO(asString, givenStoriesList);
        return givenStoriesModel;
    }

    private static MetaDTO fromJBehaveMeta(Meta meta) {
        Properties properties = new Properties();
        Set<String> propertyNames = meta.getPropertyNames();
        for (String propertyName : propertyNames) {
            String property = meta.getProperty(propertyName);
            properties.put(propertyName, property);
        }
        MetaDTO metaModel = new MetaDTO(properties);
        return metaModel;
    }

    private static NarrativeDTO fromJBehaveNarrative(String asString, Narrative narrative) {

        String label = "Narrative:";
        String inOrderTo = narrative.inOrderTo();
        String asA = narrative.asA();
        String iWantTo = narrative.iWantTo();
        String soThat = narrative.soThat();
        NarrativeDTO nm = new NarrativeDTO(asString, label, inOrderTo, asA, iWantTo, soThat);
        return nm;
    }

    private static String asTextStory(StoryDTO storyModel) {

        StringBuilder sb = new StringBuilder();
        final String LINE_BREAK = "\n";

        // narrative
        NarrativeDTO narrative = storyModel.getNarrative();
        String narrativeText = narrative.getAsString();
        sb.append(narrativeText);
        sb.append(LINE_BREAK);

        // scenarios
        List<ScenarioDTO> scenarios = storyModel.getScenarios();
        for (ScenarioDTO scenario : scenarios) {
            String scenarioText = scenario.getAsString();
            sb.append(scenarioText);
            sb.append(LINE_BREAK);
        }

        return sb.toString();
    }

}
