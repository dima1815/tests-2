package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.mycomp.execspec.jiraplugin.dto.storyreport.StoryTestReportsPayload;
import com.mycomp.execspec.jiraplugin.service.StoryReportService;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.apache.commons.lang.Validate;
import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStoryHtml;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * Contains rest api methods related to processing of Story objects.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
@Path("/story-test")
public class StoryTestResource {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final StoryService storyService;

    private final StoryReportService storyReportService;

    private SearchService searchService;
    private JiraAuthenticationContext authenticationContext;

    public StoryTestResource(StoryService storyService,
                             StoryReportService storyReportService,
                             SearchService searchService,
                             JiraAuthenticationContext authenticationContext) {
        this.storyService = storyService;
        this.storyReportService = storyReportService;
        this.searchService = searchService;
        this.authenticationContext = authenticationContext;
    }

    @POST
    @AnonymousAllowed
    @Path("/add/{projectKey}/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addStoryTestReport(
            @PathParam("projectKey") String projectKey,
            @PathParam("issueKey") String issueKey,
            String payload) {

        ObjectMapper mapper = new ObjectMapper();
        JiraStoryHtml storyReportDTO = null;
        try {
            storyReportDTO = mapper.readValue(payload, JiraStoryHtml.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        storyReportService.addStoryTestReport(projectKey, issueKey, storyReportDTO);
        return "success";
    }

    @POST
    @AnonymousAllowed
    @Path("/add-for-path/{projectKey}/{storyPath}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addStoryTestReportForPath(
            @PathParam("projectKey") String projectKey,
            @PathParam("storyPath") String storyPath,
            String payload) {

        Validate.notNull(storyPath);
        Validate.isTrue(storyPath.endsWith(".story"));
        String issueKey = storyPath.substring(0, storyPath.lastIndexOf(".story"));

        return this.addStoryTestReport(projectKey, issueKey, payload);
    }



    @GET
    @AnonymousAllowed
    @Path("/find/{projectKey}/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public StoryTestReportsPayload findStoryTestReport(
            @PathParam("projectKey") String projectKey,
            @PathParam("issueKey") String issueKey) {

        List<JiraStoryHtml> storyTestReports = storyReportService.findStoryReports(projectKey, issueKey);

        StoryTestReportsPayload storyTestReportsPayloadDTO = new StoryTestReportsPayload(storyTestReports);
        return storyTestReportsPayloadDTO;
    }

    @DELETE
    @AnonymousAllowed
    @Path("/delete/{projectKey}/{issueKey}")
    public Response deleteStoryTestReport(
            @PathParam("projectKey") String projectKey,
            @PathParam("issueKey") String issueKey) {

        storyReportService.deleteForIssue(projectKey, issueKey);
        return Response.ok("Successful deletion from server!").build();
    }
}
