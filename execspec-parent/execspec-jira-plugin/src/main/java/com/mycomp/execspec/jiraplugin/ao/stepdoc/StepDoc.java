package com.mycomp.execspec.jiraplugin.ao.stepdoc;

import net.java.ao.Entity;
import net.java.ao.Preload;

/**
 * Created by Dmytro on 4/23/2014.
 */
@Preload
public interface StepDoc extends Entity {

    String getProjectKey();

    void setProjectKey(String projectKey);

    String getStartingWord();

    void setStartingWord(String startingWord);

    String getPattern();

    void setPattern(String pattern);

    String getRegExpPattern();

    void setRegExpPattern(String regExpPattern);

    String getGroupedRegExpPattern();

    void setGroupedRegExpPattern(String groupedRegExpPattern);

    String getParameterGroups();

    void setParameterGroups(String parameterGroups);
}
