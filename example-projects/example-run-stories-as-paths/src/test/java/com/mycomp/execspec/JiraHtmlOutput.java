package com.mycomp.execspec;

import com.mycomp.execspec.jiraplugin.dto.story.StoryDTOUtils;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.TestStatus;
import com.mycomp.execspec.util.BytesListPrintStream;
import com.mycomp.execspec.util.DefaultHTMLFormatPatterns;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.lang.Validate;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.HtmlOutput;
import org.jbehave.core.steps.StepCreator;

import javax.ws.rs.core.MediaType;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dmytro on 4/15/2014.
 */
public class JiraHtmlOutput extends HtmlOutput {

    private final BytesListPrintStream printStream;

    private String environment;

    private String jiraBaseUrl;

    private String jiraProject;

    private String addTestReportPath = "rest/story-res/1.0/story-test/add-for-path";

    private String storyPath;

    private Long jiraVersion;

    private TestStatus status;

    private int totalScenarios;

    private int totalScenariosPassed;

    private int totalScenariosFailed;

    private int totalScenariosPending;

    private int totalScenariosIgnored;

    private int totalScenariosNotPerformed;

    private TestStatus currentScenarioStatus;

    public JiraHtmlOutput(String jiraBaseUrl, String jiraProject, String environment) {
        this(new Properties(), jiraBaseUrl, jiraProject, environment);
    }

    public JiraHtmlOutput(Properties outputPatterns, String jiraBaseUrl, String jiraProject, String environment) {
        this(outputPatterns, new LocalizedKeywords(), jiraBaseUrl, jiraProject, environment);
    }

    public JiraHtmlOutput(Keywords keywords, String jiraBaseUrl, String jiraProject, String environment) {
        this(new Properties(), keywords, jiraBaseUrl, jiraProject, environment);
    }

    public JiraHtmlOutput(Properties outputPatterns, Keywords keywords,
                          String jiraBaseUrl, String jiraProject, String environment) {
        this(outputPatterns, keywords, false, jiraBaseUrl, jiraProject, environment);
    }

    public JiraHtmlOutput(Properties outputPatterns,
                          Keywords keywords, boolean reportFailureTrace,
                          String jiraBaseUrl, String jiraProject, String environment) {
        this(new BytesListPrintStream(), outputPatterns, keywords, reportFailureTrace, false,
                jiraBaseUrl, jiraProject, environment);
    }

    public JiraHtmlOutput(BytesListPrintStream printStream, Properties outputPatterns,
                          Keywords keywords, boolean reportFailureTrace,
                          boolean compressFailureTrace,
                          String jiraBaseUrl, String jiraProject, String environment) {
        super(printStream, mergeOverridenPatterns(outputPatterns), keywords, reportFailureTrace, compressFailureTrace);
        this.printStream = printStream;
        this.jiraBaseUrl = jiraBaseUrl;
        this.jiraProject = jiraProject;
        this.environment = environment;
    }

    public void narrative(Narrative narrative) {
        super.narrative(narrative);
    }

    @Override
    public void beforeScenario(String title) {

        this.totalScenarios++;
        this.currentScenarioStatus = TestStatus.PASSED; // assume pass initially

        super.beforeScenario(title);
    }

    @Override
    public void afterScenario() {

        super.afterScenario();

        switch (this.currentScenarioStatus) {
            case PASSED:
                totalScenariosPassed++;
                break;
            case FAILED:
                totalScenariosFailed++;
                break;
            case PENDING:
                totalScenariosPending++;
                break;
            case IGNORED:
                totalScenariosIgnored++;
                break;
            case NOT_PERFORMED:
                totalScenariosNotPerformed++;
                break;
        }
    }

    @Override
    public void scenarioNotAllowed(Scenario scenario, String filter) {

        super.scenarioNotAllowed(scenario, filter);
        if (this.currentScenarioStatus == TestStatus.PASSED /*i.e. if it has not been set to some other status yet*/) {
            this.currentScenarioStatus = TestStatus.IGNORED;
        }
    }

    @Override
    public void successful(String step) {

        super.successful(step);
    }

    @Override
    public void failed(String step, Throwable storyFailure) {

        super.failed(step, storyFailure);
        this.currentScenarioStatus = TestStatus.FAILED;
    }

    @Override
    public void pending(String step) {

        super.pending(step);
        if (this.currentScenarioStatus != TestStatus.FAILED) { // fail status has priority over pending
            this.currentScenarioStatus = TestStatus.PENDING;
        }
    }


    @Override
    public void beforeStory(Story story, boolean givenStory) {

        this.status = TestStatus.PASSED; // assume passed at start, and then change to other if failed/pending, etc.

        storyPath = story.getPath();

        if (!givenStory && !storyPath.equals("BeforeStories") && !storyPath.equals("AfterStories")) {
            // extract version
            String regexPattern = "(.*)\\.([0-9]*)(\\.story)";
            Pattern p = Pattern.compile(regexPattern);
            Matcher matcher = p.matcher(storyPath);
            if (matcher.matches()) {
                String versionStr = matcher.group(2);
                jiraVersion = Long.parseLong(versionStr);
            } else {
                throw new IllegalArgumentException("Story path must match pattern - " + regexPattern);
            }
        }

        super.beforeStory(story, givenStory);
    }

    @Override
    public void afterStory(boolean givenStory) {

        super.afterStory(givenStory);

        if (!givenStory && !storyPath.equals("BeforeStories") && !storyPath.equals("AfterStories")) {

            // set story status
            if (totalScenariosFailed > 0) {
                this.status = TestStatus.FAILED;
            } else if (totalScenariosPending > 0) {
                this.status = TestStatus.PENDING;
            } else if (totalScenariosIgnored == totalScenarios) {
                this.status = TestStatus.IGNORED;
            } else {
                this.status = TestStatus.PASSED;
            }

            String storyReport = StoryDTOUtils.bytesListToString(this.printStream.getWrittenBytes());
            sendStoryReport(storyReport);
        }

    }

    protected void sendStoryReport(String testReport) {

        Validate.notNull(status);
        Validate.notEmpty(testReport);

        StoryHtmlReportDTO storyHtmlReportDTO = new StoryHtmlReportDTO(environment, storyPath, jiraVersion, status, testReport);

        storyHtmlReportDTO.setTotalScenarios(totalScenarios);
        storyHtmlReportDTO.setTotalScenariosPassed(totalScenariosPassed);
        storyHtmlReportDTO.setTotalScenariosFailed(totalScenariosFailed);
        storyHtmlReportDTO.setTotalScenariosPending(totalScenariosPending);
        storyHtmlReportDTO.setTotalScenariosSkipped(totalScenariosIgnored);
        storyHtmlReportDTO.setTotalScenariosNotPerformed(totalScenariosNotPerformed);

        // remove the version part from story path
        // extract version
        String regexPattern = "(.*)\\.([0-9]*)(\\.story)";
        Pattern p = Pattern.compile(regexPattern);
        Matcher matcher = p.matcher(storyPath);
        if (matcher.matches()) {
            storyPath = matcher.group(1) + matcher.group(3);;
        } else {
            throw new IllegalArgumentException("Story path must match pattern - " + regexPattern);
        }

        String loginParams = "?os_username=admin&os_password=admin";
        String postUrl = jiraBaseUrl
                + "/" + addTestReportPath + "/"
//                + jiraProject + "/"
                + storyPath
                + loginParams;

        Client client = Client.create();
        WebResource res = client.resource(postUrl);

        String response = res.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(String.class, storyHtmlReportDTO);

        System.out.println("response - " + response);
    }

    @Override
    protected void print(String text) {
        text = markTableCorrectly(text);
        super.print(text);
    }

    private String markTableCorrectly(String text) {

        if (text.contains("&#9252;")) {

            StringBuilder sb = new StringBuilder();
            String[] tokens = text.split("\\&#9252;");

            for (int i = 0; i < tokens.length; i++) {
                String token = tokens[i];
                boolean isTableStartLine = false;
                boolean isTableEndLine = false;
                if (token.startsWith("|")) {
                    // table line
                    if (i == 0 || (i != 0 && !tokens[i - 1].startsWith("|"))) {
                        // if previous line was not a table line then open the table tag
                        String tableStartMarker = StepCreator.PARAMETER_TABLE_START;
                        tableStartMarker = (String) escape(Format.HTML, tableStartMarker)[0];
                        sb.append(tableStartMarker);
                        sb.append(token);
                        isTableStartLine = true;
                    }
                    if (i == tokens.length - 1 || !tokens[i + 1].startsWith("|")) {
                        // if this is the last line or the next line is not a table line
                        String beforePart = token.substring(0, token.lastIndexOf("|") + 1);
                        sb.append(beforePart);
                        String tableEndMarker = StepCreator.PARAMETER_TABLE_END;
                        tableEndMarker = (String) escape(Format.HTML, tableEndMarker)[0];
                        sb.append(tableEndMarker);
                        String afterPart = token.substring(token.lastIndexOf("|") + 1);
                        sb.append(afterPart);
                        isTableEndLine = true;
                    }
                    if (!isTableStartLine && !isTableEndLine) {
                        // table line that is not first or last line in that table
                        sb.append(token);
                    }
                    if (i != 0 && i != tokens.length - 1) {
                        sb.append("\n");
                    }
                } else {
                    if (i != 0) {
                        sb.append("\n");
                    }
                    sb.append(token);
                }
            }

            String result = sb.toString();
            return result;

        } else {
            return text;
        }
    }

    private static Properties mergeOverridenPatterns(Properties outputPatterns) {
        Properties patterns = new DefaultHTMLFormatPatterns().getPatterns();
        Properties overridenPatterns = overridenPatterns();
        patterns.putAll(overridenPatterns);
        patterns.putAll(outputPatterns);
        return patterns;
    }

    public static Properties overridenPatterns() {

        Properties patterns = new Properties();

        patterns.setProperty("beforeScenario", "<div class=\"scenario\">\n<div class=\"scenario-title\"><span class=\"label\">{0}</span> <span class=\"title\">{1}</span></div>\n");
        patterns.setProperty("afterScenario", "</div>\n");
        patterns.setProperty("afterScenarioWithFailure", "<pre class=\"failure\">{0}</pre>\n</div>\n");

        patterns.setProperty("successful", "<div class=\"step successful\">{0}</div>\n");
        patterns.setProperty("ignorable", "<div class=\"step ignorable\">{0}</div>\n");
        patterns.setProperty("pending", "<div class=\"step pending\">{0} <span class=\"keyword pending\">({1})</span></div>\n");
        patterns.setProperty("notPerformed", "<div class=\"step notPerformed\">{0} <span class=\"keyword notPerformed\">({1})</span></div>\n");
        patterns.setProperty("failed", "<div class=\"step failed\">{0} <span class=\"keyword failed\">({1})</span><br/><span class=\"message failed\">{2}</span></div>\n");
        patterns.setProperty("restarted", "<div class=\"step restarted\">{0} <span class=\"message restarted\">{1}</span></div>\n");

        patterns.setProperty("pendingMethod", "");

        return patterns;
    }

}
