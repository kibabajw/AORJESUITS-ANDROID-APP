package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Array;

public class AdusumServiceModel {

    @SerializedName("fullname")
    private String ufullname;

    @SerializedName("username")
    private String uusername;

    @SerializedName("date_of_birth")
    private String udateofbirth;

    @SerializedName("date_of_entry")
    private String udateofentry;

    @SerializedName("name_of_provincial")
    private String unameofprovincial;

    @SerializedName("current_community")
    private String ucurrentcommunity;

    @SerializedName("brostatus")
    private String ubrostatus;

    @SerializedName("profile_picture")
    private String uprofilepicture;

    public AdusumServiceModel(String ufullname, String uusername, String udateofbirth, String udateofentry, String unameofprovincial, String ucurrentcommunity, String ubrostatus, String uprofilepicture) {
        this.ufullname = ufullname;
        this.uusername = uusername;
        this.udateofbirth = udateofbirth;
        this.udateofentry = udateofentry;
        this.unameofprovincial = unameofprovincial;
        this.ucurrentcommunity = ucurrentcommunity;
        this.ubrostatus = ubrostatus;
        this.uprofilepicture = uprofilepicture;
    }

    public String getUfullname() {
        return ufullname;
    }

    public String getUusername() {
        return uusername;
    }

    public String getUdateofbirth() {
        return udateofbirth;
    }

    public String getUdateofentry() {
        return udateofentry;
    }

    public String getUnameofprovincial() {
        return unameofprovincial;
    }

    public String getUcurrentcommunity() {
        return ucurrentcommunity;
    }

    public String getUbrostatus() {
        return ubrostatus;
    }

    public String getUprofilepicture() {
        return uprofilepicture;
    }
}
