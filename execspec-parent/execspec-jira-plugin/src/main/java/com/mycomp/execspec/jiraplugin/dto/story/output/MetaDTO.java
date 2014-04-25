package com.mycomp.execspec.jiraplugin.dto.story.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaDTO {

    private Properties properties;

    public MetaDTO() {
    }

    public MetaDTO(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public String asString() {
        if (properties.isEmpty()) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder("Meta:\n");
            Set<String> propertyNames = properties.stringPropertyNames();
            for (String propertyName : propertyNames) {
                sb.append("@" + propertyName);
                Object value = properties.get(propertyName);
                if (value != null) {
                    sb.append(" " + value.toString());
                }
                sb.append("\n");
            }
            String asString = sb.toString();
            return asString;
        }
    }
}
