package org.easternafricajesuits.adusum.model;

public class NewUserModel {

    private String email;
    private String username;
    private String fullname;
    private String birth;
    private String entry;
    private String provincial;
    private String community;
    private String password;

    public NewUserModel(String email, String username, String fullname, String birth, String entry, String provincial, String community, String password) {
        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.birth = birth;
        this.entry = entry;
        this.provincial = provincial;
        this.community = community;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getProvincial() {
        return provincial;
    }

    public void setProvincial(String provincial) {
        this.provincial = provincial;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
