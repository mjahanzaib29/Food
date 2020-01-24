package com.example.food.Getter;

public class TicketGS {
    private String product_name,product_qty,product_price;
    public Double SubTotal,Total;

    public TicketGS(String product_name, String product_qty, String product_price) {
        this.product_name = product_name;
        this.product_qty = product_qty;
        this.product_price = product_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(String product_qty) {
        this.product_qty = product_qty;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
}
