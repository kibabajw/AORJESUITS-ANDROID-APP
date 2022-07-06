package org.easternafricajesuits.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsReceivedModel {

    @SerializedName("products")
    private List<ProductsModel> productsModel;

    public List<ProductsModel> getProductsModel() {
        return productsModel;
    }

    public void setProductsModel(List<ProductsModel> productsModel) {
        this.productsModel = productsModel;
    }
}
