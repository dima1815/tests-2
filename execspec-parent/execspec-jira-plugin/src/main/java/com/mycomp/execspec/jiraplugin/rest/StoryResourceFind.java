package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.mycomp.execspec.jiraplugin.dto.story.out.MetaDTO;
import com.mycomp.execspec.jiraplugin.dto.story.out.StoryDTO;
import com.mycomp.execspec.jiraplugin.dto.story.out.storypath.StoryPathsDTO;
import com.mycomp.execspec.jiraplugin.dto.story.out.wrapperpayloads.StoriesPayload;
import com.mycomp.execspec.jiraplugin.dto.story.out.wrapperpayloads.StoryPayload;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Contains rest api methods related to processing of Story objects.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */
@Path("/find")
public class StoryResourceFind {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final StoryService storyService;

    private SearchService searchService;
    private JiraAuthenticationContext authenticationContext;

    public StoryResourceFind(StoryService storyService, SearchService searchService, JiraAuthenticationContext authenticationContext) {
        this.storyService = storyService;
        this.searchService = searchService;
        this.authenticationContext = authenticationContext;
    }

    @GET
    @AnonymousAllowed
    @Path("/story-paths/{projectKey}")
    @Produces({MediaType.APPLICATION_JSON})
    public StoryPathsDTO listStoryPaths(@PathParam("projectKey") String projectKey) {

        Validate.notEmpty(projectKey);

        List<StoryDTO> stories = storyService.findByProjectKey(projectKey);
        List<String> paths = new ArrayList<String>(stories.size());
        for (StoryDTO story : stories) {
            paths.add(story.getPath());
        }
        StoryPathsDTO pathsModel = new StoryPathsDTO();
        pathsModel.setPaths(paths);

        return pathsModel;
    }

    @GET
    @AnonymousAllowed
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public StoriesPayload listAll() {
        List<StoryDTO> all = storyService.all();
        StoriesPayload payload = new StoriesPayload(all);
        return payload;
    }

    @GET
    @AnonymousAllowed
    @Path("/for-issue/{projectKey}/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public StoryPayload findForIssue(
            @PathParam("projectKey") String projectKey,
            @PathParam("issueKey") String issueKey,
            @DefaultValue("false") @QueryParam("asString") boolean asString) {

        StoryDTO byIssueKey = storyService.findByProjectAndIssueKey(projectKey, issueKey);
        StoryPayload payload = new StoryPayload(byIssueKey);
        return payload;
    }

    @GET
    @AnonymousAllowed
    @Path("/as-string/{projectKey}/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findAsStringForIssue(
            @PathParam("projectKey") String projectKey,
            @PathParam("issueKey") String issueKey) {

        StoryDTO story = storyService.findByProjectAndIssueKey(projectKey, issueKey);

        // append version as a field in Meta section so that clients can access it from a usual JBehave parsed Story object
        MetaDTO meta = story.getMeta();
        Properties properties = meta.getProperties();
        Long version = story.getVersion();
        properties.put("jira-version", version.toString());

        String asString = story.asString();
        return asString;
    }

}
