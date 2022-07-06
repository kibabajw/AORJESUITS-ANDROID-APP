package org.easternafricajesuits.models;

import com.google.gson.annotations.SerializedName;

public class OrderdonateModelReceived {

    @SerializedName("message")
    private String message;

    public OrderdonateModelReceived(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
