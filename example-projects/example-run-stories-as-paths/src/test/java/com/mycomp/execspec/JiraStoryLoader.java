package com.mycomp.execspec;

import com.mycomp.execspec.jiraplugin.dto.ModelUtils;
import com.mycomp.execspec.jiraplugin.dto.model.NarrativeModel;
import com.mycomp.execspec.jiraplugin.dto.model.ScenarioModel;
import com.mycomp.execspec.jiraplugin.dto.model.StoryModel;
import com.mycomp.execspec.jiraplugin.dto.payloads.StoryPayload;
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

/**
 * Created by Dmytro on 2/25/14.
 */
public class JiraStoryLoader implements StoryLoader {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private String jiraBaseUrl;

    private String downloadedStoriesDir = "src/test/resources/jira_stories";

    @Override
    public String loadStoryAsText(String storyPath) {

        URI jiraSearchUrl = null;
        try {
            String fullPath = jiraBaseUrl + "/" + storyPath;
            fullPath += "?os_username=admin&os_password=admin";
            log.debug("full story path is - " + fullPath);
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
            StoryPayload storyPayload = response.getEntity(StoryPayload.class);

            StoryModel storyModel = storyPayload.getStory();
            if (storyModel != null) {
                this.writeModelToFile(storyModel);
            } else {
                log.warn("Failed to load story using path - " + storyPath);
            }

            String storyAsString = ModelUtils.asTextStory(storyModel);
            return storyAsString;

        } else {
            int status = response.getStatus();
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException("Error occurred while trying to find Jira story paths. " +
                    "Response status was - " + status + ", status info - " + statusInfo);
        }

    }

    private void writeModelToFile(StoryModel storyModel) {

        PrintWriter pw = null;
        try {
            File storiesDir = new File(downloadedStoriesDir);
            storiesDir.mkdirs();
            String issueKey = storyModel.getIssueKey();
            String storyFileName = issueKey + ".story";
            File outFile = new File(storiesDir, storyFileName);
            FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
            pw = new PrintWriter(fw);

            // narrative
            NarrativeModel narrative = storyModel.getNarrative();
            String narrativeText = narrative.getAsString();
            pw.println(narrativeText);

            // scenarios
            pw.println();
            List<ScenarioModel> scenarios = storyModel.getScenarios();
            for (ScenarioModel scenario : scenarios) {
                String scenarioText = scenario.getAsString();
                pw.println(scenarioText);
                pw.println();
            }

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
}
