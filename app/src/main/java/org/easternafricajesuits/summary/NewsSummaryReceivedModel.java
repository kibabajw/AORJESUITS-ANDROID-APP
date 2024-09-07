package org.easternafricajesuits.summary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsSummaryReceivedModel {

    @SerializedName("newsitemlist")
    private List<NewsSummaryModel> newsModel;

    public List<NewsSummaryModel> getNewsModel() {
        return newsModel;
    }

    public void setNewsModel(List<NewsSummaryModel> newsModel) {
        this.newsModel = newsModel;
    }


}
