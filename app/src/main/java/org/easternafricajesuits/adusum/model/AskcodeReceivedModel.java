package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class AskcodeReceivedModel {

    @SerializedName("resetStatusCode")
    private String resetStatusCode;

    public AskcodeReceivedModel(String resetStatusCode) {
        this.resetStatusCode = resetStatusCode;
    }

    public String getResetStatusCode() {
        return resetStatusCode;
    }
}
