package com.mycomp.execspec.jiraplugin.dto.story;

import com.mycomp.execspec.jiraplugin.dto.testreport.StoryHtmlReportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * TODO - add at least one line of java doc comment.
 *
 * @author stasyukd
 * @since 6.0.0-SNAPSHOT
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryWithReportsPayload {

    private StoryDTO story;

    private List<StoryHtmlReportDTO> testReports;


    public StoryWithReportsPayload() {
    }

    public StoryWithReportsPayload(StoryDTO story, List<StoryHtmlReportDTO> testReports) {
        this.story = story;
        this.testReports = testReports;
    }

    public StoryDTO getStory() {
        return story;
    }

    public void setStory(StoryDTO story) {
        this.story = story;
    }

    public List<StoryHtmlReportDTO> getTestReports() {
        return testReports;
    }

    public void setTestReports(List<StoryHtmlReportDTO> testReports) {
        this.testReports = testReports;
    }

}
