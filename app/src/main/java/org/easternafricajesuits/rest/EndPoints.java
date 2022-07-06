package org.easternafricajesuits.rest;

import org.easternafricajesuits.adusum.model.AdusumDocuments;
import org.easternafricajesuits.adusum.model.AdusumModel;
import org.easternafricajesuits.adusum.model.AdusumServiceModel;
import org.easternafricajesuits.adusum.model.Apostleship;
import org.easternafricajesuits.adusum.model.AskcodeModel;
import org.easternafricajesuits.adusum.model.AskcodeReceivedModel;
import org.easternafricajesuits.adusum.model.CatalogueModel;
import org.easternafricajesuits.adusum.model.ImageClass;
import org.easternafricajesuits.adusum.model.NewPasswordModel;
import org.easternafricajesuits.adusum.model.NewPasswordReceivedModel;
import org.easternafricajesuits.adusum.model.NewUserModel;
import org.easternafricajesuits.adusum.model.NewUserModelReceived;
import org.easternafricajesuits.adusum.model.RequestPassResetModel;
import org.easternafricajesuits.adusum.model.RequestPasswordResetReceived;
import org.easternafricajesuits.adusum.model.UpdatemeModel;
import org.easternafricajesuits.adusum.model.UserLoginModel;
import org.easternafricajesuits.adusum.model.UserLoginReceived;
import org.easternafricajesuits.models.EventsreceivedModel;
import org.easternafricajesuits.models.LikeModel;
import org.easternafricajesuits.models.NewsReceivedModel;
import org.easternafricajesuits.models.OrderdonateModel;
import org.easternafricajesuits.models.OrderdonateModelReceived;
import org.easternafricajesuits.models.ProductsReceivedModel;
import org.easternafricajesuits.models.Thoughts;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPoints {

    @POST("newbrother")
    Call<NewUserModelReceived> createAccount(@Body NewUserModel newUser);

    @GET("brother/{id}")
    Call<AdusumServiceModel> getBrother(@Path("id") String id);

    @POST("login")
    Call<UserLoginReceived> loginBrother(@Body UserLoginModel userLoginModel);

    @PUT("requestpasswordreset")
    Call<RequestPasswordResetReceived> requestpasswordReset(@Body RequestPassResetModel requestPassResetModel);

    @POST("askforresetcode")
    Call<AskcodeReceivedModel> askforrequestCode(@Body AskcodeModel askcodeModel);

    @PUT("newpassword")
    Call<NewPasswordReceivedModel> newPassword(@Body NewPasswordModel newPasswordModel);

    @GET("documents")
    Call<AdusumDocuments> getDocuments();

    @GET("api/news")
    Call<NewsReceivedModel> getNews(@Query("nextID") int nextID);

    @GET("events/{month}/{year}")
    Call<EventsreceivedModel> getEvents(@Path("month") String month, @Path("year") String year);

    @GET("apostleship/{month}/{year}")
    Call<Apostleship> getApostleship(@Path("month") String month, @Path("year") String year);

    @GET("like")
    Call<LikeModel> doLikeNews(@Query("newsid") String newsid, @Query("likeuserid") String likeuserid);

    @GET("isliked")
    Call<Object> isLiked(@Query("likeid") String likeid, @Query("likeuserid") String likeuserid);

    @GET("prayers")
    Call<Thoughts> getThoughts(@Query("month") String month, @Query("day") String day);

    @GET("/brother/{id}")
    Call<AdusumModel> adusumGetMyInfo(@Path("id") String id);

    @GET("catalogue")
    Call<CatalogueModel> adusumGetCatalogue();

    @Multipart
    @PUT("profilepicture")
    Call<ImageClass> uploadImageraw(
            @Part("userid") RequestBody userid,
            @Part MultipartBody.Part photo
    );

    @FormUrlEncoded
    @PUT("profilepicture")
    Call<ImageClass> uploadImage(@Field("userid") String userid, @Field("photo") String photo);

    @PUT("updateme")
    Call<UpdatemeModel> updateMe(@Body UpdatemeModel updatemeModel);

    @GET("products")
    Call<ProductsReceivedModel> getProducts();

    @POST("neworderordonation")
    Call<OrderdonateModelReceived> neworderordonation(@Body OrderdonateModel newOrderdonate);
}
