package com.example.food.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Getter.Receipt;
import com.example.food.R;

import java.util.List;

public class Receipt_Adapter extends RecyclerView.Adapter<Receipt_Adapter.MyViewHolder> {
    private Context context;
    private List<Receipt> receiptList;

    public Receipt_Adapter(Context context, List<Receipt> receiptList) {
        this.context = context;
        this.receiptList = receiptList;
    }

    @NonNull
    @Override
    public Receipt_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_receipt,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Receipt_Adapter.MyViewHolder holder, int position) {
        Receipt receipt = receiptList.get(position);
    }

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
