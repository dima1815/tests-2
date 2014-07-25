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

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public StoryPathsDTO listStoryPaths(@PathParam("projectKey") String projectKey,
                                        @QueryParam("appendVersionToPath")
                                        @DefaultValue("true") boolean includeVersionInPath) {

        Validate.notEmpty(projectKey);

        List<StoryDTO> stories = storyService.findByProjectKey(projectKey);
        List<String> paths = new ArrayList<String>(stories.size());
        for (StoryDTO story : stories) {
            StringBuilder storyPathSb = new StringBuilder(story.getProjectKey() + "/" + story.getIssueKey());
            if (includeVersionInPath) {
                Long version = story.getVersion();
                Validate.notNull(version);
                storyPathSb.append("." + version);
            }
            storyPathSb.append(".story");
            String storyPath = storyPathSb.toString();
            paths.add(storyPath);
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
            @PathParam("storyPath") String storyPath,
            @QueryParam("versionInPath")
            @DefaultValue("false") boolean versionInPath) {

        String issueKey;
        if (versionInPath) {
            String regexPattern = "(.*)\\.([0-9]*)(\\.story)";
            Pattern p = Pattern.compile(regexPattern);
            Matcher matcher = p.matcher(storyPath);
            if (matcher.matches()) {
                String version = matcher.group(2);
                log.debug("version in issue key is - " + version);
                issueKey = matcher.group(1);
            } else {
                throw new IllegalArgumentException("If story version is part of story path then the path must match pattern - " + regexPattern);
            }
        } else {
            issueKey = storyPath.substring(0, storyPath.lastIndexOf(".story"));
        }

        Validate.notNull(storyPath);
        Validate.isTrue(storyPath.endsWith(".story"));

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
