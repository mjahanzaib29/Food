package com.example.food.Sales;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food.Adapter.Ticket_Adapter;
import com.example.food.Customer.Dialog_add_customer;
import com.example.food.Getter.TicketGS;
import com.example.food.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class Ticket extends Fragment implements Ticket_Adapter.interfaceDelete {
    String pname,pprice,pstock,count,dialog_pqty , cname,cemail,cphone  , dname,dtype,dprice;
    TextView textViewtotal,totals,applycustomer;
    LinearLayout linearLayout;
    Ticket_Adapter ticketAdapter;
    public ArrayList<TicketGS> ticketGSList = new ArrayList<>();
    public List<String> newlist = new ArrayList<>();
    RecyclerView ticket_recycler;
    TicketGS ticketGS;
    String two="" ,twotwo = "";
    Button ticket_add_customer,charge;

    public Ticket() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View v = inflater.inflate(R.layout.fragment_ticket, container, false);

//        RECEIVE DATA FROM FROM BROADCAST FROM PRODUCTADAPTER THROUGH ticket-data KEY
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mselectedproducts,
//                new IntentFilter("ticket-data"));
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(productqty,
//                new IntentFilter("ticket-reenter"));
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(customer,
//                new IntentFilter("customer-data"));

        totals = (TextView) v.findViewById(R.id.textTotal);
        charge = (Button) v.findViewById(R.id.charge);
        textViewtotal = (TextView) v.findViewById(R.id.textViewtotal);
        ticket_recycler = (RecyclerView) v.findViewById(R.id.ticketrecycler);
        applycustomer =(TextView) v.findViewById(R.id.applycustomer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ticket_recycler.setLayoutManager(mLayoutManager);

//        ItemTouchHelper.SimpleCallback item =
//                new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(ticket_recycler);





        // SETTING TOTAL PRICE HERE
        totals.setText(overtotal);

        ticket_add_customer = (Button) v.findViewById(R.id.ticket_add_customer);
        ticket_add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_add_customer dialog_add_customer = new Dialog_add_customer();
                if (dialog_add_customer!=null) {
                    dialog_add_customer.show(getFragmentManager(), "Dialog_add_customer");
                }
            }
        });


//        CHARGE AMOUNT
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                charge.setVisibility(View.VISIBLE);
                if (cname == null && cphone == null) {
                    senddata();
                } else {
                    senddatawithcustomer();
                }
            }
        });
        return v;
    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback=
            new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    ticketGSList.remove(viewHolder.getAdapterPosition());
                    ticketAdapter.notifyDataSetChanged();
                    addition(ticketGSList);
                }
            };

//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
//        if (viewHolder instanceof Ticket_Adapter.MyViewHolder)
//        {
//            ticketAdapter.notifyItemRangeRemoved(direction,position);
//        }
//    }


    public BroadcastReceiver mselectedproducts = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            pname =intent.getStringExtra("product_name");
            pprice = intent.getStringExtra("product_price");
            pstock = intent.getStringExtra("product_stock");
            count = intent.getStringExtra("count");
            dialog_pqty = intent.getStringExtra("qty_item");
            send_data(pname,dialog_pqty,pprice);
        }
    };

    int position_qty;
    String overtotal,productprice;
    public BroadcastReceiver productqty = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dialog_pqty = intent.getStringExtra("pqty_item");
            //ticket_price = intent.getStringExtra("pqty_price");
            position_qty = intent.getIntExtra("pcount",0);
            overtotal = intent.getStringExtra("pqty_product");
            productprice = intent.getStringExtra("pqty_price");
            updateqty(overtotal,dialog_pqty,productprice);
        }
    };
// selected customer data from customer_adapter
    public BroadcastReceiver customer = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            cname = intent.getStringExtra("cname");
            cemail = intent.getStringExtra("cemail");
            cphone = intent.getStringExtra("cphone");
            Toast.makeText(context, cemail+cphone+cname, Toast.LENGTH_SHORT).show();
            if (cname == null){
                applycustomer.setText(cphone);
            }
            else{
                applycustomer.setText(cname);
            }

        }
    };


    public BroadcastReceiver selecteddiscount = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dname = intent.getStringExtra("discount-name");
            dtype = intent.getStringExtra("discount-type");
            dprice = intent.getStringExtra("discount-price");
            Toast.makeText(context, "Selected" + dtype + dprice, Toast.LENGTH_SHORT).show();
        }
    };

  //  UPDATING QUANTITY OF ITEMS IN TICKET
    public void updateqty(String p_name,String p_qty, String p_price){
        int a1 = Integer.parseInt(dialog_pqty);
        int b1 = Integer.parseInt(p_price)  ;
        int c1 = a1 * b1;
        p_price = String.valueOf(c1);
        total = c1 ;
        Amount =  total;
//        String cb = String.valueOf(Amount);
//        totals.setText(cb);
        ticketGS = new TicketGS(p_name, p_qty, p_price);
        ticketGSList.set(position_qty,ticketGS);
        ticketAdapter.notifyItemChanged(position_qty);
        ticketAdapter.notifyDataSetChanged();
        addition(ticketGSList);
    }

    int total = 0, Amount = 0;
//    ADDING SELECTED PRODUCT FROM PRODUCT_ADAPTER INTO TICKET
    public void send_data(String p_name,String p_qty,String p_price) {
        int a = Integer.parseInt(pprice);
        int b = Integer.parseInt(dialog_pqty);
        int c = a * b;
        p_price = String.valueOf(c);
        total = c;
        Amount = total;
        String cb = String.valueOf(Amount);

        ticketGS = new TicketGS(p_name, p_qty, p_price);
        ticketGSList.add(ticketGS);
        ticketAdapter = new Ticket_Adapter(ticketGSList, getActivity(), this);
        ticket_recycler.setAdapter(ticketAdapter);
        ticketAdapter.notifyDataSetChanged();
        addition(ticketGSList);
        
    }

    int totaldiscountvalue;
//  ADDITION OF TOTAL PRICE
    public void addition(List<TicketGS> ls)
    {
        int one=0,i;
        for(i = 0; i < ticketGSList.size();i++) {
            one += Integer.parseInt(ls.get(i).getProduct_price());
            two = String.valueOf(one);
        }

        Log.d("Total Value method", "addition: ");
        if(ticketGSList.size() != 0){

            charge.setVisibility(View.VISIBLE);
            textViewtotal.setVisibility(View.VISIBLE);
            totals.setVisibility(View.VISIBLE);
            totals.setText(two);
            if (totals.getText() != null && dprice == null){
              totals.setText(two);
            }
            else if (totals.getText() != null && dprice != null){
                ticketAdapter.notifyDataSetChanged();
                if (dtype == "%") {
                    int tot, dis;
                    int finalamount;
                    dis = Integer.parseInt(dprice);
                    tot = Integer.parseInt(totals.getText().toString());
                    finalamount = (tot * dis) / 100;
                    int lastamount = tot - finalamount;
                    totals.setText(String.valueOf(lastamount));
                }
                else {
                    int dis,tot,finalamount ;
                    dis =Integer.parseInt(dprice);
                    tot = Integer.parseInt(totals.getText().toString());
                    finalamount = tot - dis;
                    totals.setText(String.valueOf(finalamount));

                }
            }
        }
           else {
               charge.setVisibility(View.INVISIBLE);
               totals.setVisibility(View.INVISIBLE);
               textViewtotal.setVisibility(View.INVISIBLE);
               totals.setText("0");
        }
    }

    @Override
    public void updateTotalValue(List<TicketGS> ticketList) {
        addition(ticketList);
    }

//Sending data to Adjust Charge for printing
    public void senddata(){
        Intent i = new Intent(getActivity().getBaseContext(),Adjust_Charge.class);
        i.putExtra("totals" , totals.getText().toString());
//        i.putStringArrayListExtra("test", (ArrayList<String>) newlist);
//        List<TicketGS> ticketlist1 = ticketGSList;
        i.putParcelableArrayListExtra("Total_list",(ArrayList<? extends Parcelable>)ticketGSList);
        //START ACTIVITY
        getActivity().startActivity(i);
    }

    //Sending data to Adjust Charge for printing
    public void senddatawithcustomer(){
        Intent i = new Intent(getActivity().getBaseContext(),Adjust_Charge.class);
        i.putExtra("totals" , totals.getText().toString());
        i.putExtra("billcname" , cname);
        i.putExtra("billcphone" , cphone);

//        i.putStringArrayListExtra("test", (ArrayList<String>) newlist);
//        List<TicketGS> ticketlist1 = ticketGSList;
        i.putParcelableArrayListExtra("Total_list",(ArrayList<? extends Parcelable>)ticketGSList);
        //START ACTIVITY
        getActivity().startActivity(i);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mselectedproducts);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(productqty);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(customer);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(selecteddiscount);
    }

    @Override
    public void onResume() {
        super.onResume();
        //        RECEIVE DATA FROM FROM BROADCAST FROM PRODUCTADAPTER THROUGH ticket-data KEY
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mselectedproducts,
                new IntentFilter("ticket-data"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(productqty,
                new IntentFilter("ticket-reenter"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(customer,
                new IntentFilter("customer-data"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selecteddiscount,
                new IntentFilter("Discount-to-ticket"));
    }


}
