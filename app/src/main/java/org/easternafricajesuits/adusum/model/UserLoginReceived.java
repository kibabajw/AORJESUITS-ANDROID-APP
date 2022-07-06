package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class UserLoginReceived {

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("username")
    private String username;

    @SerializedName("date_of_birth")
    private String dateofbirth;

    @SerializedName("date_of_entry")
    private String dateofentry;

    @SerializedName("name_of_provincial")
    private String nameofprovincial;

    @SerializedName("current_community")
    private String currentcommunity;

    @SerializedName("brotherLoginID")
    private String loginbrotherID;

    @SerializedName("brotherTOKEN")
    private String brotherTOKEN;

    @SerializedName("status")
    private String status;

    public UserLoginReceived(String fullname, String username, String dateofbirth, String dateofentry, String nameofprovincial, String currentcommunity, String loginbrotherID, String brotherTOKEN, String status) {
        this.fullname = fullname;
        this.username = username;
        this.dateofbirth = dateofbirth;
        this.dateofentry = dateofentry;
        this.nameofprovincial = nameofprovincial;
        this.currentcommunity = currentcommunity;
        this.loginbrotherID = loginbrotherID;
        this.brotherTOKEN = brotherTOKEN;
        this.status = status;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getDateofentry() {
        return dateofentry;
    }

    public String getNameofprovincial() {
        return nameofprovincial;
    }

    public String getCurrentcommunity() {
        return currentcommunity;
    }

    public String getLoginbrotherID() {
        return loginbrotherID;
    }

    public String getBrotherTOKEN() {
        return brotherTOKEN;
    }

    public String getStatus() {
        return status;
    }
}
