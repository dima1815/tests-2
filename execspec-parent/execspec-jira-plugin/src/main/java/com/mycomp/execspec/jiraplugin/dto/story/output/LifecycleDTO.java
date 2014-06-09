package com.mycomp.execspec.jiraplugin.dto.story.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 5/28/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LifecycleDTO {

    private String keyword;

    private List<String> beforeSteps = new ArrayList<String>();

    private List<String> afterSteps = new ArrayList<String>();

    public List<String> getBeforeSteps() {
        return beforeSteps;
    }

    public void setBeforeSteps(List<String> beforeSteps) {
        this.beforeSteps = beforeSteps;
    }

    public List<String> getAfterSteps() {
        return afterSteps;
    }

    public void setAfterSteps(List<String> afterSteps) {
        this.afterSteps = afterSteps;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "LifecycleDTO{" +
                "keyword='" + keyword + '\'' +
                ", beforeSteps=" + beforeSteps +
                ", afterSteps=" + afterSteps +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LifecycleDTO that = (LifecycleDTO) o;

        if (afterSteps != null ? !afterSteps.equals(that.afterSteps) : that.afterSteps != null) return false;
        if (beforeSteps != null ? !beforeSteps.equals(that.beforeSteps) : that.beforeSteps != null) return false;
        if (keyword != null ? !keyword.equals(that.keyword) : that.keyword != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keyword != null ? keyword.hashCode() : 0;
        result = 31 * result + (beforeSteps != null ? beforeSteps.hashCode() : 0);
        result = 31 * result + (afterSteps != null ? afterSteps.hashCode() : 0);
        return result;
    }
}
