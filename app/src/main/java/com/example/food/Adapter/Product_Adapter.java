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

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.MyViewHolder> {

    //private GridLayoutManager product_context;
    private List<Product> productList;
    private int selectedPos = RecyclerView.NO_POSITION;
    private Context context;
    private static final int GRID_VIEW_LAYOUT = 1;
    private static final int LINEAR_VIEW_LAYOUT = 2;
    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 3;

    public Product_Adapter(Context product_context, List<Product> productList) {
        this.context = product_context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == GRID_VIEW_LAYOUT) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_item_grid, parent, false);
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_item, parent, false);
        }
        return new MyViewHolder(v,viewType);
    }
    String url = "http://192.168.1.13:8081/foodstore/";
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemView.setSelected(selectedPos == position);
        Product product = productList.get(position);
        holder.product_name.setText(product.getP_name());
        holder.product_price.setText(product.getP_price());
        holder.product_stock.setText(product.getP_stock());
//        holder.product_image.setImageBitmap(product.getProduct_image());
//        Picasso.with(context).load(product.getProduct_image()).into(holder.product_image);
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

        public MyViewHolder(final View productVIew, int viewType) {
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


//                        AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
//                        builder1.setMessage("Enter quantity of "+clickeditem.getP_name());
//                        final EditText input = new EditText(v.getContext());
//                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.MATCH_PARENT);
//                        input.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//                        input.setLayoutParams(lp);
//                        builder1.setView(input);
//                        builder1.setCancelable(true);
//
//                        builder1.setPositiveButton(
//                                "Yes",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
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

//                                        dialog.cancel();
//                                    }
//                                });
//
//                        builder1.setNegativeButton(
//                                "No",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//
//                        AlertDialog alert11 = builder1.create();
//                        alert11.show();



//                        Bundle bundle = new Bundle();
//                        bundle.putString("product_name",clickeditem.getP_name());
//                        bundle.putString("product_price",clickeditem.getP_price());
//                        bundle.putString("product_stock",clickeditem.getP_stock());
//                        bundle.putString("count",count);
////
//                        Ticket ticketobj = new Ticket();
//                        ticketobj.setArguments(bundle);
//                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.ticket,ticketobj).addToBackStack(null).commit();
                    }

                }

            });

            if (viewType == GRID_VIEW_LAYOUT){
                grid_product_name = (TextView) productVIew.findViewById(R.id.grid_product_name);
            }

            else {
//                product_name = (TextView) productVIew.findViewById(R.id.itemlist_name);
//                product_price = (TextView) productVIew.findViewById(R.id.itemlist_price);
//                product_stock = (TextView) productVIew.findViewById(R.id.itemlist_stock);
//                product_image = (ImageView) productVIew.findViewById(R.id.itemlist_image);
            }
        }

    }

    public void remove(Product data) {
        int position = productList.indexOf(data);
        productList.remove(position);
        notifyItemRemoved(position);
    }
}
