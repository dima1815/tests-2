package org.bitbucket.jbehaveforjira.plugin.customfields;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.CalculatedCFType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.config.FieldConfigItem;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import org.bitbucket.jbehaveforjira.plugin.service.StepDocsSerivce;
import org.bitbucket.jbehaveforjira.plugin.service.StoryReportService;
import org.bitbucket.jbehaveforjira.plugin.service.StoryService;
import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStory;
import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStoryHtml;
import org.bitbucket.jbehaveforjira.javaclient.util.TestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryStatusField extends CalculatedCFType<EnvironmentTestStatuses, Object> {

    private static final Logger log = LoggerFactory.getLogger(StoryStatusField.class);

    private final StoryService storyService;
    private final StoryReportService storyReportService;
    private final StepDocsSerivce stepDocsSerivce;

    public StoryStatusField(StoryService storyService,
                            StoryReportService storyReportService, StepDocsSerivce stepDocsSerivce) {
        this.storyService = storyService;
        this.storyReportService = storyReportService;
        this.stepDocsSerivce = stepDocsSerivce;
    }

    @Override
    public String getStringFromSingularObject(Object s) {
        return s.toString();
    }

    @Override
    public EnvironmentTestStatuses getSingularObjectFromString(String s) throws FieldValidationException {
        EnvironmentTestStatuses environmentTestStatuses = new EnvironmentTestStatuses(new HashMap<String, TestStatus>());
        return environmentTestStatuses;
    }

    @Nullable
    @Override
    public EnvironmentTestStatuses getValueFromIssue(CustomField customField, Issue issue) {

        String projectKey = issue.getProjectObject().getKey();
        String issueKey = issue.getKey();

        JiraStory story = storyService.findByProjectAndIssueKey(projectKey, issueKey);

        if (story != null) {

            List<JiraStoryHtml> storyTestReports = storyReportService.findStoryReports(projectKey, issueKey);
            Map<String, TestStatus> statusesByEnvironment = new HashMap<String, TestStatus>(storyTestReports.size());
            EnvironmentTestStatuses environmentTestStatuses = new EnvironmentTestStatuses(statusesByEnvironment);

            for (JiraStoryHtml storyTestReport : storyTestReports) {
                TestStatus status = storyTestReport.getStatus();
                String environment = storyTestReport.getEnvironment();
                statusesByEnvironment.put(environment, status);
            }

            return environmentTestStatuses;

        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> getVelocityParameters(final Issue issue,
                                                     final CustomField field,
                                                     final FieldLayoutItem fieldLayoutItem) {

        final Map<String, Object> map = super.getVelocityParameters(issue, field, fieldLayoutItem);

        // This method is also called to get the default value, in
        // which case issue is null so we can't use it to add currencyLocale
        if (issue == null) {
            return map;
        }

        FieldConfig fieldConfig = field.getRelevantConfig(issue);
        //add what you need to the map here
        CustomField customField = fieldConfig.getCustomField();
        List<FieldConfigItem> configItems = fieldConfig.getConfigItems();

        map.put("test", "hello world");

        return map;
    }

    public StoryService getStoryService() {
        return storyService;
    }

}