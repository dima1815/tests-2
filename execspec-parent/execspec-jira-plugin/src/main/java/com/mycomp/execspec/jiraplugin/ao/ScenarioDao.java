package com.mycomp.execspec.jiraplugin.ao;

import com.atlassian.activeobjects.external.ActiveObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

public final class ScenarioDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ActiveObjects ao;

    public ScenarioDao(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }

    public Scenario create() {
        return ao.create(Scenario.class);
    }

    public void delete(Scenario scenario) {
        ao.delete(scenario);
    }

    public Scenario get(Integer id) {
        return ao.get(Scenario.class, id);
    }

    public List<Scenario> findAll() {
        return newArrayList(ao.find(Scenario.class));
    }

}
