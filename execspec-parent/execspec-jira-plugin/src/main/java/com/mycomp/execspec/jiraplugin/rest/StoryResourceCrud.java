package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.mycomp.execspec.jiraplugin.dto.story.input.SaveStoryDTO;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.apache.commons.lang.Validate;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

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
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SaveStoryDTO create(String storyModelString) {
        ObjectMapper mapper = new ObjectMapper();
        SaveStoryDTO storyModel = null;
        try {
            storyModel = mapper.readValue(storyModelString, SaveStoryDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.debug("adding storyModel: \n" + storyModel);
        Validate.notNull(storyModel);
        Validate.notNull(storyModel.getProjectKey());
        Validate.notNull(storyModel.getIssueKey());
        Validate.notEmpty(storyModel.getAsString(), "story asString parameter was empty");
        System.out.println("### in add method, storyModel - " + storyModel);
        storyService.create(storyModel);
        return storyModel;
    }

    @POST
    @AnonymousAllowed
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SaveStoryDTO update(SaveStoryDTO storyModel) {
        Validate.notNull(storyModel);
        Validate.notNull(storyModel.getIssueKey());
        System.out.println("### in update method, storyModel - " + storyModel);
        storyService.update(storyModel);
        return storyModel;
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
