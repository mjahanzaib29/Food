package com.example.food.Getter;

import com.google.gson.annotations.SerializedName;

public class Customer {
    @SerializedName("cu_id")
    private int cu_id;
    @SerializedName("cu_name")
    private String cu_name;
    @SerializedName("cu_email")
    private String cu_email;
    @SerializedName("cu_phone")
    private String cu_phone;
    @SerializedName("cu_note")
    private String cu_note;
    @SerializedName("cd_loyalpoint")
    private String cu_royalty;
    @SerializedName("cd_visit")
    private String cu_visits;
    @SerializedName("cd_lastvisit")
    private String cu_lastvisit;


    public Customer(int cu_id, String cu_name, String cu_email, String cu_phone, String cu_note , String cu_royalty , String cu_visits , String cu_lastvisit) {
        this.cu_id = cu_id;
        this.cu_name = cu_name;
        this.cu_email = cu_email;
        this.cu_phone = cu_phone;
        this.cu_note = cu_note;
        this.cu_royalty = cu_royalty;
        this.cu_visits = cu_visits;
        this.cu_lastvisit = cu_lastvisit;
    }

    public String getCu_royalty() {
        return cu_royalty;
    }

    public String getCu_visits() {
        return cu_visits;
    }

    public String getCu_lastvisit() {
        return cu_lastvisit;
    }

    public String getCu_name() {
        return cu_name;
    }

    public String getCu_email() {
        return cu_email;
    }

    public String getCu_phone() {
        return cu_phone;
    }

    public String getCu_note(){
        return cu_note;
    }

    public int getCu_id(){
        return cu_id;
    }
}
