package org.easternafricajesuits.models;

public class OrderdonateModel {
    private String fullname;
    private String email;
    private String phonenumber;
    private String description;

    public OrderdonateModel(String fullname, String email, String phonenumber, String description) {
        this.fullname = fullname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.description = description;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
