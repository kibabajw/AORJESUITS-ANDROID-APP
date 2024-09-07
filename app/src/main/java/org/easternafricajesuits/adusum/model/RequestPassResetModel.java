package org.easternafricajesuits.adusum.model;

public class RequestPassResetModel {

    private String usersentEmail;

    public RequestPassResetModel(String usersentEmail) {
        this.usersentEmail = usersentEmail;
    }

    public void setUsersentEmail(String usersentEmail) {
        this.usersentEmail = usersentEmail;
    }
}
