package com.example.food.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Getter.Product;
import com.example.food.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Product_Adapter1 extends RecyclerView.Adapter<Product_Adapter1.MyViewHolder> {

    //private GridLayoutManager product_context;
    private List<Product> productList;
    private int selectedPos = RecyclerView.NO_POSITION;
    private Context context;
    private static final int GRID_VIEW_LAYOUT = 1;
    private static final int LINEAR_VIEW_LAYOUT = 2;
    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 3;

    public Product_Adapter1(Context product_context, List<Product> productList) {
        this.context = product_context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_item, parent, false);
        return new MyViewHolder(v);
    }
    String url = "http://192.168.1.23:8081/foodstore/";
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemView.setSelected(selectedPos == position);
        Product product = productList.get(position);
        holder.product_name.setText(product.getP_name());
        holder.product_price.setText(product.getP_price());
        holder.product_stock.setText(product.getP_stock());
        Picasso.with(context).load(url+product.getProduct_image()).into(holder.product_image);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView product_name,product_price,product_stock, grid_product_name;
        private ImageView product_image;

        // Remove a RecyclerView item containing a specified Data object

        public MyViewHolder(final View productVIew) {
            super(productVIew);
            productVIew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
//                    GETTING POSITION OF CURRENT CLICK ITEM
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION){
                        final Product clickeditem = productList.get(pos);
                        productList.get(pos).setCount(productList.get(pos).getCount()+1);
                        final String count = String.valueOf(productList.get(pos).getCount());

                        //      TO SEND DATA TO TICKET FRAGMENT
                        Intent sendtoticket = new Intent("ticket-data");
                        sendtoticket.putExtra("product_name",clickeditem.getP_name());
                        sendtoticket.putExtra("product_price",clickeditem.getP_price());
                        sendtoticket.putExtra("product_stock",clickeditem.getP_stock());
                        sendtoticket.putExtra("count",count);
                        int qty = 1;
                        sendtoticket.putExtra("qty_item",String.valueOf(qty));
                        sendtoticket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(sendtoticket);
                    }

                }

            });
                product_name = (TextView) productVIew.findViewById(R.id.item_list_name);
                product_price = (TextView) productVIew.findViewById(R.id.item_list_price);
                product_stock = (TextView) productVIew.findViewById(R.id.item_list_stock);
                product_image = (ImageView) productVIew.findViewById(R.id.item_list_image);

        }

    }

    public void remove(Product data) {
        int position = productList.indexOf(data);
        productList.remove(position);
        notifyItemRemoved(position);
    }
}
