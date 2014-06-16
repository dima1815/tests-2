package com.mycomp.execspec.jiraplugin.dto.story;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Dmytro on 6/2/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SoThatDTO {

    private String keyword;

    private String value;

    public SoThatDTO() {
    }

    public SoThatDTO(String keyword, String value) {
        this.keyword = keyword;
        this.value = value;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SoThatDTO that = (SoThatDTO) o;

        if (keyword != null ? !keyword.equals(that.keyword) : that.keyword != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keyword != null ? keyword.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SoThatDTO{" +
                "keyword='" + keyword + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
