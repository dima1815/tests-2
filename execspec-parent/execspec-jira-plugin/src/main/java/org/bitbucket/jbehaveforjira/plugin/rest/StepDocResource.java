package org.bitbucket.jbehaveforjira.plugin.rest;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import org.bitbucket.jbehaveforjira.plugin.dto.stepdoc.StepDocDTO;
import org.bitbucket.jbehaveforjira.plugin.dto.stepdoc.StepDocsPayload;
import org.bitbucket.jbehaveforjira.plugin.service.StepDocsSerivce;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Contains rest api methods related to processing of step docs.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
@Path("/step-doc")
public class StepDocResource {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private StepDocsSerivce stepDocsSerivce;
    private SearchService searchService;
    private JiraAuthenticationContext authenticationContext;

    public StepDocResource(StepDocsSerivce stepDocsSerivce, SearchService searchService,
                           JiraAuthenticationContext authenticationContext) {
        this.stepDocsSerivce = stepDocsSerivce;
        this.searchService = searchService;
        this.authenticationContext = authenticationContext;
    }

    @POST
    @AnonymousAllowed
    @Path("/add/{projectKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addStepDocs(
            @PathParam("projectKey") String projectKey,
            String payload) {

        ObjectMapper mapper = new ObjectMapper();
        StepDocsPayload stepDocsPayload = null;
        try {
            stepDocsPayload = mapper.readValue(payload, StepDocsPayload.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<StepDocDTO> stepDocs = stepDocsPayload.getStepDocs();
        stepDocsSerivce.createStepDocs(projectKey, stepDocs);
        return "success";
    }

    @GET
    @AnonymousAllowed
    @Path("/for-project/{projectKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public StepDocsPayload findForProject(@PathParam("projectKey") String projectKey) {

        List<StepDocDTO> stepDocs = stepDocsSerivce.findForProject(projectKey);
        StepDocsPayload payload = new StepDocsPayload(stepDocs);
        return payload;
    }


}
