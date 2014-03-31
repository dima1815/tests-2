package com.mycomp.execspec.mavenplugin;

/**
 * TODO - add at least one line of java doc comment.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */


import com.mycomp.execspec.jiraplugin.dto.model.NarrativeModel;
import com.mycomp.execspec.jiraplugin.dto.model.ScenarioModel;
import com.mycomp.execspec.jiraplugin.dto.model.StoryModel;
import com.mycomp.execspec.jiraplugin.dto.payloads.StoriesPayload;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Says "Hi" to the user.
 */
@Mojo(name = DownloadStoriesFromJira.GOAL)
public class DownloadStoriesFromJira extends AbstractMojo {

    public static final String GOAL = "downloadstories";

    @Parameter(property = GOAL + ".jiraUrl", defaultValue = "http://localhost:2990/jira")
    private String jiraUrl;

    @Parameter(property = GOAL + ".restApiBasePath", defaultValue = "/rest/story-res/1.0")
    private String restApiBasePath;

    @Parameter(property = GOAL + ".listStoryPaths", defaultValue = "/story/list-story-paths")
    private String listStoryPaths;

    @Parameter(property = GOAL + ".jiraProject")
    private String jiraProject;

    @Parameter(property = GOAL + ".downloadedStoriesDir", defaultValue = "src/test/resources")
    private String downloadedStoriesDir;

    public void execute() throws MojoExecutionException {

        String resourcePath = jiraUrl + restApiBasePath + listStoryPaths + "/" + jiraProject;

        getLog().info("resourcePath = " + resourcePath);

        Client client = Client.create();
        WebResource res = client.resource(resourcePath);
        res.type(MediaType.APPLICATION_JSON);
//        res.type(MediaType.TEXT_PLAIN);
        ClientResponse response = res.get(ClientResponse.class);
        getLog().info("response - " + response);
        if (response.getStatus() == 200) {
            StoriesPayload storiesPayload = response.getEntity(StoriesPayload.class);
            List<StoryModel> stories = storiesPayload.getStories();
            getLog().info("stories.size() = " + stories.size());
            getLog().info("stories:\n" + stories);

            writeModelToFile(storiesPayload);

        } else {
            getLog().info("Response was ERROR");
        }

    }

    private void writeModelToFile(StoriesPayload storiesPayload) {

        List<StoryModel> stories = storiesPayload.getStories();

        for (StoryModel storyModel : stories) {

            PrintWriter pw = null;
            try {
                File storiesDir = new File(downloadedStoriesDir);
                storiesDir.mkdirs();
                String issueKey = storyModel.getIssueKey();
                String storyFileName = issueKey + ".story";
                File outFile = new File(storiesDir, storyFileName);
                FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
                pw = new PrintWriter(fw);

                NarrativeModel narrative = storyModel.getNarrative();
                String narrativeText = narrative.getAsString();
                pw.println(narrativeText);

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


    }

}
