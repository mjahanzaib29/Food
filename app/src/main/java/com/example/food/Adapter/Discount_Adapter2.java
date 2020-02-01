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

public class Discount_Adapter2 extends RecyclerView.Adapter<Discount_Adapter2.MyViewHolder>{
    private Context context;
    private List<Discount> discountList;

    public Discount_Adapter2(Context context, List<Discount> discountList) {
        this.context = context;
        this.discountList = discountList;
    }

    @NonNull
    @Override
    public Discount_Adapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_discount2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Discount_Adapter2.MyViewHolder holder, int position) {
        Discount discount = discountList.get(position);
        holder.discount2_name.setText(discount.getD_name()+discount.getD_type());
        holder.discount2_price.setText(discount.getD_value());
    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView discount2_name,discount2_price;
        public MyViewHolder(@NonNull View discount2View) {
            super(discount2View);
            discount2_name = (TextView) discount2View.findViewById(R.id.discount2_name);
            discount2_price = (TextView)discount2View.findViewById(R.id.discount2_price);
        }
    }
}
