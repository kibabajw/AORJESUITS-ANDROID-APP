package org.easternafricajesuits.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsReceivedModel {

    @SerializedName("newsitemlist")
    private List<NewsModel> newsModel;

    public List<NewsModel> getNewsModel() {
        return newsModel;
    }

    public void setNewsModel(List<NewsModel> newsModel) {
        this.newsModel = newsModel;
    }


}
