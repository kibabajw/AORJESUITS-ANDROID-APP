package org.easternafricajesuits.adusum.shukran;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShukranModel {

    @SerializedName("shukrans")
    private List<ShukranModel> items;

    @SerializedName("shukran_name")
    private String shukran_name;

    @SerializedName("shukran_year")
    private String shukran_year;

    @SerializedName("shukran_file")
    private String shukran_file;

    public ShukranModel(List<ShukranModel> items, String shukran_name, String shukran_year, String shukran_file) {
        this.items = items;
        this.shukran_name = shukran_name;
        this.shukran_year = shukran_year;
        this.shukran_file = shukran_file;
    }

    public List<ShukranModel> getItems() {
        return items;
    }

    public void setItems(List<ShukranModel> items) {
        this.items = items;
    }

    public String getShukran_name() {
        return shukran_name;
    }

    public void setShukran_name(String shukran_name) {
        this.shukran_name = shukran_name;
    }

    public String getShukran_year() {
        return shukran_year;
    }

    public void setShukran_year(String shukran_year) {
        this.shukran_year = shukran_year;
    }

    public String getShukran_file() {
        return shukran_file;
    }

    public void setShukran_file(String shukran_file) {
        this.shukran_file = shukran_file;
    }
}
