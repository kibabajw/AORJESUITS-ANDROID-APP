package org.easternafricajesuits.models;

import com.google.gson.annotations.SerializedName;

public class NewsModel {

    @SerializedName("id")
    private String newsId;
    @SerializedName("title")
    private String newsTitle;
    @SerializedName("image")
    private String newsImage;
    @SerializedName("body")
    private String newsBody;
    @SerializedName("likes")
    private String newsLikes;
    @SerializedName("author")
    private String author;
    @SerializedName("added_on")
    private String newsCreatedAt;

    public NewsModel() {

    }

    public NewsModel(String newsId, String newsTitle, String newsImage, String newsBody, String author, String newsCreatedAt) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsImage = newsImage;
        this.newsBody = newsBody;
        this.author = author;
        this.newsCreatedAt = newsCreatedAt;
    }


    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }

    public String getNewsLikes() {
        return newsLikes;
    }

    public void setNewsLikes(String newsLikes) {
        this.newsLikes = newsLikes;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return author;
    }

    public String getNewsCreatedAt() {
        return newsCreatedAt;
    }

    public void setNewsCreatedAt(String newsCreatedAt) {
        this.newsCreatedAt = newsCreatedAt;
    }




}
