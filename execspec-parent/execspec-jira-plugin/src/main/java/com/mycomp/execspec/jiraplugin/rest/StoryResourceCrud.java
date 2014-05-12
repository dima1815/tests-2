package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains rest api methods related to processing of Story objects.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
@Path("/crud")
public class StoryResourceCrud {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final StoryService storyService;

    private SearchService searchService;
    private JiraAuthenticationContext authenticationContext;

    public StoryResourceCrud(StoryService storyService, SearchService searchService, JiraAuthenticationContext authenticationContext) {
        this.storyService = storyService;
        this.searchService = searchService;
        this.authenticationContext = authenticationContext;
    }

    @POST
    @AnonymousAllowed
    @Path("/save/{projectKey}/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public StoryDTO save(@PathParam("projectKey") String projectKey,
                         @PathParam("issueKey") String issueKey,
                         @QueryParam("version") String versionParam,
                         String asString) {

        Validate.notNull(projectKey);
        Validate.notNull(issueKey);
        Validate.notEmpty(asString, "story asString parameter was empty");

        Long version;
        if (versionParam != null && !versionParam.isEmpty()) {
            version = Long.parseLong(versionParam);
        } else {
            version = null;
        }
        List<StoryHtmlReportDTO> storyReports = new ArrayList<StoryHtmlReportDTO>();
        StoryDTO storyDTO = new StoryDTO(projectKey, issueKey, version, asString, null, storyReports);
        log.debug("saving story:\n" + storyDTO);

        StoryDTO savedStoryDTO = storyService.saveOrUpdate(storyDTO);
        return savedStoryDTO;
    }

    @DELETE
    @AnonymousAllowed
    @Path("/delete/{storyId}")
    public Response delete(@PathParam("storyId") Long storyId) {
        storyService.delete(storyId);
        return Response.ok("Successful deletion from server!").build();
    }

    @DELETE
    @AnonymousAllowed
    @Path("/delete/{projectKey}/{issueKey}")
    public Response delete(@PathParam("projectKey") String projectKey, @PathParam("issueKey") String issueKey) {
        Validate.notEmpty(projectKey);
        Validate.notEmpty(issueKey);
        storyService.delete(projectKey, issueKey);
        return Response.ok("Successful deletion from server!").build();
    }

}
