package com.mycomp.execspec.jiraplugin.util;

import org.apache.commons.lang.StringUtils;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.model.*;
import org.jbehave.core.parsers.StoryParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.removeStart;

/**
 * Created by Dmytro on 5/20/2014.
 */
public class CustomRegExStoryParser implements StoryParser {

    private static final String NONE = "";
    private final Keywords keywords;
    private final ExamplesTableFactory tableFactory;

    public CustomRegExStoryParser() {
        this(new LocalizedKeywords());
    }

    public CustomRegExStoryParser(Keywords keywords) {
        this(keywords, new ExamplesTableFactory());
    }

    public CustomRegExStoryParser(ExamplesTableFactory tableFactory) {
        this(new LocalizedKeywords(), tableFactory);
    }

    public CustomRegExStoryParser(Keywords keywords, ExamplesTableFactory tableFactory) {
        this.keywords = keywords;
        this.tableFactory = tableFactory;
    }

    public Story parseStory(String storyAsText) {
        return parseStory(storyAsText, null);
    }

    public Story parseStory(String storyAsText, String storyPath) {

        Description description = parseDescriptionFrom(storyAsText);
        Meta meta = parseStoryMetaFrom(storyAsText);
        Narrative narrative = parseNarrativeFrom(storyAsText);
        GivenStories givenStories = parseGivenStories(storyAsText);
        Lifecycle lifecycle = parseLifecycle(storyAsText);
        List<Scenario> scenarios = parseScenariosFrom(storyAsText);
        Story story = new Story(storyPath, description, meta, narrative, givenStories, lifecycle, scenarios);
        if (storyPath != null) {
            story.namedAs(new File(storyPath).getName());
        }
        return story;
    }

    private Description parseDescriptionFrom(String storyAsText) {
        Matcher findingDescription = patternToPullDescriptionIntoGroupOne().matcher(storyAsText);
        if (findingDescription.matches()) {
            return new Description(findingDescription.group(1).trim());
        }
        return Description.EMPTY;
    }

    private Meta parseStoryMetaFrom(String storyAsText) {
        Matcher findingMeta = patternToPullStoryMetaIntoGroupOne().matcher(preScenarioText(storyAsText));
        if (findingMeta.matches()) {
            String meta = findingMeta.group(1).trim();
            return Meta.createMeta(meta, keywords);
        }
        return Meta.EMPTY;
    }

    private String preScenarioText(String storyAsText) {
        String[] split = storyAsText.split(keywords.scenario());
        return split.length > 0 ? split[0] : storyAsText;
    }

    private Narrative parseNarrativeFrom(String storyAsText) {
        Matcher findingNarrative = patternToPullNarrativeIntoGroupOne().matcher(storyAsText);
        if (findingNarrative.matches()) {
            String narrative = findingNarrative.group("narrativeContent").trim();
            return createNarrative(narrative);
        }
        return Narrative.EMPTY;
    }

    private Narrative createNarrative(String narrative) {
        Matcher findingElements = patternToPullNarrativeElementsIntoGroups().matcher(narrative);
        if (findingElements.matches()) {
            String inOrderToGroup = findingElements.group("inOrderTo");
            String inOrderTo;
            if (inOrderToGroup != null) {
                inOrderTo = inOrderToGroup.trim();
            } else {
                inOrderTo = EMPTY;
            }
            String asAGroup = findingElements.group("asA");
            String asA;
            if (asAGroup != null) {
                asA = asAGroup.trim();
            } else {
                asA = EMPTY;
            }
            String iWantToGroup = findingElements.group("iWantTo");
            String iWantTo;
            if (iWantToGroup != null) {
                iWantTo = iWantToGroup.trim();
            } else {
                iWantTo = EMPTY;
            }
            return new Narrative(inOrderTo, asA, iWantTo);
        }
        Matcher findingAlternativeElements = patternToPullAlternativeNarrativeElementsIntoGroups().matcher(narrative);
        if (findingAlternativeElements.matches()) {
            String asA = findingAlternativeElements.group(1).trim();
            String iWantTo = findingAlternativeElements.group(2).trim();
            String soThat = findingAlternativeElements.group(3).trim();
            return new Narrative("", asA, iWantTo, soThat);
        }
        return Narrative.EMPTY;
    }

    private GivenStories parseGivenStories(String storyAsText) {
        String scenarioKeyword = keywords.scenario();
        // use text before scenario keyword, if found
//        String beforeScenario = "";
//        if (StringUtils.contains(storyAsText, scenarioKeyword)) {
//            beforeScenario = StringUtils.substringBefore(storyAsText, scenarioKeyword);
//        }
        Matcher findingGivenStories = patternToPullStoryGivenStoriesIntoGroupOne().matcher(storyAsText);
        if (findingGivenStories.matches()) {
            String title = findingGivenStories.group("givenStoriesTitle");
            if (title != null) {
                String content = findingGivenStories.group("givenStoriesContent");
                if (content == null) {
                    content = EMPTY;
                }
                GivenStories givenStories = new GivenStories(content);
                return givenStories;
            } else {
                return GivenStories.EMPTY;
            }
        } else {
            return GivenStories.EMPTY;
        }
    }

    private Lifecycle parseLifecycle(String storyAsText) {
        String scenarioKeyword = keywords.scenario();
        // use text before scenario keyword, if found
//        String beforeScenario = "";
//        if (StringUtils.contains(storyAsText, scenarioKeyword)) {
//            beforeScenario = StringUtils.substringBefore(storyAsText, scenarioKeyword);
//        }

        Matcher findingLifecycle = patternToPullLifecycleIntoGroupOne().matcher(storyAsText);
        if (findingLifecycle.matches() && findingLifecycle.group("lifecycleTitle") != null) {
            String lifecycleContent = findingLifecycle.group("lifecycleContent");
            if (lifecycleContent != null) {
                // parse lifecycle before and after steps
                Matcher findingBeforeAndAfter = compile(".*" + keywords.before() + "(.*)\\s*" + keywords.after() + "(.*)\\s*", DOTALL).matcher(lifecycleContent);
                if (findingBeforeAndAfter.matches()) {
                    List<String> beforeSteps = findSteps(startingWithNL(findingBeforeAndAfter.group(1).trim()));
                    List<String> afterSteps = findSteps(startingWithNL(findingBeforeAndAfter.group(2).trim()));
                    return new Lifecycle(beforeSteps, afterSteps);
                }
                Matcher findingBefore = compile(".*" + keywords.before() + "(.*)\\s*", DOTALL).matcher(lifecycleContent);
                if (findingBefore.matches()) {
                    List<String> beforeSteps = findSteps(startingWithNL(findingBefore.group(1).trim()));
                    return new Lifecycle(beforeSteps, null);
                }
                Matcher findingAfter = compile(".*" + keywords.after() + "(.*)\\s*", DOTALL).matcher(lifecycleContent);
                if (findingAfter.matches()) {
                    List<String> afterSteps = findSteps(startingWithNL(findingAfter.group(1).trim()));
                    return new Lifecycle(null, afterSteps);
                }
                return new Lifecycle(null, null);
            } else {
                return new Lifecycle(null, null);
            }
        } else {
            return Lifecycle.EMPTY;
        }
    }

    private List<Scenario> parseScenariosFrom(String storyAsText) {
        List<Scenario> parsed = new ArrayList<Scenario>();
        for (String scenarioAsText : splitScenarios(storyAsText)) {
            parsed.add(parseScenario(scenarioAsText));
        }
        return parsed;
    }

    private List<String> splitScenarios(String storyAsText) {
        List<String> scenarios = new ArrayList<String>();
        String scenarioKeyword = keywords.scenario();

        // use text after scenario keyword, if found
        if (StringUtils.contains(storyAsText, scenarioKeyword)) {
            String textAfterFirstScenario = StringUtils.substringAfter(storyAsText, scenarioKeyword);
            String[] scenarioTokens = textAfterFirstScenario.split(scenarioKeyword);
            for (String scenarioAsText : scenarioTokens) {
                if (scenarioAsText.trim().length() > 0) {
//                    scenarios.add(scenarioKeyword + "\n" + scenarioAsText);
                    scenarios.add(scenarioKeyword + scenarioAsText);
                }
            }
        }

        return scenarios;
    }

    private Scenario parseScenario(String scenarioAsText) {
        String title = findScenarioTitle(scenarioAsText);
        String scenarioWithoutKeyword = removeStart(scenarioAsText, keywords.scenario()).trim();
        String scenarioWithoutTitle = removeStart(scenarioWithoutKeyword, title);
        scenarioWithoutTitle = startingWithNL(scenarioWithoutTitle);
        Meta meta = findScenarioMeta(scenarioWithoutTitle);
        ExamplesTable examplesTable = findExamplesTable(scenarioWithoutTitle);
        GivenStories givenStories = findScenarioGivenStories(scenarioWithoutTitle);
        if (givenStories.requireParameters()) {
            givenStories.useExamplesTable(examplesTable);
        }
        List<String> steps = findSteps(scenarioWithoutTitle);
        return new Scenario(title, meta, givenStories, examplesTable, steps);
    }

    private String startingWithNL(String text) {
        if (!text.startsWith("\n")) { // always ensure starts with newline
            return "\n" + text;
        }
        return text;
    }

    private String findScenarioTitle(String scenarioAsText) {
        Matcher findingTitle = patternToPullScenarioTitleIntoGroupOne().matcher(scenarioAsText);
        if (findingTitle.matches()) {
            String result = findingTitle.group(1).trim();
            return result;
        } else {
            return NONE;
        }
    }

    private Meta findScenarioMeta(String scenarioAsText) {
        Matcher findingMeta = patternToPullScenarioMetaIntoGroupOne().matcher(scenarioAsText);
        if (findingMeta.matches()) {
            String meta = findingMeta.group(1).trim();
            return Meta.createMeta(meta, keywords);
        }
        return Meta.EMPTY;
    }

    private ExamplesTable findExamplesTable(String scenarioAsText) {

        Matcher findingTable = patternToPullExamplesTableIntoGroupOne().matcher(scenarioAsText);

        if (findingTable.find()) {
            String tableContent = findingTable.group("examplesTableContent").trim();
            return tableFactory.createExamplesTable(tableContent);
        } else {
            return ExamplesTable.EMPTY;
        }

    }

    private GivenStories findScenarioGivenStories(String scenarioAsText) {
        Matcher findingGivenStories = patternToPullScenarioGivenStoriesIntoGroupOne().matcher(scenarioAsText);
        String givenStories = findingGivenStories.find() ? findingGivenStories.group(1).trim() : NONE;
        if (givenStories.isEmpty()) {
            return GivenStories.EMPTY;
        } else {
            return new GivenStories(givenStories);
        }
    }

    private List<String> findSteps(String scenarioAsText) {
        Matcher matcher = patternToPullStepsIntoGroupOne().matcher(scenarioAsText);
        List<String> steps = new ArrayList<String>();
        int startAt = 0;
        while (matcher.find(startAt)) {
            steps.add(StringUtils.substringAfter(matcher.group(1), "\n"));
            startAt = matcher.start(4);
        }
        return steps;
    }

    // Regex Patterns

    private Pattern patternToPullDescriptionIntoGroupOne() {
        String metaOrNarrativeOrLifecycleOrScenario = concatenateWithOr(keywords.meta(), keywords.narrative(), keywords.lifecycle(), keywords.scenario());
        return compile("(.*?)(" + metaOrNarrativeOrLifecycleOrScenario + ").*", DOTALL);
    }

    private Pattern patternToPullStoryMetaIntoGroupOne() {
        String narrativeOrGivenStories = concatenateWithOr(keywords.narrative(), keywords.givenStories());
        return compile(".*" + keywords.meta() + "(.*?)\\s*(\\Z|" + narrativeOrGivenStories + ").*", DOTALL);
    }

    private Pattern patternToPullNarrativeIntoGroupOne() {
        String givenStoriesOrLifecycleOrScenario = concatenateWithOr(keywords.givenStories(), keywords.lifecycle(), keywords.scenario());
        return compile(
                "(.*?)"
                        + "(?<narrativeTitle>"
                        + keywords.narrative()
                        + ")"
                        + "(?<narrativeContent>.*)"
//                        + "(\\s*)"
                        + "("
                        + givenStoriesOrLifecycleOrScenario
                        + ")?"
                        + "(.*?)",
                DOTALL
        );
    }

    private Pattern patternToPullNarrativeElementsIntoGroups() {
        String pattern = "(.*?)"
                + "(?<inOrderTo>" + keywords.inOrderTo() + ")?"
                + "(.*?)(\\s*)"
                + "(?<asA>" + keywords.asA() + ")?"
                + "(.*?)(\\s*)"
                + "(?<iWantTo>" + keywords.iWantTo() + ")?"
                + "(.*)";
        return compile(pattern, DOTALL);
    }

    private Pattern patternToPullAlternativeNarrativeElementsIntoGroups() {
        return compile(".*" + keywords.asA() + "(.*)\\s*" + keywords.iWantTo() + "(.*)\\s*" + keywords.soThat()
                + "(.*)", DOTALL);
    }

    private Pattern patternToPullStoryGivenStoriesIntoGroupOne() {
        String lifecycleOrScenario = concatenateWithOr(keywords.lifecycle(), keywords.scenario());
        return compile(
                "(.*?)"
                        + "(?<givenStoriesTitle>"
                        + keywords.givenStories()
                        + ")"
                        + "(?<givenStoriesContent>.*?)" +
                        "(\\s*)" +
                        "(\\Z|"
                        + lifecycleOrScenario
                        + ")?" +
                        "(.*)",
                DOTALL
        );
    }

    private Pattern patternToPullLifecycleIntoGroupOne() {
        return compile(
                "(.*?)"
                        + "(?<lifecycleTitle>"
                        + keywords.lifecycle()
                        + ")"
                        + "(\\s)*" +
                        "(?<lifecycleContent>.*)"
                        + "(" + keywords.scenario() + ")?(.*)",
                DOTALL
        );
    }

    private Pattern patternToPullScenarioTitleIntoGroupOne() {
        String startingWords = concatenateWithOr("\\n", "", keywords.startingWords());
        return compile(
                keywords.scenario()
                        + "(.*?)" +
                        "(\\n!--(.*))?" +
                        "(\\n" + keywords.meta() + "(.*))?" +
                        "(\\n" + keywords.given() + "(.*))?" +
                        "(\\n" + keywords.when() + "(.*))?" +
                        "(\\n" + keywords.then() + "(.*))?" +
                        "(\\n" + keywords.and() + "(.*))?" +
                        "(\\n" + keywords.examplesTable() + "(.*))?",
                DOTALL
        );
    }

    private Pattern patternToPullScenarioMetaIntoGroupOne() {
        String startingWords = concatenateWithOr("\\n", "", keywords.startingWords());
        return compile(".*" + keywords.meta() + "(.*?)\\s*(" + keywords.givenStories() + "|" + startingWords + ").*",
                DOTALL);
    }

    private Pattern patternToPullScenarioGivenStoriesIntoGroupOne() {
        String startingWords = concatenateWithOr("\\n", "", keywords.startingWords());
        return compile("\\n" + keywords.givenStories() + "((.|\\n)*?)\\s*(" + startingWords + ").*", DOTALL);
    }

    private Pattern patternToPullStepsIntoGroupOne() {
        String initialStartingWords = concatenateWithOr("\\n", "", keywords.startingWords());
        String followingStartingWords = concatenateWithOr("\\n", "\\s", keywords.startingWords());
        return compile(
                "((" + initialStartingWords + ")\\s(.)*?)\\s*(\\Z|" + followingStartingWords + "|\\n"
                        + keywords.examplesTable() + ")", DOTALL
        );
    }

    private Pattern patternToPullExamplesTableIntoGroupOne() {
        return compile("\\n" + "(?<examplesTableTitle>" + keywords.examplesTable() + ")" + "\\s*(?<examplesTableContent>.*)", DOTALL);
    }

    private String concatenateWithOr(String... keywords) {
        return concatenateWithOr(null, null, keywords);
    }

    private String concatenateWithOr(String beforeKeyword, String afterKeyword, String[] keywords) {
        StringBuilder builder = new StringBuilder();
        String before = beforeKeyword != null ? beforeKeyword : NONE;
        String after = afterKeyword != null ? afterKeyword : NONE;
        for (String keyword : keywords) {
            builder.append(before).append(keyword).append(after).append("|");
        }
        return StringUtils.chomp(builder.toString(), "|"); // chop off the last
        // "|"
    }


}
