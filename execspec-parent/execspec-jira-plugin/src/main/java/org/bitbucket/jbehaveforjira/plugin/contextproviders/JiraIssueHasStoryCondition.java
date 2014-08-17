package org.bitbucket.jbehaveforjira.plugin.contextproviders;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.project.Project;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;
import org.bitbucket.jbehaveforjira.plugin.ao.story.Story;
import org.bitbucket.jbehaveforjira.plugin.ao.story.StoryDao;

import java.util.List;
import java.util.Map;

/**
 * Created by Dmytro on 5/7/2014.
 */
public class JiraIssueHasStoryCondition implements Condition {

    private StoryDao storyDao;

    public JiraIssueHasStoryCondition(StoryDao storyDao) {

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
        List<Story> byProjectAndIssueKey = storyDao.findByProjectAndIssueKey(projectKey, isssueKey);

        return byProjectAndIssueKey.isEmpty() ? false : true;
    }
}
