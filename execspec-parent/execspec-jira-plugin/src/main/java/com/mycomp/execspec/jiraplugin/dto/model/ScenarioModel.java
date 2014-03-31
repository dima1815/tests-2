package com.mycomp.execspec.jiraplugin.dto.model;

import com.mycomp.execspec.jiraplugin.dto.model.step.StepModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 2/8/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioModel {

    private final String asString;
    private final String title;
    private final MetaModel meta;
    private final GivenStoriesModel givenStories;
    private final List<StepModel> steps;
    private final ExamplesTableModel examplesTable;

    public ScenarioModel(String asString, String title, MetaModel meta, GivenStoriesModel givenStories, List<StepModel> steps, ExamplesTableModel examplesTable) {
        this.asString = asString;
        this.title = title;
        this.meta = meta;
        this.givenStories = givenStories;
        this.steps = steps;
        this.examplesTable = examplesTable;
    }

    public String getAsString() {
        return asString;
    }

    public String getTitle() {
        return title;
    }

    public MetaModel getMeta() {
        return meta;
    }

    public GivenStoriesModel getGivenStories() {
        return givenStories;
    }

    public List<StepModel> getSteps() {
        return steps;
    }

    public ExamplesTableModel getExamplesTable() {
        return examplesTable;
    }
}
