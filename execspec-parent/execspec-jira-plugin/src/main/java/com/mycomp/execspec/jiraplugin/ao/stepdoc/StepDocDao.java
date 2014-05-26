package com.mycomp.execspec.jiraplugin.ao.stepdoc;

import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.Query;
import org.jbehave.core.steps.StepType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

public final class StepDocDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ActiveObjects ao;

    public StepDocDao(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }

    public StepDoc create() {
        return ao.create(StepDoc.class);
    }

    public void delete(StepDoc stepDoc) {
        ao.delete(stepDoc);
    }

    public StepDoc get(Integer id) {
        return ao.get(StepDoc.class, id);
    }

    public List<StepDoc> findAll() {
        return newArrayList(ao.find(StepDoc.class));
    }

    public List<StepDoc> findAllForProject(String projectKey) {
        String[] params = new String[]{projectKey};
        Query query = Query.select().where("PROJECT_KEY = ?", params);
        StepDoc[] result = ao.find(StepDoc.class, query);
        return newArrayList(result);
    }

    public List<StepDoc> findAllForProject(String projectKey, StepType stepType) {
        String[] params = new String[]{projectKey, stepType.name()};
        Query query = Query.select().where("PROJECT_KEY = ? AND STEP_TYPE = ?", params);
        StepDoc[] result = ao.find(StepDoc.class, query);
        return newArrayList(result);
    }
}
