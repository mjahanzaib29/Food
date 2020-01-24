package com.example.food.Getter;

import com.google.gson.annotations.SerializedName;

public class Discount {
    @SerializedName("d_name")
    private String d_name;
    @SerializedName("d_value")
    private String d_value;
    @SerializedName("d_type")
    private String d_type;

    public Discount(String d_name, String d_value, String d_type) {
        this.d_name = d_name;
        this.d_value = d_value;
        this.d_type = d_type;

    }

    public String getD_name() {
        return d_name;
    }

    public String getD_value() {
        return d_value;
    }

    public String getD_type() {
        return d_type;
    }
}
