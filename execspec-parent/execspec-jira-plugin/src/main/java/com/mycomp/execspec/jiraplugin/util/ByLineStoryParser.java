package com.mycomp.execspec.jiraplugin.util;

import com.mycomp.execspec.jiraplugin.dto.story.output.*;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmytro on 5/20/2014.
 */
public class ByLineStoryParser {

    private enum CompositeElement {
        description,
        meta,
        narrative,
        inOrderTo,
        asA,
        iWantTo,
        soThat,
        givenStories,
        lifecycle,
        beforeSteps,
        afterSteps,
        scenario,
        given,
        when,
        then,
        and
    }

    private static final String EMPTY = "";
    private final Keywords keywords;

    public ByLineStoryParser() {
        this(new LocalizedKeywords());
    }

    public ByLineStoryParser(Keywords keywords) {

        this.keywords = keywords;
    }

    public StoryDTO parseStory(String storyAsText, String storyPath) {

        StoryDTO storyDTO = new StoryDTO();

        if (storyPath != null && !storyPath.isEmpty()) {
            storyDTO.setPath(storyPath);
        }

        String[] lines = storyAsText.split("\\n");
        CompositeElement lastElement = null;
        for (int i = 0; i < lines.length; i++) {

            String line = lines[i].trim();
            int lineNumber = i + 1;
            if (line.isEmpty()) {
                // skip empty lines
            } else {

                // meta
                if (line.startsWith(keywords.meta())) {
                    if (storyDTO.getMeta() != null) {
                        throw new StoryParseException("Found a duplicate " + keywords.meta()
                                + " (meta) keyword declaration on line " + lineNumber);
                    } else if (storyDTO.getNarrative() != null) {
                        throw new StoryParseException("Found an out of order " + keywords.meta()
                                + " (meta) keyword declaration on line " + lineNumber);
                    } else {
                        storyDTO.setMeta(new MetaDTO());
                        lastElement = CompositeElement.meta;
                    }
                }

                // narrative
                else if (line.startsWith(keywords.narrative())) {
                    if (storyDTO.getNarrative() != null) {
                        throw new StoryParseException("Found a duplicate " + keywords.narrative()
                                + " (narrative) keyword declaration on line " + lineNumber);
                    } else {
                        NarrativeDTO narrativeDTO = new NarrativeDTO();
                        narrativeDTO.setKeyword(keywords.narrative());
                        storyDTO.setNarrative(narrativeDTO);
                        lastElement = CompositeElement.narrative;
                    }
                } else if (line.startsWith(keywords.inOrderTo())) {
                    if (storyDTO.getNarrative() == null) {
                        throw new StoryParseException("Found an out of order " + keywords.inOrderTo()
                                + " (inOrderTo) keyword declaration on line " + lineNumber);
                    } else if (storyDTO.getNarrative().getInOrderTo() != null) {
                        throw new StoryParseException("Found a duplicate " + keywords.inOrderTo()
                                + " (inOrderTo) keyword declaration on line " + lineNumber);
                    } else {
                        InOrderToDTO inOrderTo = new InOrderToDTO();
                        inOrderTo.setKeyword(keywords.inOrderTo());
                        String value = line.substring(keywords.inOrderTo().length());
                        if (!value.isEmpty()) {
                            inOrderTo.setValue(value);
                        }
                        storyDTO.getNarrative().setInOrderTo(inOrderTo);
                        lastElement = CompositeElement.inOrderTo;
                    }
                } else if (line.startsWith(keywords.asA())) {
                    if (storyDTO.getNarrative() == null) {
                        throw new StoryParseException("Found an out of order " + keywords.asA()
                                + " (asA) keyword declaration on line " + lineNumber);
                    } else if (storyDTO.getNarrative().getAsA() != null) {
                        throw new StoryParseException("Found a duplicate " + keywords.asA()
                                + " (asA) keyword declaration on line " + lineNumber);
                    } else {
                        AsADTO asA = new AsADTO();
                        asA.setKeyword(keywords.asA());
                        String value = line.substring(keywords.asA().length());
                        if (!value.isEmpty()) {
                            asA.setValue(value);
                        }
                        storyDTO.getNarrative().setAsA(asA);
                        lastElement = CompositeElement.asA;
                    }
                } else if (line.startsWith(keywords.iWantTo())) {
                    if (storyDTO.getNarrative() == null) {
                        throw new StoryParseException("Found an out of order " + keywords.iWantTo()
                                + " (iWantTo) keyword declaration on line " + lineNumber);
                    } else if (storyDTO.getNarrative().getiWantTo() != null) {
                        throw new StoryParseException("Found a duplicate " + keywords.iWantTo()
                                + " (iWantTo) keyword declaration on line " + lineNumber);
                    } else {
                        IWantToDTO iWantTo = new IWantToDTO();
                        iWantTo.setKeyword(keywords.iWantTo());
                        String value = line.substring(keywords.iWantTo().length());
                        if (!value.isEmpty()) {
                            iWantTo.setValue(value);
                        }
                        storyDTO.getNarrative().setiWantTo(iWantTo);
                        lastElement = CompositeElement.iWantTo;
                    }
                } else if (line.startsWith(keywords.soThat())) {
                    if (storyDTO.getNarrative() == null) {
                        throw new StoryParseException("Found an out of order " + keywords.soThat()
                                + " (soThat) keyword declaration on line " + lineNumber);
                    } else if (storyDTO.getNarrative().getSoThat() != null) {
                        throw new StoryParseException("Found a duplicate " + keywords.soThat()
                                + " (soThat) keyword declaration on line " + lineNumber);
                    } else {
                        SoThatDTO soThat = new SoThatDTO();
                        soThat.setKeyword(keywords.soThat());
                        String value = line.substring(keywords.soThat().length());
                        if (!value.isEmpty()) {
                            soThat.setValue(value);
                        }
                        storyDTO.getNarrative().setSoThat(soThat);
                        lastElement = CompositeElement.soThat;
                    }
                }

                // givenStories
                else if (line.startsWith(keywords.givenStories())) {
                    if (storyDTO.getGivenStories() != null) {
                        throw new StoryParseException("Found a duplicate " + keywords.givenStories()
                                + " (givenStories) keyword declaration on line " + lineNumber);
                    }
//                    else if (storyDTO.narrative == null || lastElement != CompositeElement.scenario) {
//                      TODO - implement check when this is an out of order element and throw exception
//                    }
                    else {
//                        List<String> paths = new ArrayList<String>();
//                        paths.add(line.substring(keywords.givenStories().length())); // TODO - implement properly
                        storyDTO.setGivenStories(new GivenStoriesDTO());
                        lastElement = CompositeElement.givenStories;
                    }
                }

                // lifecycle
                else if (line.startsWith(keywords.lifecycle())) {
                    if (storyDTO.getLifecycle() != null) {
                        throw new StoryParseException("Found a duplicate " + keywords.lifecycle()
                                + " (Lifecycle) keyword declaration on line " + lineNumber);
                    } else {
                        storyDTO.setLifecycle(new LifecycleDTO());
                        lastElement = CompositeElement.lifecycle;
                    }
                }

                // scenario
                else if (line.startsWith(keywords.scenario())) {
                    ScenarioDTO scenarioDto = new ScenarioDTO();
                    scenarioDto.setKeyword(keywords.scenario());
                    String title = line.substring(keywords.scenario().length());
                    if (!title.isEmpty()) {
                        scenarioDto.setTitle(title);
                    }
                    if (storyDTO.getScenarios() == null) {
                        storyDTO.setScenarios(new ArrayList<ScenarioDTO>());
                    }
                    storyDTO.getScenarios().add(scenarioDto);
                    lastElement = CompositeElement.scenario;
                } else if (line.startsWith(keywords.given())) {

                    if (storyDTO.getScenarios().isEmpty()) {
                        throw new StoryParseException("Found an out of order " + keywords.given()
                                + " (Given) keyword declaration on line " + lineNumber);
                    }

                    ScenarioDTO scenario = storyDTO.getScenarios().get(storyDTO.getScenarios().size() - 1);
                    List<String> steps = scenario.getSteps();
                    if (steps == null) {
                        steps = new ArrayList<String>();
                        scenario.setSteps(steps);
                    }
                    steps.add(line);
                    lastElement = CompositeElement.given;
                } else if (line.startsWith(keywords.when())) {

                    if (storyDTO.getScenarios().isEmpty()) {
                        throw new StoryParseException("Found an out of order " + keywords.when()
                                + " (When) keyword declaration on line " + lineNumber);
                    }

                    ScenarioDTO scenario = storyDTO.getScenarios().get(storyDTO.getScenarios().size() - 1);
                    List<String> steps = scenario.getSteps();
                    if (steps == null) {
                        steps = new ArrayList<String>();
                        scenario.setSteps(steps);
                    }
                    steps.add(line);
                    lastElement = CompositeElement.when;
                } else if (line.startsWith(keywords.then())) {

                    if (storyDTO.getScenarios().isEmpty()) {
                        throw new StoryParseException("Found an out of order " + keywords.then()
                                + " (Then) keyword declaration on line " + lineNumber);
                    }

                    ScenarioDTO scenario = storyDTO.getScenarios().get(storyDTO.getScenarios().size() - 1);
                    List<String> steps = scenario.getSteps();
                    if (steps == null) {
                        steps = new ArrayList<String>();
                        scenario.setSteps(steps);
                    }
                    steps.add(line);
                    lastElement = CompositeElement.then;
                } else if (line.startsWith(keywords.and())) {

                    if (storyDTO.getScenarios().isEmpty()) {
                        throw new StoryParseException("Found an out of order " + keywords.and()
                                + " (And) keyword declaration on line " + lineNumber);
                    }

                    ScenarioDTO scenario = storyDTO.getScenarios().get(storyDTO.getScenarios().size() - 1);
                    List<String> steps = scenario.getSteps();
                    if (steps == null) {
                        throw new StoryParseException("Found an out of order " + keywords.and() + " (And) keyword " +
                                "declaration on line " + lineNumber);
                    }
                    steps.add(line);
                    lastElement = CompositeElement.and;
                } else {
                    // line doesn't start with any of the keywords, so try to append to either description if at the
                    // start of the storyDTO or the last element if it is "appendable"
                    if (storyDTO.getMeta() == null
                            && storyDTO.getNarrative() == null
                            && storyDTO.getGivenStories() == null
                            && storyDTO.getLifecycle() == null
                            && storyDTO.getScenarios() == null) {
                        // we are at the start and can create/append to description
                        if (storyDTO.getDescription() == null) {
                            storyDTO.setDescription(line);
                        } else {
                            storyDTO.setDescription(storyDTO.getDescription() + "\n" + line);
                        }
                    } else if (line.startsWith("@") && storyDTO.getMeta() != null
                            && lastElement == CompositeElement.meta) {
                        Map properties = storyDTO.getMeta().getProperties();
                        if (properties == null) {
                            properties = new HashMap();
                            storyDTO.getMeta().setProperties(properties);
                        }
                        if (line.length() == 0) {
                            throw new StoryParseException("Meta properties must have a minimum of 1 character");
                        } else {
                            String withoutAtChar = line.substring(1);
                            int firstSpaceIndex = withoutAtChar.indexOf(" ");
                            if (firstSpaceIndex != -1) {
                                String key = withoutAtChar.substring(0, firstSpaceIndex);
                                String value = withoutAtChar.substring(firstSpaceIndex + 1);
                                properties.put(key, value);
                            } else {
                                // some properties don't have value, e.g. @skip
                                properties.put(withoutAtChar, null);
                            }
                        }
                    } else {
                        // should not get here, unless the storyDTO structure/format is wrong
                        throw new StoryParseException("An out of order line found - \n" + line);
                    }

                }


            }


        }

        return storyDTO;
    }


}
