package com.mycomp.execspec;

import com.mycomp.execspec.jiraplugin.dto.story.StoryDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.lang.Validate;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.model.Meta;
import org.jbehave.core.parsers.RegexStoryParser;
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
import java.util.Properties;

/**
 * Created by Dmytro on 2/25/14.
 */
public class JiraStoryLoader implements StoryLoader {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private String jiraBaseUrl;

    private String jiraProject;

    private String downloadedStoriesDir = "src/test/resources/jira_stories";

    //    private String loadStoryPath = "rest/story-res/1.0/find/as-string";
    private String loadStoryPath = "rest/story-res/1.0/find/for-path";

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
            StoryDTO storyDTO = response.getEntity(StoryDTO.class);
            Long version = storyDTO.getVersion();
            Validate.notNull(version);
            String receivedAsString = storyDTO.getAsString();

            RegexStoryParser parser = new RegexStoryParser();
            org.jbehave.core.model.Story jBehaveStory = parser.parseStory(receivedAsString, storyPath);

            // set jira-version property
            Meta originalMeta = jBehaveStory.getMeta();
            Properties properties = new Properties();
            properties.put("jira-version", version.toString());
            Meta overridenMeta = new Meta(properties);
            overridenMeta.inheritFrom(originalMeta);

//            org.jbehave.core.model.Story overridenJBehaveStory = new Story(
//                    jBehaveStory.getPath(), jBehaveStory.getDescription(), overridenMeta,
//                    jBehaveStory.getNarrative(), jBehaveStory.getGivenStories(),
//                    jBehaveStory.getLifecycle(), jBehaveStory.getScenarios());
//
//            String jiraVersion = overridenJBehaveStory.getMeta().getProperty("jira-version");
//            Validate.notEmpty(jiraVersion);
//
//            BytesListPrintStream printStream = new BytesListPrintStream();
//            Properties customPatterns = new Properties();
//            customPatterns.setProperty("beforeStory", "");
//            customPatterns.setProperty("pending", "{0}\n");
////            customPatterns.setProperty("examplesTableStart", "");
////            customPatterns.setProperty("examplesTableRowEnd", "");
//
//            TxtOutput txtOutput = new TxtOutput(printStream, customPatterns);
//            ReportingStoryWalker walker = new ReportingStoryWalker();
//            walker.walkStory(overridenJBehaveStory, txtOutput);
//            List<Byte> writtenBytes = printStream.getWrittenBytes();
//            String asString = StoryDTOUtils.bytesListToString(writtenBytes);
//
//            this.writeModelToFile(storyPath, asString);
            return storyDTO.getAsString();
        } else {
            int status = response.getStatus();
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException("Error occurred while trying to find Jira story paths. " +
                    "Response status was - " + status + ", status info - " + statusInfo);
        }

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
