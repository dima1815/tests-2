package com.mycomp.execspec.jiraplugin.dto.story;

import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import com.mycomp.execspec.jiraplugin.dto.story.output.*;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
import org.jbehave.core.model.Lifecycle;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.steps.StepCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StoryDTOUtils {

    public static StoryDTO toDTO(Story story, List<StoryHtmlReportDTO> storyReports, List<StepDocDTO> stepDocs) {

        String issueKey = story.getIssueKey();
        String projectKey = story.getProjectKey();
        String storyAsString = story.getAsString();

        RegexStoryParser parser = new RegexStoryParser();
        org.jbehave.core.model.Story jbehaveStory = parser.parseStory(storyAsString, story.getIssueKey());

        ReportingStoryWalker reportingStoryWalker = new ReportingStoryWalker(stepDocs);
        BytesListPrintStream printStream = new BytesListPrintStream();
        Properties outputPatterns = new DefaultHTMLFormatPatterns().getPatterns();
        outputPatterns.setProperty("pending", "<div class=\"step pending\">{0}</div>\n");
        StoryReporter reporter = new CustomHTMLOutput(printStream, outputPatterns);
        reportingStoryWalker.walkStory(jbehaveStory, reporter);

        List<Byte> writtenBytes = printStream.getWrittenBytes();
        String asHTML = bytesListToString(writtenBytes);
//        String asHTML = toHTML(jbehaveStory, stepDocs);

        String desription = "default description";

        GivenStoriesDTO givenStories = new GivenStoriesDTO();
        givenStories.setPaths(jbehaveStory.getGivenStories().getPaths());

        Lifecycle jbLifecycle = jbehaveStory.getLifecycle();
        LifecycleDTO lifecycle;
        if (jbLifecycle != null) {
            List<String> beforeSteps = jbLifecycle.getBeforeSteps();
            List<String> afterSteps = jbLifecycle.getAfterSteps();
            lifecycle = new LifecycleDTO();
            lifecycle.setBeforeSteps(beforeSteps);
            lifecycle.setAfterSteps(afterSteps);
        } else {
            lifecycle = null;
        }

        MetaDTO meta = new MetaDTO(); // TODO - use actual properties here
        Narrative jbNarrative = jbehaveStory.getNarrative();
        NarrativeDTO narrative = new NarrativeDTO();
//        narrative.setInOrderTo(jbNarrative.inOrderTo());
//        narrative.setAsA(jbNarrative.asA());
//        narrative.setiWantTo(jbNarrative.iWantTo());
//        narrative.setSoThat(jbNarrative.soThat());
        List<ScenarioDTO> scenarioDTOs = new ArrayList<ScenarioDTO>();

        StoryDTO storyModel = null;
//                new StoryDTO(
//                projectKey, issueKey, story.getVersion(), storyAsString, asHTML, storyReports,
//                desription, givenStories, lifecycle, meta, narrative, scenarioDTOs);

        return storyModel;
    }

    public static String bytesListToString(List<Byte> writtenBytes) {

        Byte[] bytes = writtenBytes.toArray(new Byte[writtenBytes.size()]);
        byte[] bytesArray = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            Byte aByte = bytes[i];
            bytesArray[i] = aByte;
        }

        String str = new String(bytesArray);
        return str;
    }

    private static String markTablesInPendingStep(String step, CustomHTMLOutput reporter) {

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

}
