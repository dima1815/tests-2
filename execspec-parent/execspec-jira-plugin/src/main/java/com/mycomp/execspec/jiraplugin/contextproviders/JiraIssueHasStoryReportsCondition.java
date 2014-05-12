package com.mycomp.execspec.jiraplugin.contextproviders;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.project.Project;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;
import com.mycomp.execspec.jiraplugin.ao.story.Story;
import com.mycomp.execspec.jiraplugin.ao.story.StoryDao;
import com.mycomp.execspec.jiraplugin.ao.testreport.StoryHtmlReport;
import org.apache.commons.lang.Validate;

import java.util.List;
import java.util.Map;

/**
 * Created by Dmytro on 5/7/2014.
 */
public class JiraIssueHasStoryReportsCondition implements Condition {

    private StoryDao storyDao;

    public JiraIssueHasStoryReportsCondition(StoryDao storyDao) {

        this.storyDao = storyDao;
    }

    @Override
    public void init(Map<String, String> params) throws PluginParseException {
    }

    @Override
    public boolean shouldDisplay(Map<String, Object> context) {

        Project project = (Project) context.get("project");
        String projectKey = project.getKey();
        Issue issue = (Issue) context.get("issue");
        String isssueKey = issue.getKey();
        List<Story> stories = storyDao.findByProjectAndIssueKey(projectKey, isssueKey);

        if (stories.isEmpty()) {
            return false;
        } else {
            // check if there are reports
            Validate.isTrue(stories.size() == 1);
            Story story = stories.get(0);
            StoryHtmlReport[] storyHtmlReports = story.getStoryHtmlReports();
            return storyHtmlReports.length != 0;
        }
    }
}
