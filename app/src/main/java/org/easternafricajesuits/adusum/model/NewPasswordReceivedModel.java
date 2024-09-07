package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class NewPasswordReceivedModel {

    @SerializedName("responseCode")
    private String responseCode;

    public NewPasswordReceivedModel(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return responseCode;
    }
}
