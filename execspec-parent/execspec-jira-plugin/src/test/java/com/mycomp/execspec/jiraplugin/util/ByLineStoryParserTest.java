package com.mycomp.execspec.jiraplugin.util;

import org.bitbucket.jbehaveforjira.javaclient.dto.JiraStory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Dmytro on 5/30/2014.
 */
public class ByLineStoryParserTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ByLineStoryParser parser = new ByLineStoryParser();

    @Test
    public void testStories() throws IOException {

//        File storiesDir = new File("src/test/resources/stories/dev");
//        logger.debug("testing story files in directory - " + storiesDir.getAbsolutePath());
//
//        Iterator<File> iterator = FileUtils.iterateFiles(storiesDir, new String[]{"story"}, true);
//
//        while (iterator.hasNext()) {
//            File storyFile = iterator.next();
//            String fileAsString = FileUtils.readFileToString(storyFile);
//
//            // trim up to the '===' separator
//            String[] tokens = fileAsString.split("===");
//            String storyAsString = tokens[0].trim();
//            StoryDTO storyDTO = parser.parseStory(storyAsString, storyFile.getName() + ".story");
//
//            String expStoryAsJson = tokens[1].trim();
//            ObjectMapper mapper = new ObjectMapper();
//            StoryDTO expStoryDTO = mapper.readValue(expStoryAsJson, StoryDTO.class);
//
//            // do compare
//            verifyEqual(storyDTO, expStoryDTO);
//        }

    }

    private void verifyEqual(JiraStory actualStoryDTO, JiraStory expStoryDTO) {

//        String actualDescription = actualStoryDTO.getDescription();
//        String expectedDesription = expStoryDTO.getDescription();
//        Assert.assertEquals("Story description did not match", expectedDesription, actualDescription);
//
//        MetaDTO actualMeta = actualStoryDTO.getMeta();
//        MetaDTO expectedMeta = expStoryDTO.getMeta();
//        Assert.assertEquals("Meta section was different than expected", expectedMeta, actualMeta);
//
//        NarrativeDTO actualNarrative = actualStoryDTO.getNarrative();
//        NarrativeDTO expectedNarrative = expStoryDTO.getNarrative();
//        Assert.assertEquals("Narrative section was different than expected", expectedNarrative, actualNarrative);
//
//        GivenStoriesDTO actualGivenStories = actualStoryDTO.getGivenStories();
//        GivenStoriesDTO expectedGivenStories = expStoryDTO.getGivenStories();
//        Assert.assertEquals("GivenStories section was different than expected", expectedGivenStories, actualGivenStories);
//
//        LifecycleDTO actualLifecycle = actualStoryDTO.getLifecycle();
//        LifecycleDTO expectedLifecycle = expStoryDTO.getLifecycle();
//        Assert.assertEquals("Lifecycle section was different than expected", expectedLifecycle, actualLifecycle);
//
//
//        List<ScenarioDTO> actualScenarioDTOs = actualStoryDTO.getScenarios();
//        List<ScenarioDTO> expectedScenarioDTOs = expStoryDTO.getScenarios();
//
//        if (actualScenarioDTOs == null) {
//            if (expectedScenarioDTOs != null) {
//                Assert.fail("Expected the following scenarios:\n" + expectedScenarioDTOs + "\nbut there were none");
//            }
//        } else {
//            if (expectedScenarioDTOs == null) {
//                Assert.fail("Expected no scenarios, but there were the following scenarios:\n" + actualScenarioDTOs);
//            } else {
//                Assert.assertEquals("Number of actual scenarios and expected scenarios did not match", actualScenarioDTOs.size(), expectedScenarioDTOs.size());
//                for (ScenarioDTO expectedScenarioDTO : expectedScenarioDTOs) {
//                    Assert.assertTrue("Actual scenarios did not contain the following expected scenario:\n"
//                            + expectedScenarioDTO + "\nActual scenarios were:\n" + actualScenarioDTOs, actualScenarioDTOs.contains(expectedScenarioDTO));
//                }
//            }
//        }

    }
}
