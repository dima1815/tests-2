package com.mycomp.execspec.jiraplugin.rest;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.mycomp.execspec.jiraplugin.dto.story.output.*;
import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;
import com.mycomp.execspec.jiraplugin.service.StoryService;
import org.apache.commons.lang.Validate;
import org.jbehave.core.i18n.LocalizedKeywords;
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

        // TODO - decide what to do about the null parameters below?
        StoryDTO storyDTO = null;
//                new StoryDTO(
//                projectKey, issueKey, version, asString, null, storyReports,
//                null, null, null, null, null, null);
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

    @GET
    @AnonymousAllowed
    @Path("/newstorytemplate/{projectKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewStoryTemplate(@PathParam("projectKey") String projectKey) {

        // TODO - make this project specific, e.g. keywords maybe localized for some projects
        LocalizedKeywords keywords = new LocalizedKeywords();

        StoryDTO storyDTO = new StoryDTO();
        NarrativeDTO narrative = new NarrativeDTO(keywords.narrative());
        narrative.setInOrderTo(new InOrderToDTO(keywords.inOrderTo(), null));
        narrative.setAsA(new AsADTO(keywords.asA(), null));
        narrative.setiWantTo(new IWantToDTO(keywords.iWantTo(), null));
        narrative.setSoThat(new SoThatDTO(keywords.soThat(), null));
        storyDTO.setNarrative(narrative);
        storyDTO.setProjectKey(projectKey);

        return Response.ok(storyDTO, MediaType.APPLICATION_JSON).build();
    }


}
