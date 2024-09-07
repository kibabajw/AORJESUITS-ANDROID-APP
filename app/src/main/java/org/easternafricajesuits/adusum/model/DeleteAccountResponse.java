package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class DeleteAccountResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public DeleteAccountResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
