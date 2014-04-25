package com.mycomp.execspec;

import com.mycomp.execspec.jiraplugin.dto.story.output.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.jbehave.core.io.StoryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Dmytro on 2/25/14.
 */
public class JiraStoryLoader implements StoryLoader {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private String jiraBaseUrl;

    private String jiraProject;

    private String loadStoryPath = "rest/story-res/1.0/find/as-string";
//    private String loadStoryPath = "/rest/story-res/1.0/find/for-issue";

    private String downloadedStoriesDir = "src/test/resources/jira_stories";

    @Override
    public String loadStoryAsText(String storyPath) {

        URI jiraSearchUrl = null;
        try {
            String fullPath = jiraBaseUrl + "/" + loadStoryPath + "/" + jiraProject + "/" + storyPath;
            fullPath += "?os_username=admin&os_password=admin";
            log.debug("full story path is - " + fullPath);
            jiraSearchUrl = new URI(fullPath);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Client client = Client.create();
        WebResource res = client.resource(jiraSearchUrl);
        ClientResponse response = res.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        log.info("response - " + response);

        if (response.getStatus() == 200) {
            String storyPayload = response.getEntity(String.class);
            this.writeModelToFile(storyPath + ".story", storyPayload);
            return storyPayload;
        } else {
            int status = response.getStatus();
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException("Error occurred while trying to find Jira story paths. " +
                    "Response status was - " + status + ", status info - " + statusInfo);
        }

    }

    private String asString(StoryPayload storyPayload) {

        StoryDTO story = storyPayload.getStory();

        StringBuilder sb = new StringBuilder();

        // narrative
        NarrativeDTO narrative = story.getNarrative();
        String narrativeStr = narrative.getAsString();
        sb.append(narrativeStr);
        sb.append("\n\n");

        // meta
        sb.append("Meta:\n");
        MetaDTO storyMeta = story.getMeta();
        Properties storyMetaProp = storyMeta.getProperties();
        for (Object o : storyMetaProp.keySet()) {
            String key = (String) o;
            String value = (String) storyMetaProp.get(key);
            sb.append("@" + key + " " + value);
            sb.append("\n");
        }
        sb.append("\n");

        // scenarios
        List<ScenarioDTO> scenarios = story.getScenarios();
        for (ScenarioDTO scenario : scenarios) {
            sb.append(scenario.asString());
            sb.append("\n");
            sb.append("\n");
        }

        String asString = sb.toString();
        return asString;
    }

    private void writeModelToFile(String storyPath, String storyModel) {

        PrintWriter pw = null;
        try {
            File storiesDir = new File(downloadedStoriesDir);
            File outFile = new File(storiesDir, storyPath);
            outFile.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
            pw = new PrintWriter(fw);

            // we need to insert jira story version as a meta tag into the story
            // the meta section may exist - in this case we add an extra attribute
            // if meta section doesn't exist - we create it and create one attribute containing our version


            pw.print(storyModel);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }

    }

    public String getJiraBaseUrl() {
        return jiraBaseUrl;
    }

    public void setJiraBaseUrl(String jiraBaseUrl) {
        this.jiraBaseUrl = jiraBaseUrl;
    }

    public void setJiraProject(String jiraProject) {
        this.jiraProject = jiraProject;
    }

    public String getJiraProject() {
        return jiraProject;
    }
}
