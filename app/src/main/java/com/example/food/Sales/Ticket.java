package com.example.food.Sales;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ticket extends Fragment implements Ticket_Adapter.interfaceDelete{
    String pname,pprice,pstock,count,dialog_pqty;
    TextView tname,totals;
    LinearLayout linearLayout;
    private Ticket_Adapter ticketAdapter;
    private List<TicketGS> ticketGSList = new ArrayList<>();
    RecyclerView ticket_recycler;
    TicketGS ticketGS;
    String two;
    Button ticket_add_customer;

    public Ticket() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View v = inflater.inflate(R.layout.fragment_ticket, container, false);

//        RECEIVE DATA FROM FROM BROADCAST FROM PRODUCTADAPTER THROUGH ticket-data KEY
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mselectedproducts,
                new IntentFilter("ticket-data"));
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(productqty,
                new IntentFilter("ticket-reenter"));

        totals = (TextView) v.findViewById(R.id.textTotal);
//        tname = (TextView) v.findViewById(R.id.tname);
        ticket_recycler = (RecyclerView) v.findViewById(R.id.ticketrecycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ticket_recycler.setLayoutManager(mLayoutManager);

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
        return v;
    }

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
    String overtotal;
    public BroadcastReceiver productqty = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dialog_pqty = intent.getStringExtra("pqty_item");
            //ticket_price = intent.getStringExtra("pqty_price");
            position_qty = intent.getIntExtra("pcount",0);
            overtotal = intent.getStringExtra("settotal");
            updateqty(pname,dialog_pqty,pprice);
        }
    };

  //  UPDATING QUANTITY OF ITEMS IN TICKET
    public void updateqty(String p_name,String p_qty, String p_price){
        int a1 = Integer.parseInt(dialog_pqty);
        int b1 = Integer.parseInt(pprice)  ;
        int c1 = a1 * b1;
        p_price = String.valueOf(c1);
        total = c1 ;
        Amount =  total;
        String cb = String.valueOf(Amount);
//        totals.setText(cb);
        ticketGS = new TicketGS(p_name, p_qty, p_price);
        ticketGSList.set(position_qty,ticketGS);
        ticketAdapter.notifyItemChanged(position_qty);
        ticketAdapter.notifyDataSetChanged();
        addition(ticketGSList);
    }

    int total = 0, Amount = 0;
//    ADDING SELECTED PRODUCT FROM PRODUCT_ADAPTER INTO TICKET
    public void send_data(String p_name,String p_qty, String p_price) {
            int a = Integer.parseInt(pprice);
            int b = Integer.parseInt(dialog_pqty)  ;
            int c = a * b;
            p_price = String.valueOf(c);
            total = c ;
            Amount =  total;
            String cb = String.valueOf(Amount);
//            totals.setText(cb);
        ticketGS = new TicketGS(p_name, p_qty, p_price);
        ticketGSList.add(ticketGS);
        ticketAdapter = new Ticket_Adapter(ticketGSList, getActivity(),this);
        ticket_recycler.setAdapter(ticketAdapter);
        ticketAdapter.notifyDataSetChanged();
            addition(ticketGSList);
    }

//  ADDITION OF TOTAL PRICE
    public void addition(List<TicketGS> ls)
    {
        int one=0;
        for(int i = 0; i < ticketGSList.size();i++) {
            one += Integer.parseInt(ls.get(i).getProduct_price());
            two = String.valueOf(one);
        }
        Log.d("Total Value method", "addition: ");
        totals.setText(two);
        Toast.makeText(getActivity(), "final value : " + two, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateTotalValue(List<TicketGS> ticketList) {
        addition(ticketList);
    }
}
