package org.easternafricajesuits.summary;

import com.google.gson.annotations.SerializedName;

public class PopesPrayerModel {

    @SerializedName("prayer_name")
    private String prayer_name;
    @SerializedName("prayer_item")
    private String prayer_item;
    @SerializedName("prayer_month")
    private String prayer_month;
    @SerializedName("prayer_year")
    private String prayer_year;
    @SerializedName("prayer_image")
    private String prayer_image;

    public PopesPrayerModel(String prayer_name, String prayer_item, String prayer_month, String prayer_year, String prayer_image) {
        this.prayer_name = prayer_name;
        this.prayer_item = prayer_item;
        this.prayer_month = prayer_month;
        this.prayer_year = prayer_year;
        this.prayer_image = prayer_image;
    }

    public String getPrayer_name() {
        return prayer_name;
    }

    public void setPrayer_name(String prayer_name) {
        this.prayer_name = prayer_name;
    }

    public String getPrayer_item() {
        return prayer_item;
    }

    public void setPrayer_item(String prayer_item) {
        this.prayer_item = prayer_item;
    }

    public String getPrayer_month() {
        return prayer_month;
    }

    public void setPrayer_month(String prayer_month) {
        this.prayer_month = prayer_month;
    }

    public String getPrayer_year() {
        return prayer_year;
    }

    public void setPrayer_year(String prayer_year) {
        this.prayer_year = prayer_year;
    }

    public String getPrayer_image() {
        return prayer_image;
    }

    public void setPrayer_image(String prayer_image) {
        this.prayer_image = prayer_image;
    }

}
