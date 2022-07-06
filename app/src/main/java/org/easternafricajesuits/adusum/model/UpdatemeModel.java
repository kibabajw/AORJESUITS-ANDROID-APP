package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class UpdatemeModel {

    private String updid;
    private String updusername;
    private String updpassword;

    @SerializedName("response")
    private String updresponse;

    public UpdatemeModel(String updid, String updusername, String updpassword) {
        this.updid = updid;
        this.updusername = updusername;
        this.updpassword = updpassword;
    }

    public String getUpdresponse() {
        return updresponse;
    }

}
