package com.mycomp.execspec;

import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.TestStatus;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.lang.Validate;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.PrintStreamOutput;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.jbehave.core.reporters.PrintStreamOutput.Format.HTML;

/**
 * Created by Dmytro on 4/15/2014.
 */
public class JiraReporterHtmlOutput_BACKUP extends PrintStreamOutput {

    private final JiraUploadPrintStream printStream;

    private String environment;
    private String jiraBaseUrl;
    private String jiraProject;
    private String addTestReportPath = "rest/story-res/1.0/story-test/add";

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

    public JiraReporterHtmlOutput_BACKUP(String jiraBaseUrl, String jiraProject, String environment) {
        this(new Properties(), jiraBaseUrl, jiraProject, environment);
    }

    public JiraReporterHtmlOutput_BACKUP(Properties outputPatterns, String jiraBaseUrl, String jiraProject, String environment) {
        this(outputPatterns, new LocalizedKeywords(), jiraBaseUrl, jiraProject, environment);
    }

    public JiraReporterHtmlOutput_BACKUP(Keywords keywords, String jiraBaseUrl, String jiraProject, String environment) {
        this(new Properties(), keywords, jiraBaseUrl, jiraProject, environment);
    }

    public JiraReporterHtmlOutput_BACKUP(Properties outputPatterns, Keywords keywords,
                                         String jiraBaseUrl, String jiraProject, String environment) {
        this(outputPatterns, keywords, false, jiraBaseUrl, jiraProject, environment);
    }

    public JiraReporterHtmlOutput_BACKUP(Properties outputPatterns,
                                         Keywords keywords, boolean reportFailureTrace,
                                         String jiraBaseUrl, String jiraProject, String environment) {
        this(mergeWithDefault(outputPatterns), keywords, reportFailureTrace, false,
                jiraBaseUrl, jiraProject, environment);
    }

    public JiraReporterHtmlOutput_BACKUP(Properties outputPatterns,
                                         Keywords keywords, boolean reportFailureTrace,
                                         boolean compressFailureTrace,
                                         String jiraBaseUrl, String jiraProject, String environment) {
        this(mergeWithDefault(outputPatterns), new JiraUploadPrintStream(), keywords, reportFailureTrace, false,
                jiraBaseUrl, jiraProject, environment);
    }

    private JiraReporterHtmlOutput_BACKUP(Properties outputPatterns, JiraUploadPrintStream printStream,
                                          Keywords keywords, boolean reportFailureTrace,
                                          boolean compressFailureTrace,
                                          String jiraBaseUrl, String jiraProject, String environment) {
        super(HTML, printStream, mergeWithDefault(outputPatterns), keywords, reportFailureTrace, compressFailureTrace);
        this.jiraBaseUrl = jiraBaseUrl;
        this.jiraProject = jiraProject;
        this.environment = environment;
        this.printStream = printStream;
    }

    private static Properties mergeWithDefault(Properties outputPatterns) {
        Properties patterns = customPatterns();
        // override any default pattern
        patterns.putAll(outputPatterns);
        return patterns;
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
        this.currentScenarioStatus = TestStatus.IGNORED;
    }

    @Override
    public void failed(String step, Throwable storyFailure) {

        super.failed(step, storyFailure);
        this.currentScenarioStatus = TestStatus.FAILED;
    }

    @Override
    public void pending(String step) {

        super.pending(step);
        this.currentScenarioStatus = TestStatus.PENDING;
    }


    @Override
    public void beforeStory(Story story, boolean givenStory) {

        this.status = TestStatus.PASSED; // assume passed at start, and then change to other if failed/pending, etc.

        storyPath = story.getPath();
        if (!givenStory && !storyPath.equals("BeforeStories") && !storyPath.equals("AfterStories")) {
            String versionProperty = story.getMeta().getProperty("jira-version");
            Validate.notEmpty(versionProperty);
            jiraVersion = Long.parseLong(versionProperty);
        }

        // instead of calling the superclass's method we put the lines from it here
        // so that we can remove our "technical" jira-version meta field which we used above
        Meta meta = story.getMeta();
        if (!meta.isEmpty() && !(meta.getPropertyNames().size() == 1 && meta.hasProperty("jira-version"))) {
            super.beforeStory(story, givenStory);
        } else {
            print(format("beforeStory", "{0}\n({1})\n", story.getDescription().asString(), story.getPath()));
        }

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

            // send the report to JIRA
            List<Byte> writtenBytes = printStream.writtenBytes;
            Byte[] bytes = writtenBytes.toArray(new Byte[writtenBytes.size()]);
            byte[] bytesArray = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                Byte aByte = bytes[i];
                bytesArray[i] = aByte;
            }
            String testReport = new String(bytesArray);
            sendTestReport(testReport);
        }

    }

    public static Properties customPatterns() {

        Properties patterns = new Properties();
        patterns.setProperty("dryRun", "<div class=\"dryRun\">{0}</div>\n");

        patterns.setProperty("beforeStory", "<div class=\"story\">\n<h1>{0}</h1>\n<div class=\"path\">{1}</div>\n");
//        patterns.setProperty("beforeStory", "<div class=\"story\">\n");
        patterns.setProperty("afterStory", "</div>\n");

        patterns.setProperty("storyCancelled", "<div class=\"cancelled\">{0} ({1} {2} s)</div>\n");
        patterns.setProperty("pendingMethod", "<div><pre class=\"pending\">{0}</pre></div>\n");
        patterns.setProperty("metaStart", "<div class=\"meta\">\n<div class=\"keyword\">{0}</div>\n");
        patterns.setProperty("metaProperty", "<div class=\"property\">{0}{1} {2}</div>\n");
        patterns.setProperty("metaEnd", "</div>\n");
        patterns.setProperty("filter", "<div class=\"filter\">{0}</div>\n");

        patterns.setProperty("narrative", "<div class=\"narrative\"><h2>{0}</h2>\n<div class=\"element inOrderTo\"><span class=\"keyword inOrderTo\">{1}</span> {2}</div>\n<div class=\"element asA\"><span class=\"keyword asA\">{3}</span> {4}</div>\n<div class=\"element iWantTo\"><span class=\"keyword iWantTo\">{5}</span> {6}</div>\n</div>\n");
//        patterns.setProperty("narrative", "<div class=\"narrative\"><div class=\"label\">{0}</div>\n<div class=\"element inOrderTo\"><span class=\"keyword inOrderTo\">{1}</span> {2}</div>\n<div class=\"element asA\"><span class=\"keyword asA\">{3}</span> {4}</div>\n<div class=\"element iWantTo\"><span class=\"keyword iWantTo\">{5}</span> {6}</div>\n</div>\n");

        patterns.setProperty("lifecycleStart", "<div class=\"lifecycle\"><h2>{0}</h2>");
        patterns.setProperty("lifecycleEnd", "</div>");
        patterns.setProperty("lifecycleBeforeStart", "<div class=\"before\"><h3>{0}</h3>");
        patterns.setProperty("lifecycleBeforeEnd", "</div>");
        patterns.setProperty("lifecycleAfterStart", "<div class=\"after\"><h3>{0}</h3>");
        patterns.setProperty("lifecycleAfterEnd", "</div>");
        patterns.setProperty("lifecycleStep", "<div class=\"step\">{0}</div>\n");

        patterns.setProperty("beforeScenario", "<div class=\"scenario\">\n<h2>{0} {1}</h2>\n");
//        patterns.setProperty("beforeScenario", "<div class=\"scenario\">\n<div class=\"scenario-title\"><span class=\"label\">{0}</span> <span class=\"title\">{1}</span></div>\n");

        patterns.setProperty("afterScenario", "</div>\n");
        patterns.setProperty("afterScenarioWithFailure", "<pre class=\"failure\">{0}</pre>\n</div>\n");
        patterns.setProperty("givenStories", "<div class=\"givenStories\">{0} {1}</div>\n");
        patterns.setProperty("givenStoriesStart", "<div class=\"givenStories\">{0}\n");
        patterns.setProperty("givenStory", "<div class=\"givenStory\">{0} {1}</div>\n");
        patterns.setProperty("givenStoriesEnd", "</div>\n");
        patterns.setProperty("successful", "<div class=\"step successful\">{0}</div>\n");
        patterns.setProperty("ignorable", "<div class=\"step ignorable\">{0}</div>\n");
        patterns.setProperty("pending", "<div class=\"step pending\">{0} <span class=\"keyword pending\">({1})</span></div>\n");
        patterns.setProperty("notPerformed", "<div class=\"step notPerformed\">{0} <span class=\"keyword notPerformed\">({1})</span></div>\n");
        patterns.setProperty("failed", "<div class=\"step failed\">{0} <span class=\"keyword failed\">({1})</span><br/><span class=\"message failed\">{2}</span></div>\n");
        patterns.setProperty("restarted", "<div class=\"step restarted\">{0} <span class=\"message restarted\">{1}</span></div>\n");
        patterns.setProperty("outcomesTableStart", "<div class=\"outcomes\"><table>\n");
        patterns.setProperty("outcomesTableHeadStart", "<thead>\n<tr>\n");
        patterns.setProperty("outcomesTableHeadCell", "<th>{0}</th>");
        patterns.setProperty("outcomesTableHeadEnd", "</tr>\n</thead>\n");
        patterns.setProperty("outcomesTableBodyStart", "<tbody>\n");
        patterns.setProperty("outcomesTableRowStart", "<tr class=\"{0}\">\n");
        patterns.setProperty("outcomesTableCell", "<td>{0}</td>");
        patterns.setProperty("outcomesTableRowEnd", "</tr>\n");
        patterns.setProperty("outcomesTableBodyEnd", "</tbody>\n");
        patterns.setProperty("outcomesTableEnd", "</table></div>\n");
        patterns.setProperty("beforeExamples", "<div class=\"examples\">\n<h3>{0}</h3>\n");
        patterns.setProperty("examplesStep", "<div class=\"step\">{0}</div>\n");
        patterns.setProperty("afterExamples", "</div>\n");
        patterns.setProperty("examplesTableStart", "<table>\n");
        patterns.setProperty("examplesTableHeadStart", "<thead>\n<tr>\n");
        patterns.setProperty("examplesTableHeadCell", "<th>{0}</th>");
        patterns.setProperty("examplesTableHeadEnd", "</tr>\n</thead>\n");
        patterns.setProperty("examplesTableBodyStart", "<tbody>\n");
        patterns.setProperty("examplesTableRowStart", "<tr>\n");
        patterns.setProperty("examplesTableCell", "<td>{0}</td>");
        patterns.setProperty("examplesTableRowEnd", "</tr>\n");
        patterns.setProperty("examplesTableBodyEnd", "</tbody>\n");
        patterns.setProperty("examplesTableEnd", "</table>\n");
        patterns.setProperty("example", "\n<h3 class=\"example\">{0} {1}</h3>\n");
        patterns.setProperty("parameterValueStart", "<span class=\"step parameter\">");
        patterns.setProperty("parameterValueEnd", "</span>");
        patterns.setProperty("parameterValueNewline", "<br/>");
        return patterns;
    }

    private static class JiraUploadPrintStream extends PrintStream {

        List<Byte> writtenBytes;

        JiraUploadPrintStream() {
            this(new LinkedList<Byte>());
        }

        private JiraUploadPrintStream(final List<Byte> bytesList) {

            super(new OutputStream() {

                @Override
                public void write(int b) throws IOException {
                    bytesList.add((byte) b);
                }

            });

            writtenBytes = bytesList;
        }

    }

    protected void sendTestReport(String testReport) {

        Validate.notNull(status);
        Validate.notEmpty(testReport);

        StoryHtmlReportDTO storyHtmlReportDTO = new StoryHtmlReportDTO(environment, storyPath, jiraVersion, status, testReport);

        storyHtmlReportDTO.setTotalScenarios(totalScenarios);
        storyHtmlReportDTO.setTotalScenariosPassed(totalScenariosPassed);
        storyHtmlReportDTO.setTotalScenariosFailed(totalScenariosFailed);
        storyHtmlReportDTO.setTotalScenariosPending(totalScenariosPending);
        storyHtmlReportDTO.setTotalScenariosSkipped(totalScenariosIgnored);
        storyHtmlReportDTO.setTotalScenariosNotPerformed(totalScenariosNotPerformed);

        String loginParams = "?os_username=admin&os_password=admin";
        String postUrl = jiraBaseUrl
                + "/" + addTestReportPath + "/"
                + jiraProject + "/"
                + storyPath
                + loginParams;

        Client client = Client.create();
        WebResource res = client.resource(postUrl);

        String response = res.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(String.class, storyHtmlReportDTO);

        System.out.println("response - " + response);
    }
}
