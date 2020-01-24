package com.example.food.Getter;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("i_name")
    private String p_name;
    @SerializedName("i_price")
    private String p_price;
    @SerializedName("i_stock")
    private String p_stock;
    @SerializedName("response")
    private String response;
    private int count;
    @SerializedName("i_image")
    private String product_image;

    public Product(String p_name, String p_price, String p_stock, int count, String product_image ) {
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_stock = p_stock;
        this.count = count;
        this.product_image = product_image;
    }


    public String getProduct_image() {
        return product_image;
    }

    public String getResponse() {
        return response;
    }

    public String getP_name() {
        return p_name;
    }

    public String getP_price() {
        return p_price;
    }

    public String getP_stock() {
        return p_stock;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


