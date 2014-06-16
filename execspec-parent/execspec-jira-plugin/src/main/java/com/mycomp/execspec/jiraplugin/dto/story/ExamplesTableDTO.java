package com.mycomp.execspec.jiraplugin.dto.story;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Dmytro on 5/30/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExamplesTableDTO {

    private List<String> headers;

    private List<TableRowDTO> rows;

    public ExamplesTableDTO() {
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<TableRowDTO> getRows() {
        return rows;
    }

    public void setRows(List<TableRowDTO> rows) {
        this.rows = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamplesTableDTO that = (ExamplesTableDTO) o;

        if (headers != null ? !headers.equals(that.headers) : that.headers != null) return false;
        if (rows != null ? !rows.equals(that.rows) : that.rows != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = headers != null ? headers.hashCode() : 0;
        result = 31 * result + (rows != null ? rows.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExamplesTableDTO{" +
                "headers=" + headers +
                ", rows=" + rows +
                '}';
    }
}
