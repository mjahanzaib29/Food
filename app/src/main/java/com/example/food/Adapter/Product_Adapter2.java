package com.example.food.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Getter.Product;
import com.example.food.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Product_Adapter2 extends RecyclerView.Adapter<Product_Adapter2.MyViewHolder> {
    private List<Product> ProductList;
    Context context;

    public Product_Adapter2(Context context,List<Product> productList) {
        this.ProductList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root;
        root = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_item2, parent, false);
        return new MyViewHolder(root);
    }

    String url = "http://192.168.1.26:8081/foodstore/";
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = ProductList.get(position);
        holder.product_name.setText(product.getP_name());
        holder.product_price.setText(product.getP_price());
        holder.product_stock.setText(product.getP_stock());
        Picasso.with(context).load(url+product.getProduct_image()).into(holder.product_image);
    }

    @Override
    public int getItemCount() {
        return ProductList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,product_price,product_stock;
        private ImageView product_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.pname);
            product_price = (TextView) itemView.findViewById(R.id.pprice);
            product_stock = (TextView) itemView.findViewById(R.id.pstock);
            product_image = (ImageView) itemView.findViewById(R.id.pimage);

        }
    }
}
