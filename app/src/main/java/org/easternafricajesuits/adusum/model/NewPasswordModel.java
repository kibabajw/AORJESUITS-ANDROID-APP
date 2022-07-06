package org.easternafricajesuits.adusum.model;

public class NewPasswordModel {

    private String touseemail;
    private String tousecode;
    private String touseNewpassword;

    public NewPasswordModel(String touseemail, String tousecode, String touseNewpassword) {
        this.touseemail = touseemail;
        this.tousecode = tousecode;
        this.touseNewpassword = touseNewpassword;
    }

    public void setTouseemail(String touseemail) {
        this.touseemail = touseemail;
    }

    public void setTousecode(String tousecode) {
        this.tousecode = tousecode;
    }

    public void setTouseNewpassword(String touseNewpassword) {
        this.touseNewpassword = touseNewpassword;
    }
}
