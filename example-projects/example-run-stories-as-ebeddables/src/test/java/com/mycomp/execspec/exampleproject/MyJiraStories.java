package com.mycomp.execspec.exampleproject;

import org.jbehave.core.junit.JUnitStories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 2/15/14.
 */
public class MyJiraStories extends JUnitStories {

    @Override
    protected List<String> storyPaths() {
        List<String> paths = new ArrayList<String>();
        paths.add("jira_stories/TESTING-1.story");
        paths.add("jira_stories/TESTING-2.story");
        return paths;
    }
}
