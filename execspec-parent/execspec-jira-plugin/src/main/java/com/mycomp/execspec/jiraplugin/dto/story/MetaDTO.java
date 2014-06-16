package com.mycomp.execspec.jiraplugin.dto.story;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dmytro on 5/28/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaDTO {

    private String keyword;

    private List<MetaEntryDTO> properties;

    public List<MetaEntryDTO> getProperties() {
        return properties;
    }

    public void setProperties(List<MetaEntryDTO> properties) {
        this.properties = properties;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "MetaDTO{" +
                "keyword='" + keyword + '\'' +
                ", properties=" + properties +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaDTO metaDTO = (MetaDTO) o;

        if (keyword != null ? !keyword.equals(metaDTO.keyword) : metaDTO.keyword != null) return false;
        if (properties != null ? !properties.equals(metaDTO.properties) : metaDTO.properties != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keyword != null ? keyword.hashCode() : 0;
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }
}
