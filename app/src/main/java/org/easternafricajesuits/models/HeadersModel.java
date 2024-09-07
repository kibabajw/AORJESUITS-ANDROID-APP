package org.easternafricajesuits.models;

import com.google.gson.annotations.SerializedName;

public class HeadersModel {

    @SerializedName("id")
    private Integer item_id;
    @SerializedName("page_heading")
    private String pageheading;

    @SerializedName("page_title")
    private String pagetitle;

    @SerializedName("page_subtitle")
    private String pagesubtitle;

    @SerializedName("page_tag")
    private String pagetag;

    @SerializedName("page_image")
    private String pageimage;

    @SerializedName("page_updated_at")
    private String pageupdatedat;

    public HeadersModel(String pageheading, String pagetitle, String pagesubtitle, String pagetag, String pageimage, String pageupdatedat, Integer item_id) {
        this.pageheading = pageheading;
        this.pagetitle = pagetitle;
        this.pagesubtitle = pagesubtitle;
        this.pagetag = pagetag;
        this.pageimage = pageimage;
        this.pageupdatedat = pageupdatedat;
        this.item_id = item_id;
    }

    public String getPageheading() {
        return pageheading;
    }

    private void setPageheading(String pageheading) {
        this.pageheading = pageheading;
    }

    public String getPagetitle() {
        return pagetitle;
    }

    private void setPagetitle(String pagetitle) {
        this.pagetitle = pagetitle;
    }

    public String getPagesubtitle() {
        return pagesubtitle;
    }

    private void setPagesubtitle(String pagesubtitle) {
        this.pagesubtitle = pagesubtitle;
    }

    public String getPagetag() {
        return pagetag;
    }

    private void setPagetag(String pagetag) {
        this.pagetag = pagetag;
    }

    public String getPageimage() {
        return pageimage;
    }

    private void setPageimage(String pageimage) {
        this.pageimage = pageimage;
    }

    private void setPageupdatedat(String pageupdatedat) {
        this.pageupdatedat = pageupdatedat;
    }

    public String getPageupdatedat() {
        return pageupdatedat;
    }

    private void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getItem_id() {
        return item_id;
    }
}
