package org.bitbucket.jbehaveforjira.plugin.dto.story;

import org.bitbucket.jbehaveforjira.plugin.ao.story.Story;
import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStory;
import org.jbehave.core.steps.StepCreator;

import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by Dmytro on 4/8/2014.
 */
public class StoryDTOUtils {

    public static JiraStory toDTO(Story story) {

        JiraStory storyDTO = new JiraStory();
        storyDTO.setProjectKey(story.getProjectKey());
        storyDTO.setIssueKey(story.getIssueKey());
        storyDTO.setVersion(story.getVersion());
        storyDTO.setAsString(story.getAsString());

        return storyDTO;
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

    private static String markTablesInPendingStep(String step) {

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

//    public static String asString(StoryDTO storyDTO) {
//
//        // walk the story model and convert to string
//
//        final String LB = "\n";
//        StringBuilder sb = new StringBuilder();
//
//        // description
//        String description = storyDTO.getDescription();
//        if (description != null && !description.trim().isEmpty()) {
//            sb.append(description.trim() + LB);
//            sb.append(LB);
//        }
//
//        // meta
//        MetaDTO meta = storyDTO.getMeta();
//        if (meta != null) {
//            String keyword = meta.getKeyword();
//            Validate.notEmpty(keyword);
//            keyword = keyword.trim();
//            Validate.notEmpty(keyword);
//            sb.append(keyword + LB);
//            List<MetaEntryDTO> properties = meta.getProperties();
//            if (properties != null && !properties.isEmpty()) {
//                for (MetaEntryDTO p : properties) {
//                    String name = p.getName();
//                    String value = p.getValue();
//                    sb.append("@" + name);
//                    if (value != null && !value.isEmpty()) {
//                        sb.append(" " + value);
//                    }
//                    sb.append(LB);
//                }
//            }
//            sb.append(LB);
//        }
//
//        // narrative
//        NarrativeDTO narrative = storyDTO.getNarrative();
//        if (narrative != null) {
//            {
//                String keyword = narrative.getKeyword();
//                sb.append(keyword + LB);
//            }
//            {
//                InOrderToDTO inOrderTo = narrative.getInOrderTo();
//                if (inOrderTo != null) {
//                    String keyword = inOrderTo.getKeyword();
//                    sb.append(keyword);
//                    String value = inOrderTo.getValue();
//                    if (value != null && !value.isEmpty()) {
//                        sb.append(" " + value);
//                    }
//                    sb.append(LB);
//                }
//            }
//            {
//                AsADTO asA = narrative.getAsA();
//                if (asA != null) {
//                    String keyword = asA.getKeyword();
//                    sb.append(keyword);
//                    String value = asA.getValue();
//                    if (value != null && !value.isEmpty()) {
//                        sb.append(" " + value);
//                    }
//                    sb.append(LB);
//                }
//            }
//            {
//                IWantToDTO iWantTo = narrative.getiWantTo();
//                if (iWantTo != null) {
//                    String keyword = iWantTo.getKeyword();
//                    sb.append(keyword);
//                    String value = iWantTo.getValue();
//                    if (value != null && !value.isEmpty()) {
//                        sb.append(" " + value);
//                    }
//                    sb.append(LB);
//                }
//            }
//            {
//                SoThatDTO soThat = narrative.getSoThat();
//                if (soThat != null) {
//                    String keyword = soThat.getKeyword();
//                    sb.append(keyword);
//                    String value = soThat.getValue();
//                    if (value != null && !value.isEmpty()) {
//                        sb.append(" " + value);
//                    }
//                    sb.append(LB);
//                }
//            }
//        }
//
//        List<ScenarioDTO> scenarios = storyDTO.getScenarios();
//        if (scenarios != null && !scenarios.isEmpty()) {
//            for (ScenarioDTO scenario : scenarios) {
//                sb.append(LB);
//                String keyword = scenario.getKeyword();
//                Validate.notEmpty(keyword);
//                sb.append(keyword);
//                String title = scenario.getTitle();
//                if (title != null && !title.isEmpty()) {
//                    sb.append(" ");
//                    sb.append(title.trim());
//                }
//                sb.append(LB);
//            }
//        }
//
//        String asString = sb.toString();
//        return asString;
//    }
}
