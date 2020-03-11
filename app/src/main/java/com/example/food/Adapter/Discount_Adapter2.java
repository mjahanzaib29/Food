package com.example.food.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
        holder.discount2_name.setText(discount.getD_name());
        holder.discount2_price.setText(discount.getD_value()+discount.getD_type());
//        holder.discount2_type.setText(discount.getD_type());
    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView discount2_name,discount2_price,discount2_type;
        public MyViewHolder(@NonNull View discount2View) {
            super(discount2View);
            discount2View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Discount clickeditem =  discountList.get(pos);
//                        SEND DATA TO TICKET
                        Intent senttoticket = new Intent("Discount-to-ticket");
                        senttoticket.putExtra("discount-name",clickeditem.getD_name());
                        senttoticket.putExtra("discount-price",clickeditem.getD_value());
                        senttoticket.putExtra("discount-type",clickeditem.getD_type());
//                        Toast.makeText(context, clickeditem.getD_name()+clickeditem.getD_type()+clickeditem.getD_value(), Toast.LENGTH_SHORT).show();
//                        senttoticket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(senttoticket);
                    }
                }
            });
            discount2_name = (TextView) discount2View.findViewById(R.id.discount2_name);
            discount2_price = (TextView)discount2View.findViewById(R.id.discount2_price);
            discount2_type = (TextView) discount2View.findViewById(R.id.discount2_type);
        }
    }
}
