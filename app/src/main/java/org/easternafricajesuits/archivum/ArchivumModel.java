package org.easternafricajesuits.archivum;

import com.google.gson.annotations.SerializedName;

public class ArchivumModel {

    private Integer id;
    private String title;
    private String body;
    private String picture;
    private String video;
    private String created_at;

    public ArchivumModel(Integer id, String title, String body, String picture, String video, String created_at) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.picture = picture;
        this.video = video;
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
