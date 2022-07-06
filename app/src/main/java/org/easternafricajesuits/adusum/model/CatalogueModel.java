package org.easternafricajesuits.adusum.model;

import com.google.gson.annotations.SerializedName;

public class CatalogueModel {

    @SerializedName("catalogue_name")
    private String catalogue_name;

    @SerializedName("catalogue_file")
    private String catalogue_link;

    public CatalogueModel(String catalogue_name, String catalogue_link) {
        this.catalogue_name = catalogue_name;
        this.catalogue_link = catalogue_link;
    }

    public String getCatalogue_name() {
        return catalogue_name;
    }

    public void setCatalogue_name(String catalogue_name) {
        this.catalogue_name = catalogue_name;
    }

    public String getCatalogue_link() {
        return catalogue_link;
    }

    public void setCatalogue_link(String catalogue_link) {
        this.catalogue_link = catalogue_link;
    }
}
