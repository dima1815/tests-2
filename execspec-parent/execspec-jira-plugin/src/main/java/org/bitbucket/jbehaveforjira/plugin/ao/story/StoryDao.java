package org.bitbucket.jbehaveforjira.plugin.ao.story;

import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.Query;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

public final class StoryDao {

    private final ActiveObjects ao;

    public StoryDao(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }

    public Story create() {
        final Story story = ao.create(Story.class);
        return story;
    }

    public void delete(Story story) {
        ao.delete(story);
    }

    public Story get(Integer id) {
        return ao.get(Story.class, id);
    }

    public List<Story> findAll() {
        return newArrayList(ao.find(Story.class));
    }

    public List<Story> findByProjectAndIssueKey(String projectKey, String issueKey) {
        String[] params = new String[]{projectKey, issueKey};
        Query query = Query.select().where("PROJECT_KEY = ? AND ISSUE_KEY = ?", params);
        Story[] result = ao.find(Story.class, query);
        return newArrayList(result);
    }

    public List<Story> findByProjectKey(String projectKey) {
        String[] params = new String[]{projectKey};
        Query query = Query.select().where("PROJECT_KEY = ?", params);
        Story[] result = ao.find(Story.class, query);
        return newArrayList(result);
    }
}
