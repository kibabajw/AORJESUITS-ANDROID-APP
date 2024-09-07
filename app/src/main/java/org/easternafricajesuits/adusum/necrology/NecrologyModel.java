package org.easternafricajesuits.adusum.necrology;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NecrologyModel {

    @SerializedName("rows")
    private List<NecrologyModel> items;

    @SerializedName("name")
    String doc_name;

    @SerializedName("month")
    String doc_month;

    @SerializedName("year")
    private String doc_year;
    @SerializedName("file")
    private String doc_file;

    public NecrologyModel(List<NecrologyModel> items, String doc_name, String doc_month, String doc_year, String doc_file) {
        this.items = items;
        this.doc_name = doc_name;
        this.doc_month = doc_month;
        this.doc_year = doc_year;
        this.doc_file = doc_file;
    }

    public List<NecrologyModel> getItems() {
        return items;
    }

    public void setItems(List<NecrologyModel> items) {
        this.items = items;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_month() {
        return doc_month;
    }

    public void setDoc_month(String doc_month) {
        this.doc_month = doc_month;
    }

    public String getDoc_year() {
        return doc_year;
    }

    public void setDoc_year(String doc_year) {
        this.doc_year = doc_year;
    }

    public String getDoc_file() {
        return doc_file;
    }

    public void setDoc_file(String doc_file) {
        this.doc_file = doc_file;
    }
}
