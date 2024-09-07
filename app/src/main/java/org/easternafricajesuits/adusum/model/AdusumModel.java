package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class AdusumModel {

    @SerializedName("fullname")
     private String fullname;

    @SerializedName("username")
     private String username;

    @SerializedName("email_address")
    private String email_address;

    @SerializedName("date_of_birth")
     private String date_of_birth;

    @SerializedName("date_of_entry")
     private String date_of_entry;

    @SerializedName("name_of_provincial")
     private String name_of_provincial;

    @SerializedName("current_community")
     private String current_community;

    @SerializedName("profile_picture")
    private String profile_picture;

    public AdusumModel(String fullname, String username, String email_address, String date_of_birth, String date_of_entry, String name_of_provincial, String current_community, String profile_picture) {
        this.fullname = fullname;
        this.username = username;
        this.email_address = email_address;
        this.date_of_birth = date_of_birth;
        this.date_of_entry = date_of_entry;
        this.name_of_provincial = name_of_provincial;
        this.current_community = current_community;
        this.profile_picture = profile_picture;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDate_of_entry() {
        return date_of_entry;
    }

    public void setDate_of_entry(String date_of_entry) {
        this.date_of_entry = date_of_entry;
    }

    public String getName_of_provincial() {
        return name_of_provincial;
    }

    public void setName_of_provincial(String name_of_provincial) {
        this.name_of_provincial = name_of_provincial;
    }

    public String getCurrent_community() {
        return current_community;
    }

    public void setCurrent_community(String current_community) {
        this.current_community = current_community;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
