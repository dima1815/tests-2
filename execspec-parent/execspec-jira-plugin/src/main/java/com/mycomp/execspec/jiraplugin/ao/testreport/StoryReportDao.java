package com.mycomp.execspec.jiraplugin.ao.testreport;

import com.atlassian.activeobjects.external.ActiveObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

public final class StoryReportDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ActiveObjects ao;

    public StoryReportDao(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }

    public StoryHtmlReport createStoryHtmlReport() {
        return ao.create(StoryHtmlReport.class);
    }

    public void delete(StoryHtmlReport storyHtmlReport) {
        ao.delete(storyHtmlReport);
    }

    public StoryHtmlReport get(Integer id) {
        return ao.get(StoryHtmlReport.class, id);
    }

    public List<StoryHtmlReport> findAll() {
        return newArrayList(ao.find(StoryHtmlReport.class));
    }

}
