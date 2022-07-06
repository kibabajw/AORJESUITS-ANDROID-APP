package org.easternafricajesuits.models;

import com.google.gson.annotations.SerializedName;

public class ProductsModel {

    @SerializedName("product_image")
    private String productimage;

    @SerializedName("product_description")
    private String productdescription;

    public ProductsModel(String productimage, String productdescription) {
        this.productimage = productimage;
        this.productdescription = productdescription;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }
}
