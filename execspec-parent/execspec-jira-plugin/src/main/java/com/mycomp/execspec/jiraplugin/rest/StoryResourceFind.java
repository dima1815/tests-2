package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.mycomp.execspec.jiraplugin.dto.story.StoriesPayload;
import com.mycomp.execspec.jiraplugin.dto.story.StoryDTO;
import com.mycomp.execspec.jiraplugin.dto.story.StoryPathsDTO;
import com.mycomp.execspec.jiraplugin.service.StepDocsSerivce;
import com.mycomp.execspec.jiraplugin.service.StoryReportService;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
@Path("/find")
public class StoryResourceFind {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final StoryService storyService;
    private final StoryReportService storyReportService;
    private final StepDocsSerivce stepDocsSerivce;

    private SearchService searchService;
    private JiraAuthenticationContext authenticationContext;

    public StoryResourceFind(StoryService storyService,
                             StoryReportService storyReportService,
                             StepDocsSerivce stepDocsSerivce, SearchService searchService,
                             JiraAuthenticationContext authenticationContext) {
        this.storyService = storyService;
        this.storyReportService = storyReportService;
        this.stepDocsSerivce = stepDocsSerivce;
        this.searchService = searchService;
        this.authenticationContext = authenticationContext;
    }

    @GET
    @Path("/story-paths/{projectKey}")
    @Produces({MediaType.APPLICATION_JSON})
    public StoryPathsDTO listStoryPaths(@PathParam("projectKey") String projectKey) {

        Validate.notEmpty(projectKey);

        List<StoryDTO> stories = storyService.findByProjectKey(projectKey);
        List<String> paths = new ArrayList<String>(stories.size());
        for (StoryDTO story : stories) {
            paths.add(story.getProjectKey() + "/" + story.getIssueKey() + ".story");
        }
        StoryPathsDTO pathsModel = new StoryPathsDTO();
        pathsModel.setPaths(paths);

        return pathsModel;
    }

    @GET
    @Path("/for-project/{projectKey}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public StoriesPayload findForProject(@PathParam("projectKey") String projectKey) {
        List<StoryDTO> stories = storyService.findByProjectKey(projectKey);
        StoriesPayload payload = new StoriesPayload(stories);
        return payload;
    }

    @GET
    @Path("/for-issue/{projectKey}/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findForIssue(
            @PathParam("projectKey") String projectKey,
            @PathParam("issueKey") String issueKey) {

        StoryDTO storyDTO = storyService.findByProjectAndIssueKey(projectKey, issueKey);

        Response response;
        if (storyDTO != null) {
            response = Response.ok(storyDTO, MediaType.APPLICATION_JSON).build();
        } else {
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/for-path/{projectKey}/{storyPath}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findForPath(
            @PathParam("projectKey") String projectKey,
            @PathParam("storyPath") String storyPath) {

        Validate.notNull(storyPath);
        Validate.isTrue(storyPath.endsWith(".story"));
        String issueKey = storyPath.substring(0, storyPath.lastIndexOf(".story"));

        StoryDTO storyDTO = storyService.findByProjectAndIssueKey(projectKey, issueKey);

        Response response;
        if (storyDTO != null) {
            response = Response.ok(storyDTO, MediaType.APPLICATION_JSON).build();
        } else {
            response = Response.noContent().build();
        }
        return response;
    }
}
