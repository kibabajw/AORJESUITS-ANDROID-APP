package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class Apostleship {

    @SerializedName("prayer_name")
    private String apostlename;
    @SerializedName("prayer_item")
    private String apostleitem;
    @SerializedName("prayer_month")
    private String apostlemonth;
    @SerializedName("prayer_year")
    private String apostleyear;
    @SerializedName("prayer_image")
    private String apostleimage;

    public Apostleship(String apostlename, String apostleitem, String apostlemonth, String apostleyear, String apostleimage) {
        this.apostlename = apostlename;
        this.apostleitem = apostleitem;
        this.apostlemonth = apostlemonth;
        this.apostleyear = apostleyear;
        this.apostleimage = apostleimage;
    }

    public String getApostlename() {
        return apostlename;
    }

    public void setApostlename(String apostlename) {
        this.apostlename = apostlename;
    }

    public String getApostleitem() {
        return apostleitem;
    }

    public void setApostleitem(String apostleitem) {
        this.apostleitem = apostleitem;
    }

    public String getApostlemonth() {
        return apostlemonth;
    }

    public void setApostlemonth(String apostlemonth) {
        this.apostlemonth = apostlemonth;
    }

    public String getApostleyear() {
        return apostleyear;
    }

    public void setApostleyear(String apostleyear) {
        this.apostleyear = apostleyear;
    }

    public String getApostleimage() {
        return apostleimage;
    }

    public void setApostleimage(String apostleimage) {
        this.apostleimage = apostleimage;
    }

}
