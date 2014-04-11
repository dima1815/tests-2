package com.mycomp.execspec;

import com.mycomp.execspec.jiraplugin.dto.story.out.storypath.StoryPathsDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 2/17/14.
 */
public class JiraStoryFinder {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public List<String> findPaths(String jiraBaseUrl, String projectKey, List<String> includes, List<String> excludes) {

        Validate.notEmpty(projectKey);

        List<String> paths = new ArrayList<String>();

        URI jiraSearchUrl = null;
        try {
            String fullPath = jiraBaseUrl + "/rest/story-res/1.0/find/story-paths/" + projectKey;
            fullPath += "?os_username=admin&os_password=admin";
            jiraSearchUrl = new URI(fullPath);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Client client = Client.create();
        WebResource res = client.resource(jiraSearchUrl);
        res.type(MediaType.APPLICATION_JSON);
//        res.type(MediaType.TEXT_PLAIN);
        ClientResponse response = res.get(ClientResponse.class);
        log.info("response - " + response);
        if (response.getStatus() == 200) {

            int length = response.getLength();
            MediaType type = response.getType();
            StoryPathsDTO storyPaths = response.getEntity(StoryPathsDTO.class);

            if (storyPaths.getPaths() != null && !storyPaths.getPaths().isEmpty()) {
                paths = storyPaths.getPaths();
            }

        } else {
            int status = response.getStatus();
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException("Error occurred while trying to find Jira story paths. " +
                    "Response status was - " + status + ", status info - " + statusInfo);
        }

        return paths;
    }

}
