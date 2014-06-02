package com.mycomp.execspec.jiraplugin.dto.story.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Created by Dmytro on 5/28/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaDTO {

    private Map properties;

    public Map getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "MetaDTO{" +
                "properties=" + properties +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaDTO metaDTO = (MetaDTO) o;

        if (properties != null ? !properties.equals(metaDTO.properties) : metaDTO.properties != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return properties != null ? properties.hashCode() : 0;
    }
}
