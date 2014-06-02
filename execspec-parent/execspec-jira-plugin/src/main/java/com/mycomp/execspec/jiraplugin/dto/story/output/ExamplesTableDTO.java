package com.mycomp.execspec.jiraplugin.dto.story.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

/**
 * Created by Dmytro on 5/30/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExamplesTableDTO {

    private List<String> headers;
    private List<Map<String, String>> data;

    public List<String> getHeaders() {
        return headers;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamplesTableDTO that = (ExamplesTableDTO) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (headers != null ? !headers.equals(that.headers) : that.headers != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = headers != null ? headers.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExamplesTableDTO{" +
                "headers=" + headers +
                ", data=" + data +
                '}';
    }
}
