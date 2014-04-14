package com.mycomp.execspec.jiraplugin.dto.story.out;

import com.mycomp.execspec.jiraplugin.dto.story.out.step.StepDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 2/8/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioDTO {

    private String asString;
    private String title;
    private MetaDTO meta;
    private GivenStoriesDTO givenStories;
    private List<StepDTO> steps;
    private ExamplesTableDTO examplesTable;

    protected ScenarioDTO() {
        // used only by JaxB
    }

    public ScenarioDTO(String asString, String title, MetaDTO meta, GivenStoriesDTO givenStories, List<StepDTO> steps, ExamplesTableDTO examplesTable) {
        this.asString = asString;
        this.title = title;
        this.meta = meta;
        this.givenStories = givenStories;
        this.steps = steps;
        this.examplesTable = examplesTable;
    }

    public String asString() {
        return asString;
    }

    public String getTitle() {
        return title;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public GivenStoriesDTO getGivenStories() {
        return givenStories;
    }

    public List<StepDTO> getSteps() {
        return steps;
    }

    public ExamplesTableDTO getExamplesTable() {
        return examplesTable;
    }
}
