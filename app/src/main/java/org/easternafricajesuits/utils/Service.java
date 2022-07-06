package org.easternafricajesuits.utils;

import org.easternafricajesuits.models.NewsReceivedModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

    @GET("api/news")
    Call<NewsReceivedModel> getNews();

}
