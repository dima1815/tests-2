package com.mycomp.execspec.jiraplugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

public final class StoryDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

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
}
