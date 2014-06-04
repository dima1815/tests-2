package com.mycomp.execspec.jiraplugin.dto.story.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Dmytro on 5/28/2014.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NarrativeDTO {

    private String keyword;

    private InOrderToDTO inOrderTo;

    private AsADTO asA;

    private IWantToDTO iWantTo;

    private SoThatDTO soThat;

    public NarrativeDTO() {
    }

    public NarrativeDTO(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public InOrderToDTO getInOrderTo() {
        return inOrderTo;
    }

    public void setInOrderTo(InOrderToDTO inOrderTo) {
        this.inOrderTo = inOrderTo;
    }

    public AsADTO getAsA() {
        return asA;
    }

    public void setAsA(AsADTO asA) {
        this.asA = asA;
    }

    public IWantToDTO getiWantTo() {
        return iWantTo;
    }

    public void setiWantTo(IWantToDTO iWantTo) {
        this.iWantTo = iWantTo;
    }

    public SoThatDTO getSoThat() {
        return soThat;
    }

    public void setSoThat(SoThatDTO soThat) {
        this.soThat = soThat;
    }

    @Override
    public String toString() {
        return "NarrativeDTO{" +
                "keyword='" + keyword + '\'' +
                ", inOrderTo=" + inOrderTo +
                ", asA=" + asA +
                ", iWantTo=" + iWantTo +
                ", soThat=" + soThat +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NarrativeDTO that = (NarrativeDTO) o;

        if (asA != null ? !asA.equals(that.asA) : that.asA != null) return false;
        if (iWantTo != null ? !iWantTo.equals(that.iWantTo) : that.iWantTo != null) return false;
        if (inOrderTo != null ? !inOrderTo.equals(that.inOrderTo) : that.inOrderTo != null) return false;
        if (keyword != null ? !keyword.equals(that.keyword) : that.keyword != null) return false;
        if (soThat != null ? !soThat.equals(that.soThat) : that.soThat != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keyword != null ? keyword.hashCode() : 0;
        result = 31 * result + (inOrderTo != null ? inOrderTo.hashCode() : 0);
        result = 31 * result + (asA != null ? asA.hashCode() : 0);
        result = 31 * result + (iWantTo != null ? iWantTo.hashCode() : 0);
        result = 31 * result + (soThat != null ? soThat.hashCode() : 0);
        return result;
    }
}
