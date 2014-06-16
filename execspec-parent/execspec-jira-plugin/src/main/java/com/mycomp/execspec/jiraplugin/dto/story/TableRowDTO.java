package com.mycomp.execspec.jiraplugin.dto.story;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dmytro on 6/13/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TableRowDTO {

    private List<String> values;

    public TableRowDTO() {
    }

    public TableRowDTO(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "TableRow{" +
                "values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableRowDTO tableRow = (TableRowDTO) o;

        if (values != null ? !values.equals(tableRow.values) : tableRow.values != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return values != null ? values.hashCode() : 0;
    }
}
