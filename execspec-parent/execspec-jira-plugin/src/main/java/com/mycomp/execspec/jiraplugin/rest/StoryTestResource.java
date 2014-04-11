package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryReportDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.wrapperpayloads.StoryTestReportsPayloadDTO;
import com.mycomp.execspec.jiraplugin.service.StoryReportService;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
            String setPayloadString) {

        ObjectMapper mapper = new ObjectMapper();
        StoryReportDTO storyReportDTO = null;
        try {
            storyReportDTO = mapper.readValue(setPayloadString, StoryReportDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        storyReportService.addStoryTestReport(projectKey, issueKey, storyReportDTO);
        return "success";
    }


    @GET
    @AnonymousAllowed
    @Path("/find/{projectKey}/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public StoryTestReportsPayloadDTO findStoryTestReport(
            @PathParam("projectKey") String projectKey,
            @PathParam("issueKey") String issueKey) {

        List<StoryReportDTO> storyTestReports = storyReportService.findStoryTestReports(projectKey, issueKey);

        StoryTestReportsPayloadDTO storyTestReportsPayloadDTO = new StoryTestReportsPayloadDTO(storyTestReports);
        return storyTestReportsPayloadDTO;
    }

    @DELETE
    @AnonymousAllowed
    @Path("/delete/{projectKey}/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteStoryTestReport(
            @PathParam("projectKey") String projectKey,
            @PathParam("issueKey") String issueKey) {

        storyReportService.deleteForIssue(projectKey, issueKey);
        return "success";
    }
}
