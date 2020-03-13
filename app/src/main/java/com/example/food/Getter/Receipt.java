package com.example.food.Getter;

import com.google.gson.annotations.SerializedName;

public class Receipt {
    @SerializedName("re_id")
    private String re_id;
    @SerializedName("re_date")
    private String re_date;
    @SerializedName("re_store")
    private String re_store;
    @SerializedName("re_employee")
    private String re_employee;
    @SerializedName("re_customer")
    private String re_customer;
    @SerializedName("re_total")
    private String re_total;
    @SerializedName("rp_total")
    private String rp_total;
    @SerializedName("rp_cash")
    private String rp_cash;
    @SerializedName("rp_change")
    private String rp_change;
    @SerializedName("rd_itemname")
    private String rd_itemname;
    @SerializedName("rd_qty")
    private String rd_qty;
    @SerializedName("rd_price")
    private String rd_price;

    public Receipt(String re_id, String re_date, String re_store, String re_employee, String re_customer, String re_total, String rp_total, String rp_cash, String rp_change, String rd_itemname, String rd_qty, String rd_price) {
        this.re_id = re_id;
        this.re_date = re_date;
        this.re_store = re_store;
        this.re_employee = re_employee;
        this.re_customer = re_customer;
        this.re_total = re_total;
        this.rp_total = rp_total;
        this.rp_cash = rp_cash;
        this.rp_change = rp_change;
        this.rd_itemname = rd_itemname;
        this.rd_qty = rd_qty;
        this.rd_price = rd_price;
    }

    public String getRe_id() {
        return re_id;
    }

    public String getRe_date() {
        return re_date;
    }

    public String getRe_store() {
        return re_store;
    }

    public String getRe_employee() {
        return re_employee;
    }

    public String getRe_customer() {
        return re_customer;
    }

    public String getRe_total() {
        return re_total;
    }

    public String getRp_total() {
        return rp_total;
    }

    public String getRp_cash() {
        return rp_cash;
    }

    public String getRp_change() {
        return rp_change;
    }

    public String getRd_itemname() {
        return rd_itemname;
    }

    public String getRd_qty() {
        return rd_qty;
    }

    public String getRd_price() {
        return rd_price;
    }
}
