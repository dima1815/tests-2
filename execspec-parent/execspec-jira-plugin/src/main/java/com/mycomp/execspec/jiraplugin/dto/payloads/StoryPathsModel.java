package com.mycomp.execspec.jiraplugin.dto.payloads;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dmytro on 2/25/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StoryPathsModel {

    private List<String> paths;

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}
