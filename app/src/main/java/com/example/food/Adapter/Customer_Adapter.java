package com.example.food.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Customer.Dialog_add_customer;
import com.example.food.Getter.Customer;
import com.example.food.R;

import java.util.List;

public class Customer_Adapter extends RecyclerView.Adapter<Customer_Adapter.MyViewHolder> {
    private Context context;
    private List<Customer> customerList;
    Dialog_add_customer dialog_add_customer;
    public Customer_Adapter(Dialog_add_customer dialog_add_customer){
        this.dialog_add_customer=dialog_add_customer;
    }

    public Customer_Adapter(Context context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_customer,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.Customer_name.setText(customer.getCu_name());
        holder.Customer_email.setText(customer.getCu_email());
        holder.Customer_phone.setText(","+customer.getCu_phone());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Customer_name,Customer_email,Customer_phone;
        public MyViewHolder(@NonNull View customerView) {
            super(customerView);
            customerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Customer clickcustomer =  customerList.get(pos);
                        Intent send_to_ticket =  new Intent("customer-data");
                        send_to_ticket.putExtra("cname",clickcustomer.getCu_name());
                        send_to_ticket.putExtra("cemail",clickcustomer.getCu_email());
                        send_to_ticket.putExtra("cphone",clickcustomer.getCu_phone());
                        send_to_ticket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(send_to_ticket);
//                        String countVisits = customerList.get(pos).setCount(customerList.get(pos).getCount()+1);
                        if (dialog_add_customer != null) {
                            dialog_add_customer.dismiss();
                        }
                    }
                }
            });
            Customer_name = (TextView) customerView.findViewById(R.id.customer_name);
            Customer_email = (TextView) customerView.findViewById(R.id.customer_email);
            Customer_phone = (TextView) customerView.findViewById(R.id.customer_phone);
        }
    }
}
