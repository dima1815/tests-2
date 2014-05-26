package com.mycomp.execspec.jiraplugin.util;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.model.*;
import org.jbehave.core.parsers.StoryParser;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Dmytro on 5/20/2014.
 */
public class ByLineStoryParser implements StoryParser {

    private static final String EMPTY = "";
    private final Keywords keywords;
    private final ExamplesTableFactory tableFactory;

    public ByLineStoryParser() {
        this(new LocalizedKeywords());
    }

    public ByLineStoryParser(Keywords keywords) {
        this(keywords, new ExamplesTableFactory());
    }

    public ByLineStoryParser(ExamplesTableFactory tableFactory) {
        this(new LocalizedKeywords(), tableFactory);
    }

    public ByLineStoryParser(Keywords keywords, ExamplesTableFactory tableFactory) {
        this.keywords = keywords;
        this.tableFactory = tableFactory;
    }

    @Override
    public Story parseStory(String storyAsText) {
        return parseStory(storyAsText, null);
    }

    @Override
    public Story parseStory(String storyAsText, String storyPath) {

        String[] lines = storyAsText.split("\\n");

        Story story = new Story(storyPath, Description.EMPTY, Meta.EMPTY, Narrative.EMPTY,
                GivenStories.EMPTY, Lifecycle.EMPTY, new ArrayList<Scenario>());
        if (storyPath != null) {
            story.namedAs(new File(storyPath).getName());
        }

        for (String line : lines) {

            // meta
            if (line.startsWith(keywords.meta())
                    && story.getMeta() == Meta.EMPTY
                    && story.getNarrative() == Narrative.EMPTY) {

            }

        }


//        Description description = parseDescriptionFrom(storyAsText);
//        Meta meta = parseStoryMetaFrom(storyAsText);
//        Narrative narrative = parseNarrativeFrom(storyAsText);
//        GivenStories givenStories = parseGivenStories(storyAsText);
//        Lifecycle lifecycle = parseLifecycle(storyAsText);
//        List<Scenario> scenarios = parseScenariosFrom(storyAsText);
//        Story story = new Story(storyPath, description, meta, narrative, givenStories, lifecycle, scenarios);
//        if (storyPath != null) {
//            story.namedAs(new File(storyPath).getName());
//        }
//        return story;
        return null;
    }


}
