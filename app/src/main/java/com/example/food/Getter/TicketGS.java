package com.example.food.Getter;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketGS implements Parcelable{
    private String product_name,product_qty,product_price;
    public Double SubTotal,Total;

    public TicketGS(String product_name, String product_qty, String product_price) {
        this.product_name = product_name;
        this.product_qty = product_qty;
        this.product_price = product_price;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product_name);
        dest.writeString(product_qty);
        dest.writeString(product_price);
    }
    public static final Parcelable.Creator<TicketGS> CREATOR = new Parcelable.Creator<TicketGS>() {
        public TicketGS createFromParcel(Parcel in) {
            return new TicketGS(in);
        }

        public TicketGS[] newArray(int size) {
            return new TicketGS[size];

        }
    };

    public TicketGS(Parcel in) {
        product_name=in.readString();
        product_qty=in.readString();
        product_price=in.readString();
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
