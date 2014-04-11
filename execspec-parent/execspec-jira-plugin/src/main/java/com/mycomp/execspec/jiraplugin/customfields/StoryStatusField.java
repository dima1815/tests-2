package com.mycomp.execspec.jiraplugin.customfields;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.CalculatedCFType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.mycomp.execspec.jiraplugin.dto.story.out.StoryDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.ScenarioReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.TestStatus;
import com.mycomp.execspec.jiraplugin.service.StoryReportService;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class StoryStatusField extends CalculatedCFType<String, String> {

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
    public String getValueFromIssue(CustomField customField, Issue issue) {
        String projectKey = issue.getProjectObject().getKey();
        String issueKey = issue.getKey();
        StoryDTO story = storyService.findByProjectAndIssueKey(projectKey, issueKey);

        List<StoryReportDTO> storyTestReports = storyReportService.findStoryTestReports(projectKey, issueKey);
        if (!storyTestReports.isEmpty()) {
            boolean failed = false;
            int totalFailedReports = 0;
            int totalPendingReports = 0;
            for (StoryReportDTO storyTestReport : storyTestReports) {
                List<ScenarioReportDTO> scenarioTestReportModels = storyTestReport.getScenarioTestReportDTOs();
                for (ScenarioReportDTO scenarioTestReportModel : scenarioTestReportModels) {
                    TestStatus scenarioStatus = scenarioTestReportModel.getStatus();
                    if (scenarioStatus == TestStatus.FAILED) {
                        totalFailedReports++;
                        break;
                    } else if (scenarioStatus == TestStatus.PENDING) {
                        totalPendingReports++;
                        break;
                    }
                }
            }
            StringBuilder testStatusString = new StringBuilder();
            if (totalFailedReports > 0) {
                testStatusString.append(totalFailedReports + " failed");
            }
            if (totalPendingReports > 0) {
                if (testStatusString.length() > 0) {
                    testStatusString.append(", ");
                }
                testStatusString.append(totalPendingReports + " pending");
            }
            if (totalFailedReports > 0 || totalPendingReports > 0) {
                testStatusString.append(" of " + storyTestReports.size());
            } else {
                testStatusString.append(storyTestReports.size() + " of " + storyTestReports.size() + " passed");
            }

            return testStatusString.toString();
        } else {
            return "None";
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


        return map;
    }

    public StoryService getStoryService() {
        return storyService;
    }

    public void setStoryService(StoryService storyService) {
        this.storyService = storyService;
    }
}