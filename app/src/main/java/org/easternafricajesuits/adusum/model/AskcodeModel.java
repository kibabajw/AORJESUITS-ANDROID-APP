package org.easternafricajesuits.adusum.model;

public class AskcodeModel {

    private String requestCode;
    private String requestEmail;

    public AskcodeModel(String requestCode, String requestEmail) {
        this.requestCode = requestCode;
        this.requestEmail = requestEmail;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public void setRequestEmail(String requestEmail) {
        this.requestEmail = requestEmail;
    }
}
