package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdusumDocuments {

    @SerializedName("rows")
    private List<AdusumDocuments> items;

    @SerializedName("document_name")
    String doc_name;

    @SerializedName("document_file")
    String doc_file;

    public AdusumDocuments(List<AdusumDocuments> items, String doc_name) {
        this.doc_name = doc_name;
        this.doc_file = doc_file;
    }

    public List<AdusumDocuments> getItems() {
        return items;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public String getDoc_file() {
        return doc_file;
    }

//    public AdusumDocuments(String doc_link, String doc_name) {
//        this.doc_link = doc_link;
//        this.doc_namme = doc_namme;
//    }
//
//    public String getDoc_link() {
//        return doc_link;
//    }
//
//    public String getDoc_name() {
//        return doc_namme;
//    }
//
//    public List<AdusumDocuments> getItems() {
//        return items;
//    }

}
