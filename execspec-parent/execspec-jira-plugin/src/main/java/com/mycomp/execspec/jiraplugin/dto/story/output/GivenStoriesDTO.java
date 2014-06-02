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
public class GivenStoriesDTO {

    private List<String> paths;

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GivenStoriesDTO that = (GivenStoriesDTO) o;

        if (paths != null ? !paths.equals(that.paths) : that.paths != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return paths != null ? paths.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GivenStoriesDTO{" +
                "paths=" + paths +
                '}';
    }
}
