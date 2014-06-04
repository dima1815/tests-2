package com.mycomp.execspec.jiraplugin.dto.story;

import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
import com.mycomp.execspec.jiraplugin.util.ByLineStoryParser;
import org.jbehave.core.steps.StepCreator;

import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StoryDTOUtils {

    public static StoryDTO toDTO(Story story, List<StepDocDTO> stepDocs) {

        String issueKey = story.getIssueKey();
        String storyAsString = story.getAsString();

        ByLineStoryParser parser = new ByLineStoryParser();
        StoryDTO storyDTO = parser.parseStory(storyAsString, issueKey + ".story");
        return storyDTO;
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
