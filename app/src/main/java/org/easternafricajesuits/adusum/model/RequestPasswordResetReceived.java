package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class RequestPasswordResetReceived {

    @SerializedName("statusCode")
    private String statusCode;

    public RequestPasswordResetReceived(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
