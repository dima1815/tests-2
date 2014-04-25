package com.mycomp.execspec.jiraplugin.dto.story;

import com.mycomp.execspec.jiraplugin.ao.story.Scenario;
import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import com.mycomp.execspec.jiraplugin.dto.story.output.*;
import com.mycomp.execspec.jiraplugin.dto.story.output.step.BaseToken;
import com.mycomp.execspec.jiraplugin.dto.story.output.step.ExamplesTableTokenDTO;
import com.mycomp.execspec.jiraplugin.dto.story.output.step.StepDTO;
import com.mycomp.execspec.jiraplugin.dto.story.output.step.TokenKind;
import org.apache.commons.lang.Validate;
import org.jbehave.core.model.*;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.steps.StepCreator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StoryDTOUtils {

    public static List<StoryDTO> toDTO(List<? extends Story> stories, List<StepDocDTO> stepDocs) {

        List<StoryDTO> storyModels = new ArrayList<StoryDTO>(stories.size());
        for (Story story : stories) {
            StoryDTO storyModel = toDTO(story, stepDocs);
            storyModels.add(storyModel);
        }
        return storyModels;
    }

    public static StoryDTO toDTO(Story story, List<StepDocDTO> stepDocs) {

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
        org.jbehave.core.model.Story jbehaveStory = parser.parseStory(strStory, story.getIssueKey());

        NarrativeDTO narrative = fromJBehaveNarrative(story.getNarrative(), jbehaveStory.getNarrative());
        MetaDTO meta = fromJBehaveMeta(jbehaveStory.getMeta());
        GivenStoriesDTO givenStories = fromJBehaveGivenStories(jbehaveStory.getGivenStories());
        LifecycleDTO lifecycle = fromJBehaveLifeCycle(jbehaveStory.getLifecycle(), stepDocs);
        List<ScenarioDTO> scenarios = fromJBehaveScenarios(jbehaveStory.getScenarios(), stepDocs);

        StoryDTO storyModel = new StoryDTO(projectKey, issueKey, story.getVersion(), narrative, meta, givenStories, lifecycle, scenarios);

        String asHTML = toHTML(jbehaveStory, stepDocs);
        storyModel.setAsHTML(asHTML);

        return storyModel;
    }

    private static String toHTML(org.jbehave.core.model.Story story, List<StepDocDTO> stepDocs) {

        CustomHtmlReporter reporter = new CustomHtmlReporter();

        reporter.beforeStory(story, false);
        reporter.narrative(story.getNarrative());
        reporter.lifecyle(story.getLifecycle());
        List<String> givenStoriesPaths = story.getGivenStories().getPaths();
        if (!givenStoriesPaths.isEmpty()) {
            reporter.givenStories(givenStoriesPaths);
        }

        List<org.jbehave.core.model.Scenario> scenarios = story.getScenarios();
        for (org.jbehave.core.model.Scenario scenario : scenarios) {

            reporter.beforeScenario(scenario.getTitle());
            reporter.scenarioMeta(scenario.getMeta());
            GivenStories givenStories = scenario.getGivenStories();
            List<String> givenPaths = givenStories.getPaths();
            if (!givenPaths.isEmpty()) {
                reporter.givenStories(givenPaths);
            }

            List<String> steps = scenario.getSteps();
            for (String step : steps) {

                boolean stepDocFound = false;
                for (StepDocDTO stepDocDTO : stepDocs) {
                    String startingWord = stepDocDTO.getStartingWord();
                    String startingPrefix = startingWord + " ";
                    if (step.startsWith(startingPrefix)) {
                        String stepWithoutKeyword = step.substring(startingPrefix.length());
                        String regExpPattern = stepDocDTO.getRegExpPattern();
                        Pattern pattern = Pattern.compile(regExpPattern, Pattern.DOTALL);
                        Matcher matcher = pattern.matcher(stepWithoutKeyword);
                        boolean matches = matcher.matches();
                        if (matches) {
                            step = startingPrefix + insertParameterMarkers(stepWithoutKeyword, matcher);
                            stepDocFound = true;
                            break;
                        }
                    }
                }

                if (stepDocFound) {
                    reporter.successful(step);
                } else {
                    reporter.pending(step);
                }

            }

            // TODO - implement examples table, i.e. scenarios parametrised with examples table
            // should the steps be passed to the reporter individually like above or would it be enough to just call
            // the beforeExamples method of the reporter
//            ExamplesTable examplesTable = scenario.getExamplesTable();
//            reporter.beforeExamples(steps, examplesTable);

            reporter.afterScenario();
        }

        reporter.afterStory(false);

        String storyAsHtml = reporter.getStoryReport().trim();
        return storyAsHtml;
    }

    private static String insertParameterMarkers(String step, Matcher matcher) {

        StringBuilder sb = new StringBuilder();

        int totalGroups = matcher.groupCount();

        if (totalGroups > 0) {

            // has parameters, which are matcher's groups
            int pos = 0;
            for (int i = 1; i <= totalGroups; i++) {
                int groupStart = matcher.start(i);
                String group = matcher.group(i);
                if (groupStart > pos) {
                    // append the string up until the group start
                    String str = step.substring(pos, groupStart);
                    sb.append(str);
                    // append the current group as a parameter
                    appendParameter(sb, group);
                    // update position
                    pos = groupStart + group.length();
                } else {
                    // we are at the start of the group already
                    appendParameter(sb, group);
                    // update position
                    pos = groupStart + group.length();
                }
            }
            // append any characters which maybe after the last parameter and hence after the last matcher group
            if (pos < step.length()) {
                String str = step.substring(pos);
                sb.append(str);
            }

        } else {
            // doesn't have parameters
            sb.append(step);
        }

        return sb.toString();
    }

    private static void appendParameter(StringBuilder sb, String group) {
        String trimmedGroup = group.trim();
        if (trimmedGroup.startsWith("|") && trimmedGroup.endsWith("|")) {
            // is a table parameter
            sb.append(StepCreator.PARAMETER_TABLE_START);
            sb.append(group);
            sb.append(StepCreator.PARAMETER_TABLE_END);
        } else {
            sb.append(StepCreator.PARAMETER_VALUE_START);
            sb.append(group);
            sb.append(StepCreator.PARAMETER_VALUE_END);
        }
    }

    private static List<ScenarioDTO> fromJBehaveScenarios(List<org.jbehave.core.model.Scenario> scenarios, List<StepDocDTO> stepDocs) {

        List<ScenarioDTO> models = new ArrayList<ScenarioDTO>(scenarios.size());

        for (org.jbehave.core.model.Scenario scenario : scenarios) {

            StringBuilder sb = new StringBuilder();
            String title = scenario.getTitle();
            sb.append("Scenario: " + title + "\n");

            // Meta
            Meta meta = scenario.getMeta();
            Properties properties = new Properties();
            Set<String> propertyNames = meta.getPropertyNames();
            for (String propertyName : propertyNames) {
                String property = meta.getProperty(propertyName);
                properties.put(propertyName, property);
            }
            MetaDTO metaModel = new MetaDTO(properties);
            String metaAsString = metaModel.asString();
            if (!metaAsString.isEmpty()) {
                sb.append("\n");
                sb.append(metaAsString);
            }

            // GivenStories
            GivenStories givenStories = scenario.getGivenStories();
            List<GivenStory> stories = givenStories.getStories();
            List<GivenStoryDTO> storiesModel = new ArrayList<GivenStoryDTO>(stories.size());
            for (GivenStory story : stories) {
                String givenStoryAsString = story.asString();
                String path = story.getPath();
                GivenStoryDTO givenStoryModel = new GivenStoryDTO(path);
                storiesModel.add(givenStoryModel);
            }
            GivenStoriesDTO givenStoriesModel = new GivenStoriesDTO(storiesModel);
            String givenStoriesAsString = givenStoriesModel.asString();
            if (!givenStoriesAsString.isEmpty()) {
                sb.append("\n");
                sb.append(givenStoriesAsString);
            }

            // Steps
            List<String> steps = scenario.getSteps();
            List<StepDTO> stepsModel = new ArrayList<StepDTO>(steps.size());
            for (String step : steps) {
                List<BaseToken> tokens = parseStepIntoTokens(step, stepDocs);
                StepDTO stepModel = new StepDTO(step, tokens);
                stepsModel.add(stepModel);
                String stepAsString = stepModel.asString();
                sb.append(stepAsString);
                sb.append("\n");
            }

            // scanario parameterization table
            ExamplesTable examplesTable = scenario.getExamplesTable();
            String examplesTableAsString = examplesTable.asString();
            String headerSeparator = examplesTable.getHeaderSeparator();
            String valueSeparator = examplesTable.getValueSeparator();
            String ignorableSeparator = "NONE";
            ArrayList<String> headers = new ArrayList<String>(examplesTable.getHeaders());
            //
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
            if (examplesTableAsString != null && !examplesTableAsString.isEmpty()) {
                sb.append("\n");
                sb.append(examplesTableAsString);
            }

            String asString = sb.toString();
            ScenarioDTO scenarioModel = new ScenarioDTO(asString, title, metaModel, givenStoriesModel, stepsModel, examplesTableModel);
            models.add(scenarioModel);
        }

        return models;
    }

    private static LifecycleDTO fromJBehaveLifeCycle(Lifecycle lifecycle, List<StepDocDTO> stepDocDTOs) {
        List<StepDTO> beforeSteps;
        {
            List<String> jbehaveBeforeSteps = lifecycle.getBeforeSteps();
            beforeSteps = new ArrayList<StepDTO>(jbehaveBeforeSteps.size());
            for (String jbehaveBeforeStep : jbehaveBeforeSteps) {
                String asString = jbehaveBeforeStep;
                List<BaseToken> stepTokens = parseStepIntoTokens(jbehaveBeforeStep, stepDocDTOs);
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
                List<BaseToken> stepTokens = parseStepIntoTokens(jbehaveAfterStep, stepDocDTOs);
                StepDTO stepModel = new StepDTO(asString, stepTokens);
                afterSteps.add(stepModel);
            }
        }
        LifecycleDTO lifecycleModel = new LifecycleDTO(beforeSteps, afterSteps);
        return lifecycleModel;
    }

    private static List<BaseToken> parseStepIntoTokens(String stepAsString, List<StepDocDTO> stepDocDTOs) {

        List<BaseToken> tokens = new ArrayList<BaseToken>();

        String[] keywords = {"Given ", "When ", "Then ", "And "};
        String stepWithoutKeyword = null;
        for (String keyword : keywords) {
            if (stepAsString.startsWith(keyword)) {
                tokens.add(new BaseToken(TokenKind.keyword, keyword));
                stepWithoutKeyword = stepAsString.substring(keyword.length());
                break;
            }
        }

        // find matching stepDoc
        boolean matchFound = false;
        for (StepDocDTO stepDocDTO : stepDocDTOs) {
            String startingWord = stepDocDTO.getStartingWord();
            if (stepAsString.startsWith(startingWord)) {
                String regExpPattern = stepDocDTO.getRegExpPattern();
                Pattern pattern = Pattern.compile(regExpPattern, Pattern.DOTALL);
                Matcher matcher = pattern.matcher(stepWithoutKeyword);
                boolean matches = matcher.matches();
                if (matches) {

                    int totalGroups = matcher.groupCount();
                    if (totalGroups > 0) {
                        // has parameters, which are matcher's groups
                        int pos = 0;
                        for (int i = 1; i <= totalGroups; i++) {
                            int groupStart = matcher.start(i);
                            String group = matcher.group(i);
                            if (groupStart > pos) {
                                // append the string up until the group start
                                String str = stepWithoutKeyword.substring(pos, groupStart);
                                tokens.add(new BaseToken(TokenKind.text, str));
                                // append the current group as a parameter
                                tokens.add(new BaseToken(TokenKind.parameter, group));
                                // update position
                                pos = groupStart + group.length();
                            } else {
                                // we are at the start of the group already
                                tokens.add(new BaseToken(TokenKind.parameter, group));
                                // update position
                                pos = groupStart + group.length();
                            }
                        }
                        // append any characters which maybe after the last parameter and hence after the last matcher group
                        if (pos < stepWithoutKeyword.length()) {
                            String str = stepWithoutKeyword.substring(pos);
                            tokens.add(new BaseToken(TokenKind.text, str));
                        }

                    } else {
                        // doesn't have parameters
                        tokens.add(new BaseToken(TokenKind.text, stepWithoutKeyword));
                    }

                    matchFound = true;
                    break;
                }
            }
        }

        if (!matchFound) {
            // we simply set the whole step as a string token
            tokens.add(new BaseToken(TokenKind.text, stepAsString));
        }

        return tokens;
    }

    private static List<BaseToken> parseStepIntoTokens_OLD(String stepAsString, List<StepDocDTO> stepDocDTOs) {

//        Map<String, String> parameters = new HashMap<String, String>();
//
//        StepCandidate stepCandidate = null;
//        boolean matches = stepCandidate.matches(jbehaveBeforeStep);
//        Step matchedStep = stepCandidate.createMatchedStep(jbehaveBeforeStep, parameters);

        List<BaseToken> tokens = new ArrayList<BaseToken>();

        String[] keywords = {"Given", "When", "Then", "And"};
        for (String keyword : keywords) {
            if (stepAsString.startsWith(keyword)) {
                tokens.add(new BaseToken(TokenKind.keyword, keyword));
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
                    tokens.add(new BaseToken(TokenKind.text, stepText));
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
            tokens.add(new BaseToken(TokenKind.text, stepText));
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
            GivenStoryDTO givenStoryModel = new GivenStoryDTO(path);
            givenStoriesList.add(givenStoryModel);
        }
        GivenStoriesDTO givenStoriesModel = new GivenStoriesDTO(givenStoriesList);
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
            String scenarioText = scenario.asString();
            sb.append(scenarioText);
            sb.append(LINE_BREAK);
        }

        return sb.toString();
    }

}
