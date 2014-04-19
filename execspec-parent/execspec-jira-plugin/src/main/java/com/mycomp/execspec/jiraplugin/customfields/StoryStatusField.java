package com.mycomp.execspec.jiraplugin.customfields;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.CalculatedCFType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.config.FieldConfigItem;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.TestStatus;
import com.mycomp.execspec.jiraplugin.service.StoryReportService;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryStatusField extends CalculatedCFType<EnvironmentTestStatuses, String> {

    private static final Logger log = LoggerFactory.getLogger(StoryStatusField.class);

    private StoryService storyService;
    private StoryReportService storyReportService;

    public StoryStatusField(StoryService storyService, StoryReportService storyReportService) {
        this.storyService = storyService;
        this.storyReportService = storyReportService;
    }

    @Override
    public String getStringFromSingularObject(String s) {
        return s;
    }

    @Override
    public String getSingularObjectFromString(String s) throws FieldValidationException {
        return s;
    }

    @Nullable
    @Override
    public EnvironmentTestStatuses getValueFromIssue(CustomField customField, Issue issue) {

        String projectKey = issue.getProjectObject().getKey();
        String issueKey = issue.getKey();

        List<StoryHtmlReportDTO> storyTestReports = storyReportService.findStoryReports(projectKey, issueKey);
        if (!storyTestReports.isEmpty()) {

            Map<String, TestStatus> statusesByEnvironment = new HashMap<String, TestStatus>(storyTestReports.size());

            for (StoryHtmlReportDTO storyTestReport : storyTestReports) {
                TestStatus status = storyTestReport.getStatus();
                String environment = storyTestReport.getEnvironment();
                statusesByEnvironment.put(environment, status);
            }

            EnvironmentTestStatuses environmentTestStatuses = new EnvironmentTestStatuses(statusesByEnvironment);
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

    public void setStoryService(StoryService storyService) {
        this.storyService = storyService;
    }
}