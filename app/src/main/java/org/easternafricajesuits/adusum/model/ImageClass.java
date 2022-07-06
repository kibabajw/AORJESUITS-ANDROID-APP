package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class ImageClass {

    @SerializedName("id")
    private String imageid;

    @SerializedName("photo")
    private String photo;

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }
}
