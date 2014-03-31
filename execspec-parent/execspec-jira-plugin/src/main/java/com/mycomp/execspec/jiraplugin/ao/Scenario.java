package com.mycomp.execspec.jiraplugin.ao;

import net.java.ao.Entity;
import net.java.ao.Preload;

@Preload
public interface Scenario extends Entity {

    //    @NotNull
    Story getStory();

    void setStory(Story story);

    //    @NotNull
    String getText();

    void setText(String text);

}
