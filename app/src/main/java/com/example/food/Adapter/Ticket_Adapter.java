package com.example.food.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Getter.TicketGS;
import com.example.food.R;
import com.example.food.Sales.Ticket;

import java.util.List;

public class Ticket_Adapter extends RecyclerView.Adapter<Ticket_Adapter.MyViewHolder> {
    private List<TicketGS> ticketList;
    Context context;
    TicketGS ticket;
    RecyclerView ticket_recycler;
    TextView total;
    interfaceDelete interfaceDeleteListener;

    String overtotalprice="";

    int one,two,t;
    //int total  ;
    public Ticket_Adapter(List<TicketGS> ticketList, Context context, interfaceDelete interfaceDeleteListener)
    {
        this.ticketList =ticketList;
        this.context = context;
        this.interfaceDeleteListener = interfaceDeleteListener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ticket_productname,ticket_productqty,ticket_productprice;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            total = (TextView) itemView.findViewById(R.id.textTotal);
            ticket_productname = (TextView) itemView.findViewById(R.id.ticketitem);
            ticket_productqty = (TextView) itemView.findViewById(R.id.ticketqty);
            ticket_productprice = (TextView) itemView.findViewById(R.id.ticketprice);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.ticketsinglerow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final int ppos = getAdapterPosition();
                    if (ppos != RecyclerView.NO_POSITION){
                        final TicketGS clickedticketitem = ticketList.get(ppos);
                        final String qty =  clickedticketitem.getProduct_qty();
                        final String pri =  clickedticketitem.getProduct_price();


                        one = Integer.parseInt(qty);

                        try {
                            two =Integer.valueOf(pri);
                        } catch (NumberFormatException nfe) {
                            nfe.printStackTrace();
                        }

                        t = one * two;


                         final int sad  = getAdapterPosition();
//                        int ticketcountint = Integer.parseInt(String.valueOf((ticketList.get(ppos))));
                        Toast.makeText(v.getContext(), Integer.toString(sad), Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                        builder1.setMessage("Enter quantity of "+clickedticketitem.getProduct_name());
                        final EditText input = new EditText(v.getContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setInputType(InputType.TYPE_CLASS_NUMBER);

                        input.setLayoutParams(lp);
                        builder1.setView(input);
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (TextUtils.isEmpty(input.getText())){
//                                            DeleteItem(ppos);
//                                            notifyItemRemoved(ppos);
//                                            notifyItemRangeChanged(ppos,ticketList.size());
//                                            dialog.cancel();
                                        }
                                         else if (input.getText().toString().equals("0")){
//                                            DeleteItem(ppos);
//                                            notifyItemRemoved(ppos);
//                                            notifyItemRangeChanged(ppos,ticketList.size());
//                                            dialog.cancel();
                                        }
                                        else{

                                        Intent editticket = new Intent("ticket-reenter");
                                        editticket.putExtra("Total_amount",t);
                                        editticket.putExtra("pqty_item",input.getText().toString());
                                        editticket.putExtra("pqty_product",clickedticketitem.getProduct_name());
                                        editticket.putExtra("pqty_price",clickedticketitem.getProduct_price());
                                        editticket.putExtra("pcount",sad);
                                        editticket.putExtra("settotal",overtotalprice);
                                        editticket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(editticket);
//                                        DeleteItem(ppos);

                                        dialog.cancel();}
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                        //LONG PRESS
                        itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                AlertDialog.Builder alertremove = new AlertDialog.Builder(v.getContext());
                                alertremove.setMessage("Remove item "+clickedticketitem.getProduct_name());
//                                final EditText input = new EditText(v.getContext());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
//                                input.setInputType(InputType.TYPE_CLASS_NUMBER);

//                                input.setLayoutParams(lp);
//                                alertremove.setView(input);
                                alertremove.setCancelable(true);

                                alertremove.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                DeleteItem(ppos);
                                                notifyItemRemoved(ppos);
                                                notifyItemRangeChanged(ppos,ticketList.size());
                                                dialog.cancel();
                                            }
                                        });

                                alertremove.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = alertremove.create();
                                alert11.show();

                                return true;
                            }
                        });
                    }
                }
            });


        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_ticket ,parent,false);
        return new MyViewHolder(v);
    }

    public interface interfaceDelete{
        void updateTotalValue(List<TicketGS> ticketList);
    }

    int totalsum=0 ;
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        ticket = ticketList.get(position);

        holder.ticket_productname.setText(ticket.getProduct_name());
        holder.ticket_productqty.setText(ticket.getProduct_qty());
        holder.ticket_productprice.setText(ticket.getProduct_price());
//        int totalprice = Integer.parseInt(ticket.getProduct_qty()) * Integer.parseInt(ticket.getProduct_price());
//        totalsum = totalsum + totalprice;

    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    private void DeleteItem(int Position){
        ticketList.remove(Position);
        notifyItemRemoved(Position);
        notifyItemRangeChanged(Position,ticketList.size());
        interfaceDeleteListener.updateTotalValue(ticketList);
    }
}
