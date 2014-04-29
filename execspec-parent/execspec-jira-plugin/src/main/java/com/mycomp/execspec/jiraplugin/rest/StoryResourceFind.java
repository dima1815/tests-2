package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.mycomp.execspec.jiraplugin.dto.stepdoc.StepDocDTO;
import com.mycomp.execspec.jiraplugin.dto.story.output.StoriesPayload;
import com.mycomp.execspec.jiraplugin.dto.story.output.StoryDTO;
import com.mycomp.execspec.jiraplugin.dto.story.output.StoryPathsDTO;
import com.mycomp.execspec.jiraplugin.dto.story.output.StoryPayload;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
            @PathParam("issueKey") String issueKey) {

        List<StepDocDTO> stepDocs = stepDocsSerivce.findForProject(projectKey);
        StoryDTO story = storyService.findByProjectAndIssueKey(projectKey, issueKey, stepDocs);

        List<StoryHtmlReportDTO> storyReports = storyReportService.findStoryReports(projectKey, issueKey);

        // sort storyReports by environment alphabetically
        Collections.sort(storyReports, new Comparator<StoryHtmlReportDTO>() {
            @Override
            public int compare(StoryHtmlReportDTO o1, StoryHtmlReportDTO o2) {
                return o1.getEnvironment().compareTo(o2.getEnvironment());
            }
        });

        StoryPayload payload = new StoryPayload(story, storyReports);
        return payload;
    }

    @GET
    @AnonymousAllowed
    @Path("/as-string/{projectKey}/{issueKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findAsStringForIssue(
            @PathParam("projectKey") String projectKey,
            @PathParam("issueKey") String issueKey) {

        List<StepDocDTO> stepDocs = stepDocsSerivce.findForProject(projectKey);
        StoryDTO story = storyService.findByProjectAndIssueKey(projectKey, issueKey, stepDocs);

        // append version as a field in Meta section so that clients can access it from a usual JBehave parsed Story object
//        MetaDTO meta = story.getMeta();
//        Properties properties = meta.getProperties();
//        Long version = story.getVersion();
//        properties.put("jira-version", version.toString());

        String asString = story.getAsString();
        return asString;
    }

}
