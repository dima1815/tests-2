package com.mycomp.execspec.util;

import com.mycomp.execspec.dto.StepDoc;
import org.jbehave.core.model.GivenStories;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.steps.StepCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dmytro on 4/29/2014.
 */
@Deprecated
public class ReportingStoryWalker {

    private final List<StepDoc> stepDocs;

    public ReportingStoryWalker() {
        this(new ArrayList<StepDoc>());
    }

    public ReportingStoryWalker(List<StepDoc> stepDocs) {
        this.stepDocs = stepDocs;
    }

    public void walkStory(Story story, StoryReporter reporter) {

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
                for (StepDoc stepDocDTO : stepDocs) {
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
                    String withTablesMarked = markTablesInPendingStep(step, reporter);
                    reporter.pending(withTablesMarked);
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
    }

    private String insertParameterMarkers(String step, Matcher matcher) {

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

    private void appendParameter(StringBuilder sb, String group) {
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

    private String markTablesInPendingStep(String step, StoryReporter reporter) {

        if (!step.contains("|")) {
            return step;
        } else {

            StringBuilder sb = new StringBuilder();
            String[] lines = step.split("\\n");

            for (int i = 0; i < lines.length; i++) {

                String line = lines[i];
                boolean isTableStartLine = false;
                boolean isTableEndLine = false;
                if (line.startsWith("|")) {
                    // table line
                    if (i == 0) {
                        throw new IllegalArgumentException("Table cannot start on the first line, i.e. expecting a keyword at the start e.g. Given, When, etc.");
                    }
                    if (!lines[i - 1].startsWith("|")) {
                        // if previous line was not a table line then open the table tag
//                    String tableStartMarker = reporter.escapeHTML(StepCreator.PARAMETER_TABLE_START);
                        int tableStartPos = sb.length();
                        String tableStartMarker = StepCreator.PARAMETER_TABLE_START;
                        sb.append(tableStartMarker);
                        sb.append(line);
                        isTableStartLine = true;
                    }
                    if (i == lines.length - 1 || !lines[i + 1].startsWith("|")) {
                        // if this is the last line or the next line is not a table line
                        String beforePart = line.substring(0, line.lastIndexOf("|") + 1);
                        sb.append(beforePart);
//                    String tableEndMarker = reporter.escapeHTML(StepCreator.PARAMETER_TABLE_END);
                        String tableEndMarker = StepCreator.PARAMETER_TABLE_END;
                        sb.append(tableEndMarker);
                        String afterPart = line.substring(line.lastIndexOf("|") + 1);
                        sb.append(afterPart);
                        isTableEndLine = true;
                    }
                    if (!isTableStartLine && !isTableEndLine) {
                        // table line that is not first or last line in that table
                        sb.append(line);
                    }
                } else {
                    sb.append(line);
                }

                if (i != lines.length - 1) {
                    sb.append("\n");
                }
            }

            String result = sb.toString();
            return result;

        }
    }
}
