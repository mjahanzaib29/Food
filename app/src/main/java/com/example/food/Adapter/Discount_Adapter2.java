package com.example.food.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Getter.Discount;
import com.example.food.R;

import java.util.List;

public class Discount_Adapter2 extends RecyclerView.Adapter<Discount_Adapter2.MyViewHolder> {

     Context context;
     List<Discount> discountList;

    public Discount_Adapter2(Context context, List<Discount> discountList) {
        this.context = context;
        this.discountList = discountList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_discount,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Discount discount = discountList.get(position);
        holder.discount_name.setText(discount.getD_name());
        holder.discount_price.setText(discount.getD_value());
    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView discount_name,discount_price;
        public MyViewHolder(@NonNull View discountView) {
            super(discountView);
            discount_name = (TextView) discountView.findViewById(R.id.discount2_name);
            discount_price = (TextView) discountView.findViewById(R.id.discount2_price);

        }
    }
}
