package com.mycomp.execspec.jiraplugin.dto.story.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dmytro on 5/28/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioDTO {

    private String keyword;
    private String title;
    private MetaDTO meta;
    private GivenStoriesDTO givenStories;
    private ExamplesTableDTO examplesTable;
    private List<String> steps;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public void setMeta(MetaDTO meta) {
        this.meta = meta;
    }

    public GivenStoriesDTO getGivenStories() {
        return givenStories;
    }

    public void setGivenStories(GivenStoriesDTO givenStories) {
        this.givenStories = givenStories;
    }

    public ExamplesTableDTO getExamplesTable() {
        return examplesTable;
    }

    public void setExamplesTable(ExamplesTableDTO examplesTable) {
        this.examplesTable = examplesTable;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScenarioDTO that = (ScenarioDTO) o;

        if (examplesTable != null ? !examplesTable.equals(that.examplesTable) : that.examplesTable != null)
            return false;
        if (givenStories != null ? !givenStories.equals(that.givenStories) : that.givenStories != null) return false;
        if (keyword != null ? !keyword.equals(that.keyword) : that.keyword != null) return false;
        if (meta != null ? !meta.equals(that.meta) : that.meta != null) return false;
        if (steps != null ? !steps.equals(that.steps) : that.steps != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (meta != null ? meta.hashCode() : 0);
        result = 31 * result + (givenStories != null ? givenStories.hashCode() : 0);
        result = 31 * result + (examplesTable != null ? examplesTable.hashCode() : 0);
        result = 31 * result + (steps != null ? steps.hashCode() : 0);
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScenarioDTO{" +
                "title='" + title + '\'' +
                ", meta=" + meta +
                ", givenStories=" + givenStories +
                ", examplesTable=" + examplesTable +
                ", steps=" + steps +
                ", keyword='" + keyword + '\'' +
                '}';
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
