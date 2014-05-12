package com.mycomp.execspec.jiraplugin.contextproviders;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.plugin.webfragment.contextproviders.AbstractJiraContextProvider;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmytro on 5/5/2014.
 */
public class StoryPanelContextProvider extends AbstractJiraContextProvider {

    @Override
    public Map getContextMap(User user, JiraHelper jiraHelper) {
        Map contextMap = new HashMap();
        return contextMap;
    }
}
