package com.morgen.morgencoffee.Response;

import com.morgen.morgencoffee.Model.ProductModel;

import java.util.List;

public class ProductGetResponse {
    private List<ProductModel> data;

    public ProductGetResponse(List<ProductModel> data) {
        this.data = data;
    }

    public List<ProductModel> getData() {
        return data;
    }

    public void setData(List<ProductModel> data) {
        this.data = data;
    }
}
