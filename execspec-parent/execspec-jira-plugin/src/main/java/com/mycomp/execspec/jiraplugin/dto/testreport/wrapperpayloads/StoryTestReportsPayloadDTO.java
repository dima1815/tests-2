package com.mycomp.execspec.jiraplugin.dto.testreport.wrapperpayloads;

import com.mycomp.execspec.jiraplugin.dto.testreport.StoryReportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dmytro on 4/10/2014.
 */
@XmlRootElement(name = "stories_payload")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryTestReportsPayloadDTO {

    private List<StoryReportDTO> storyTestReports;

    protected StoryTestReportsPayloadDTO() {
    }

    public StoryTestReportsPayloadDTO(List<StoryReportDTO> storyTestReports) {
        this.storyTestReports = storyTestReports;
    }

    public List<StoryReportDTO> getStoryTestReports() {
        return storyTestReports;
    }
}
