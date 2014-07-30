package com.mycomp.execspec;

import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocsPayload;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.jbehave.core.model.StepPattern;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.reporters.StepdocReporter;
import org.jbehave.core.steps.StepType;
import org.jbehave.core.steps.Stepdoc;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 2/26/14.
 */
public class JiraStepDocReporter implements StepdocReporter {

    private final String jiraBaseUrl;
    private final String jiraProject;

    private String createStepDocsPath = "rest/story-res/1.0/step-doc/add";

    private RegexPrefixCapturingPatternParser patternParser;

    public JiraStepDocReporter(String jiraBaseUrl, String jiraProject) {
        this.jiraBaseUrl = jiraBaseUrl;
        this.jiraProject = jiraProject;
        patternParser = new RegexPrefixCapturingPatternParser();
    }

    @Override
    public void stepdocs(List<Stepdoc> stepdocs, List<Object> stepsInstances) {

//        List<StepCandidate> stepCandidates = candidateSteps.get(0).listCandidates();
        System.out.println("stepdocs, stepdocs - " + stepdocs + ", stepsInstances - " + stepsInstances);

        List<StepDocDTO> stepDocDTOs = new ArrayList<StepDocDTO>(stepdocs.size());

        for (Stepdoc stepdoc : stepdocs) {

            String pattern = stepdoc.getPattern();
            StepType stepType = stepdoc.getStepType();
            String startingWord = stepdoc.getStartingWord();

            StepMatcher stepMatcher = patternParser.parseStep(stepType, pattern);
            StepPattern stepPattern = stepMatcher.pattern();
            String regExpPattern = stepPattern.resolved();
            StepDocDTO stepDocDTO = new StepDocDTO(stepType, startingWord, pattern, regExpPattern, null, null);
            stepDocDTOs.add(stepDocDTO);
        }

        StepDocsPayload stepDocsPayload = new StepDocsPayload(stepDocDTOs);
        sendStepDocs(stepDocsPayload);
    }

    private void sendStepDocs(StepDocsPayload stepDocsPayload) {

        String loginParams = "?os_username=admin&os_password=admin";
        String postUrl = jiraBaseUrl
                + "/" + createStepDocsPath + "/"
                + jiraProject
                + loginParams;

        Client client = Client.create();
        WebResource res = client.resource(postUrl);

        String response = res.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .post(String.class, stepDocsPayload);

        System.out.println("response - " + response);

    }

    @Override
    public void stepdocsMatching(String stepAsString, List<Stepdoc> matching, List<Object> stepsIntances) {
        System.out.println("stepdocsMatching, stepAsString - " + stepAsString + ", matching - " + matching
                + ", stepInstances - " + stepsIntances);
    }
}
