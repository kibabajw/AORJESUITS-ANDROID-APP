package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class NewUserModelReceived {

    @SerializedName("data")
    private Object brotherObj;

    @SerializedName("message")
    private String message;

    public NewUserModelReceived(Object brotherObj, String message) {
        this.brotherObj = brotherObj;
        this.message = message;
    }

    public void setBrotherObj(Object brotherObj) {
        this.brotherObj = brotherObj;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBrotherObj() {
        return brotherObj;
    }

    public String getMessage() {
        return message;
    }
}
