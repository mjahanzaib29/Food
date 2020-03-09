package com.example.food.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.Customer.Dialog_add_customer;
import com.example.food.Getter.Customer;
import com.example.food.R;
import com.example.food.ui.Sales.SalesFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.logging.Handler;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Customer_Adapter extends RecyclerView.Adapter<Customer_Adapter.MyViewHolder> {
    private Context context;
    private List<Customer> customerList;
    Dialog_add_customer dialog_add_customer;
    Customer clickcustomer;
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
        public MyViewHolder(@NonNull final View customerView) {
            super(customerView);
            customerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        clickcustomer =  customerList.get(pos);
//                        Intent send_to_ticket =  new Intent("customer-data");
//
//                        send_to_ticket.putExtra("cname",clickcustomer.getCu_name());
//                        send_to_ticket.putExtra("cemail",clickcustomer.getCu_email());
//                        send_to_ticket.putExtra("cphone",clickcustomer.getCu_phone());
//                        send_to_ticket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(send_to_ticket);

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Edit Customer");

                        // set the custom layout
//                        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        LayoutInflater inflater = (LayoutInflater) v.getContext()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View customLayout = inflater.inflate(R.layout.edit_customerdialog, null);
                        builder.setView(customLayout);
//                        LinearLayout linearLayout = new LinearLayout(v.getContext());
//                        linearLayout.setOrientation(LinearLayout.VERTICAL);

                        final TextInputEditText cname = (TextInputEditText)(customLayout.findViewById(R.id.ec_name));
                        cname.setText(clickcustomer.getCu_name());

                        final TextInputEditText cemail = (TextInputEditText)(customLayout.findViewById(R.id.ec_email));
                        cemail.setText(clickcustomer.getCu_email());

                        final TextInputEditText cphone = (TextInputEditText)(customLayout.findViewById(R.id.ec_phone));
                        cphone.setText(clickcustomer.getCu_phone());

                        final TextInputEditText cnote = (TextInputEditText)(customLayout.findViewById(R.id.ec_note));
                        cnote.setText(clickcustomer.getCu_note());


                        final TextInputEditText cvisits = (TextInputEditText)(customLayout.findViewById(R.id.evisits));
                        cvisits.setText(clickcustomer.getCu_visits());


                        final TextInputEditText cloyalpoints = (TextInputEditText)(customLayout.findViewById(R.id.eroyalty));
                        cloyalpoints.setText(clickcustomer.getCu_royalty());


                        final TextInputEditText clastvisit = (TextInputEditText)(customLayout.findViewById(R.id.elastvisits));
                        clastvisit.setText(clickcustomer.getCu_lastvisit());

//                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.MATCH_PARENT);
//                        builder.setView(linearLayout);

                        builder.setCancelable(true);
                        builder.setNeutralButton("Add to ticket", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent send_to_ticket =  new Intent("customer-data");
                                send_to_ticket.putExtra("cname",clickcustomer.getCu_name());
                                send_to_ticket.putExtra("cemail",clickcustomer.getCu_email());
                                send_to_ticket.putExtra("cphone",clickcustomer.getCu_phone());
                                send_to_ticket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                LocalBroadcastManager.getInstance(customerView.getContext()).sendBroadcast(send_to_ticket);
                                try {
                                    dialog_add_customer.dismiss();
                                }
                                catch (Exception e){}

                            }
                        });
                        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = clickcustomer.getCu_id();
                               String name= cname.getText().toString();
                               String email= cemail.getText().toString();
                               String phone= cphone.getText().toString();
                               String note = cnote.getText().toString();
                               edit_customer(id,name,email,phone,note);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alert11 = builder.create();
                        alert11.show();

                        if (alert11!= null){
                            int width = ViewGroup.LayoutParams.MATCH_PARENT;
                            int height = ViewGroup.LayoutParams.MATCH_PARENT;
                            alert11.getWindow().setLayout(width, height);
                        }


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
    private void edit_customer(int id,String name,String email, String phone, String note){
        APIInterface apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseBody> create = apiInterface.editcustomer(id,name,email,phone,note);
        create.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
