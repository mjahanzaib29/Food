package com.example.food.Getter;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("c_name")
  private String c_name;
    @SerializedName("c_image")
  private int c_image;
    public Category() {
    }

    public Category(String c_name, int c_image) {
        this.c_name = c_name;
        this.c_image = c_image;

    }

    public String getC_name() {
        return c_name;
    }

    public int getC_image() {
        return c_image;
    }
}
