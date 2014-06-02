package com.mycomp.execspec.jiraplugin.service;

import com.mycomp.execspec.jiraplugin.dto.autocomplete.AutoCompleteDTO;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import com.mycomp.execspec.jiraplugin.util.CustomRegExStoryParser;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.model.*;
import org.jbehave.core.parsers.StoryParser;
import org.jbehave.core.steps.StepType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Dmytro on 5/15/2014.
 */
public class AutoCompleteServiceImpl implements AutoCompleteService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private StepDocsSerivce stepDocsSerivce;

    public AutoCompleteServiceImpl(StepDocsSerivce stepDocsSerivce) {
        this.stepDocsSerivce = stepDocsSerivce;
    }

    private static enum AutoCompleteEntry {

        Meta("meta:\n"),
        MetaSkip("@skip\n"),
        MetaIgnored("@ignored true\n"),
        MetaAuthor("@author "),

        Narrative("narrative:\n"),
        GivenStories("givenStories: "),
        Lifecycle("lifecycle:\n"),
        Scenario("scenario: "),
        InOrderTo("In order to "),
        AsA("As a "),
        IWantTo("I want to "),
        SoThat("So that "),
        Before("Before:\n"),
        After("After:\n"),
        Examples("Examples:\n"),
        Given("Given "),
        When("When "),
        Then("Then "),
        And("And ");

        public final String asString;

        AutoCompleteEntry(String asString) {
            this.asString = asString;
        }

        public String autoComplete(String input) {

            if (input.length() == 0) {
                return this.asString;
            } else if (this.asString.startsWith(input) && input.length() < this.asString.length()) {
                return this.asString.substring(input.length());
            } else {
                return null;
            }

        }
    }

    @Override
    public List<AutoCompleteDTO> autoComplete(String projectKey, String input) {

        log.debug("input - " + input);

        List<AutoCompleteDTO> results = new ArrayList<AutoCompleteDTO>();

        StoryParser storyParser;
//        regexStoryParser = new RegexStoryParser();
        storyParser = new CustomRegExStoryParser();
        Story story = storyParser.parseStory(input);

        String lastLine;
        if (input.endsWith("\n")) {
            lastLine = "";
        } else if (input.lastIndexOf("\n") != -1) {
            lastLine = input.substring(input.lastIndexOf("\n") + 1);
        } else {
            lastLine = input;
        }

        autoCompleteForMeta(story, lastLine, results);
        autoCompleteForNarrative(story, lastLine, results);
        autoCompleteForGivenStories(story, lastLine, results);
        autoCompleteForLifecycle(story, lastLine, results);
        autoCompleteForScenario(story, lastLine, results);
        autoCompleteForScenarioKeywords(story, lastLine, results);

        autoCompleteForSteps(story, lastLine, results, projectKey);

        return results;
    }

    private void autoCompleteForSteps(Story story, String lastLine, List<AutoCompleteDTO> results, String projectKey) {

        LocalizedKeywords keywords = new LocalizedKeywords();

        List<Scenario> scenarios = story.getScenarios();
        if (!scenarios.isEmpty()) {

            Scenario scenario = scenarios.get(scenarios.size() - 1);

            ExamplesTable examplesTable = scenario.getExamplesTable();
            if (examplesTable == ExamplesTable.EMPTY) {

                String givenAutoComplete = AutoCompleteEntry.Given.autoComplete(lastLine);
                if (givenAutoComplete != null || lastLine.startsWith(keywords.given())) {
                    autoCompleteForGivenSteps(story, lastLine, results, projectKey, keywords);
                } else {
                    String whenAutoComplete = AutoCompleteEntry.When.autoComplete(lastLine);
                    if (whenAutoComplete != null || lastLine.startsWith(keywords.when())) {
                        autoCompleteForWhenSteps(story, lastLine, results, projectKey, keywords);
                    } else {
                        String thenAutoComplete = AutoCompleteEntry.Then.autoComplete(lastLine);
                        if (thenAutoComplete != null || lastLine.startsWith(keywords.then())) {
                            autoCompleteForThenSteps(story, lastLine, results, projectKey, keywords);
                        } else {
                            String andAutoComplete = AutoCompleteEntry.And.autoComplete(lastLine);
                            List<String> steps = scenario.getSteps();
                            if (andAutoComplete != null && !steps.isEmpty()) {
                                String lastStep = steps.get(steps.size() - 1);
                                // figure out what step type to complete on based on the previous step
                                if (lastStep.startsWith(keywords.given())) {
                                    autoCompleteForGivenSteps(story, lastLine, results, projectKey, keywords);
                                } else if (lastStep.startsWith(keywords.when())) {
                                    autoCompleteForWhenSteps(story, lastLine, results, projectKey, keywords);
                                } else if (lastStep.startsWith(keywords.then())) {
                                    autoCompleteForThenSteps(story, lastLine, results, projectKey, keywords);
                                }
                            }
                        }
                    }
                }

            }

        }
    }

    private void autoCompleteForGivenSteps(Story story, String lastLine, List<AutoCompleteDTO> results, String projectKey, Keywords keywords) {

        List<StepDocDTO> stepsForProject = this.stepDocsSerivce.findForProject(projectKey, StepType.GIVEN);
        autoCompleteForKeywordSteps(lastLine, results, stepsForProject, keywords.given());
    }

    private void autoCompleteForWhenSteps(Story story, String lastLine, List<AutoCompleteDTO> results, String projectKey, Keywords keywords) {

        List<StepDocDTO> stepsForProject = this.stepDocsSerivce.findForProject(projectKey, StepType.WHEN);
        autoCompleteForKeywordSteps(lastLine, results, stepsForProject, keywords.when());
    }

    private void autoCompleteForThenSteps(Story story, String lastLine, List<AutoCompleteDTO> results, String projectKey, Keywords keywords) {

        List<StepDocDTO> stepsForProject = this.stepDocsSerivce.findForProject(projectKey, StepType.THEN);
        autoCompleteForKeywordSteps(lastLine, results, stepsForProject, keywords.then());
    }

    private void autoCompleteForKeywordSteps(String lastLine, List<AutoCompleteDTO> results, List<StepDocDTO> stepsForProject, String keyword) {
        for (StepDocDTO stepDocDTO : stepsForProject) {

            String strPattern = stepDocDTO.getPattern();
            String strPatternWithKeyword = keyword + " " + strPattern;
            String regExpPattern = stepDocDTO.getRegExpPattern();
            String regExpPatternWithKeyword = keyword + " " + regExpPattern;

            // find the maximum matching part of the pattern
            char[] chars = regExpPatternWithKeyword.toCharArray();
            StringBuilder sb = new StringBuilder();
            int matchingPartLength = 0;
            for (char aChar : chars) {
                sb.append(aChar);
                String part = sb.toString();
                Pattern partPattern;
                try {
                    partPattern = Pattern.compile(part);
                    Matcher matcher = partPattern.matcher(lastLine);
                    if (matcher.matches()) {
                        matchingPartLength = sb.length();
                    }
                } catch (PatternSyntaxException pse) {
                    // ignore
                }

//                else if (!part.endsWith("\\")) {
//                    // we stop trying more of the pattern only if the part doesn't end with the '\' as this character
//                    // is used for escaping in regexp, e.g. \\n or \\s
//                    break;
//                }
            }

            if (matchingPartLength > 0) {
                // we found a match for part of the pattern, workout the auto complete part using the string
                // representation of the pattern
                String matchingSubPattern = regExpPatternWithKeyword.substring(0, matchingPartLength);
                Pattern subPattern = Pattern.compile(matchingSubPattern);
                Matcher matcher = subPattern.matcher(strPatternWithKeyword);
                boolean found = matcher.find();
                if (found) {
                    int matchEnd = matcher.end();
                    if (matchEnd < strPatternWithKeyword.length()) {
                        String autoCopmleteForPattern = strPatternWithKeyword.substring(matchEnd);
                        AutoCompleteDTO entry = new AutoCompleteDTO(strPatternWithKeyword, autoCopmleteForPattern);
                        results.add(entry);
                    }
                } else {
                    throw new RuntimeException("Sub pattern - " + matchingSubPattern
                            + "for the step auto complete failed to be found for input string - '" + strPatternWithKeyword + "'.");
                }
            }

//            Pattern pattern = Pattern.compile(patternStr);

            System.out.println();

        }
    }

    private void autoCompleteForScenarioKeywords(Story story, String lastLine, List<AutoCompleteDTO> results) {

        List<Scenario> scenarios = story.getScenarios();

        if (!scenarios.isEmpty()) {

            Scenario lastScenario = scenarios.get(scenarios.size() - 1);

            Meta meta = lastScenario.getMeta();
            GivenStories givenStories = lastScenario.getGivenStories();
            List<String> steps = lastScenario.getSteps();
            ExamplesTable examplesTable = lastScenario.getExamplesTable();

            if (meta == Meta.EMPTY
                    && givenStories == GivenStories.EMPTY
                    && steps.isEmpty()
                    && examplesTable == ExamplesTable.EMPTY) {
                autoCompleteEntry(AutoCompleteEntry.Meta, lastLine, results);
            }

            if (givenStories == GivenStories.EMPTY
                    && steps.isEmpty()
                    && examplesTable == ExamplesTable.EMPTY) {
                autoCompleteEntry(AutoCompleteEntry.GivenStories, lastLine, results);
            }

            if (examplesTable == ExamplesTable.EMPTY) {
                autoCompleteEntry(AutoCompleteEntry.Given, lastLine, results);
                autoCompleteEntry(AutoCompleteEntry.When, lastLine, results);
                autoCompleteEntry(AutoCompleteEntry.Then, lastLine, results);
                autoCompleteEntry(AutoCompleteEntry.Examples, lastLine, results);
                if (!steps.isEmpty()) {
                    // advise on And only if there are already steps present
                    autoCompleteEntry(AutoCompleteEntry.And, lastLine, results);
                }
            }

        }

    }

    private void autoCompleteForScenario(Story story, String lastLine, List<AutoCompleteDTO> results) {

        autoCompleteEntry(AutoCompleteEntry.Scenario, lastLine, results);
        List<Scenario> scenarios = story.getScenarios();
        if (!scenarios.isEmpty()) {
            Scenario lastScenario = scenarios.get(scenarios.size() - 1);
            Meta scenarioMeta = lastScenario.getMeta();
            GivenStories scenarioGivenStories = lastScenario.getGivenStories();
            List<String> steps = lastScenario.getSteps();
            ExamplesTable examplesTable = lastScenario.getExamplesTable();
            System.out.println();
        }
    }

    private void autoCompleteForLifecycle(Story story, String lastLine, List<AutoCompleteDTO> results) {

        Lifecycle lifecycle = story.getLifecycle();
        if (lifecycle == Lifecycle.EMPTY) {
            if (story.getScenarios().isEmpty()) {
                autoCompleteEntry(AutoCompleteEntry.Lifecycle, lastLine, results);
            }
        } else {
            if (story.getScenarios().isEmpty()) {
                // auto complete on Before and After Steps
                List<String> beforeSteps = lifecycle.getBeforeSteps();
                List<String> afterSteps = lifecycle.getAfterSteps();
                if (beforeSteps == null && afterSteps == null) {
                    autoCompleteEntry(AutoCompleteEntry.Before, lastLine, results);
                }
                if (afterSteps == null) {
                    autoCompleteEntry(AutoCompleteEntry.After, lastLine, results);
                }
            }
        }
    }

    private void autoCompleteForGivenStories(Story story, String lastLine, List<AutoCompleteDTO> results) {

        GivenStories givenStories = story.getGivenStories();
        if (givenStories == GivenStories.EMPTY) {
            if (story.getLifecycle() == Lifecycle.EMPTY
                    && story.getScenarios().isEmpty()) {
                autoCompleteEntry(AutoCompleteEntry.GivenStories, lastLine, results);
            }
        } else {
            // TODO - implement auto complete on story names used in givenStories
        }
    }

    private void autoCompleteForNarrative(Story story, String lastLine, List<AutoCompleteDTO> results) {

        Narrative narrative = story.getNarrative();
        if (narrative == Narrative.EMPTY
                && story.getGivenStories() == GivenStories.EMPTY
                && story.getLifecycle() == Lifecycle.EMPTY
                && story.getScenarios().isEmpty()) {
            autoCompleteEntry(AutoCompleteEntry.Narrative, lastLine, results);
        } else {
            if (narrative.inOrderTo().isEmpty()
                    && story.getGivenStories() == GivenStories.EMPTY
                    && story.getLifecycle() == Lifecycle.EMPTY
                    && story.getScenarios().isEmpty()) {
                autoCompleteEntry(AutoCompleteEntry.InOrderTo, lastLine, results);
            }
            if (narrative.asA().isEmpty()
                    && story.getGivenStories() == GivenStories.EMPTY
                    && story.getLifecycle() == Lifecycle.EMPTY
                    && story.getScenarios().isEmpty()) {
                autoCompleteEntry(AutoCompleteEntry.AsA, lastLine, results);
            }
            if (narrative.iWantTo().isEmpty()
                    && story.getGivenStories() == GivenStories.EMPTY
                    && story.getLifecycle() == Lifecycle.EMPTY
                    && story.getScenarios().isEmpty()) {
                autoCompleteEntry(AutoCompleteEntry.IWantTo, lastLine, results);
            }
        }
    }

    private void autoCompleteForMeta(Story story, String lastLine, List<AutoCompleteDTO> results) {

        Meta meta = story.getMeta();
        if (meta == Meta.EMPTY) {
            if (story.getNarrative() == Narrative.EMPTY
                    && story.getGivenStories() == GivenStories.EMPTY
                    && story.getLifecycle() == Lifecycle.EMPTY
                    && story.getScenarios().isEmpty()) {
                autoCompleteEntry(AutoCompleteEntry.Meta, lastLine, results);
            }
        } else {
            if (story.getNarrative() == Narrative.EMPTY
                    && story.getGivenStories() == GivenStories.EMPTY
                    && story.getLifecycle() == Lifecycle.EMPTY
                    && story.getScenarios().isEmpty()) {
                // meta is not empty but we can perhaps autocomplete on individual meta entries
                Set<String> propertyNames = meta.getPropertyNames();
                if (!propertyNames.contains("skip")) {
                    autoCompleteEntry(AutoCompleteEntry.MetaSkip, lastLine, results);
                }
                if (!propertyNames.contains("ignored")) {
                    autoCompleteEntry(AutoCompleteEntry.MetaIgnored, lastLine, results);
                }
                if (!propertyNames.contains("author")) {
                    autoCompleteEntry(AutoCompleteEntry.MetaAuthor, lastLine, results);
                }
            }
        }
    }

    private void autoCompleteEntry(AutoCompleteEntry autoCompleteEntry, String lastLine, List<AutoCompleteDTO> results) {
        String autoComplete = autoCompleteEntry.autoComplete(lastLine);
        if (autoComplete != null) {
            AutoCompleteDTO entry = new AutoCompleteDTO(autoCompleteEntry.asString, autoComplete);
            results.add(entry);
        }
    }

    private String retrieveLastStep(String input) {
        return null;
    }
}
