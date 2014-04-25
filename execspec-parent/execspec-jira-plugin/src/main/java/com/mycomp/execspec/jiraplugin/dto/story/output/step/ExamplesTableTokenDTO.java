package com.mycomp.execspec.jiraplugin.dto.story.output.step;

import com.mycomp.execspec.jiraplugin.dto.story.output.ExamplesTableDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Dmytro on 4/1/2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ExamplesTableTokenDTO extends BaseToken {

    private ExamplesTableDTO examplesTableDTO;

    protected ExamplesTableTokenDTO() {

    }

    public ExamplesTableTokenDTO(String asString, ExamplesTableDTO examplesTableDTO) {
        this.kind = TokenKind.table;
        this.asString = asString;
        this.examplesTableDTO = examplesTableDTO;
    }

    public ExamplesTableDTO getExamplesTableDTO() {
        return examplesTableDTO;
    }
}
