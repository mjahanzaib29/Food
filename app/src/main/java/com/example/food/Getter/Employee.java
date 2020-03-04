package com.example.food.Getter;

import com.google.gson.annotations.SerializedName;

public class Employee {
    @SerializedName("email")
    private String e_email;
    @SerializedName("name")
    private String e_name;
    @SerializedName("response")
    private String response;

    public Employee(String e_email, String e_name, String response) {
        this.e_email = e_email;
        this.e_name = e_name;
        this.response = response;
    }

    public String getE_email() {
        return e_email;
    }

    public String getE_name() {
        return e_name;
    }

    public String getResponse() {
        return response;
    }
}