package com.mycomp.execspec.jiraplugin.dto.story.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Dmytro on 3/6/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GivenStoriesDTO {

    private List<GivenStoryDTO> givenStories;

    public GivenStoriesDTO() {
    }

    public GivenStoriesDTO(List<GivenStoryDTO> givenStories) {
        this.givenStories = givenStories;
    }

    public String asString() {

        if (givenStories.isEmpty()) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder("GivenStories: ");

            String fixedAlignmentPrefix = "              ";
            for (GivenStoryDTO givenStory : givenStories) {
                String path = givenStory.getPath();
                sb.append(path);
                sb.append("\n");
                sb.append(fixedAlignmentPrefix);
            }
            sb.append("\n");

            String asString = sb.toString();
            return asString;
        }
    }

    public List<GivenStoryDTO> getGivenStories() {
        return givenStories;
    }
}
