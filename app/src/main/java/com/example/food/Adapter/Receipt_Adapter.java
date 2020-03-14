package com.example.food.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Getter.Receipt;
import com.example.food.R;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

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
        holder.receipt_no.setText(receipt.getRe_id());
        holder.receipt_dateandtime.setText(receipt.getRe_date());
        Currency currency = Currency.getInstance("PKR");
        String symbol = currency.getSymbol();
        holder.receipt_amount.setText(symbol+receipt.getRp_total());
    }

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView receipt_no,receipt_dateandtime , receipt_amount;
        public MyViewHolder(@NonNull View receiptView) {
            super(receiptView);
            receiptView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        Receipt receiptclickeditem = receiptList.get(pos);
                        // SEND DATA TO TICKET
                        Intent intent = new Intent("Selected-Receipt-more-info");
                        intent.putExtra("receipt_id",receiptclickeditem.getRe_id());
                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Receipt");

                        LayoutInflater inflater = (LayoutInflater) v.getContext()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View customLayout = inflater.inflate(R.layout.receipt_complete_dialog, null);
                        builder.setView(customLayout);

                        TextView receipt_total = (TextView) customLayout.findViewById(R.id.receipt_total);
                        receipt_total.setText(receiptclickeditem.getRp_total());
                        TextView receipt_cashier = (TextView) customLayout.findViewById(R.id.receipt_cashier);
                        receipt_cashier.setText(receiptclickeditem.getRe_employee());
                        TextView receipt_pos = (TextView) customLayout.findViewById(R.id.receipt_pos);
                        receipt_pos.setText(receiptclickeditem.getRe_store());
                        TextView receipt_billamount = (TextView) customLayout.findViewById(R.id.receipt_billamount);
                        receipt_billamount.setText(receiptclickeditem.getRe_total());
                        TextView receipt_paid_amount = (TextView) customLayout.findViewById(R.id.receipt_paid_amount);
                        receipt_paid_amount.setText(receiptclickeditem.getRp_cash());
                        TextView receipt_dateandtime = (TextView) customLayout.findViewById(R.id.receipt_dateandtime);
                        receipt_dateandtime.setText(receiptclickeditem.getRe_date());
                        TextView receipt_receiptid = (TextView) customLayout.findViewById(R.id.receipt_receiptid);
                        receipt_receiptid.setText(receiptclickeditem.getRe_id());

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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


                    }
                }
            });
            receipt_no = (TextView) itemView.findViewById(R.id.receipt_no);
            receipt_dateandtime = (TextView) itemView.findViewById(R.id.receipt_dateandtime);
            receipt_amount = (TextView) itemView.findViewById(R.id.receipt_amount);

        }
    }
}
