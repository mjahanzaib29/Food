package com.example.food.Sales;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.food.R;

public class Dialog_add_product_to_fragment extends DialogFragment {
    EditText product_qty;
    Button set;
    String p_qty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.dialog_add_product_to_fragment,container,false);
        product_qty = (EditText) view.findViewById(R.id.dialog_product_qty);
        set = (Button) view.findViewById(R.id.set);
        p_qty = product_qty.getText().toString();

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog_add_product_to_fragment asd = new Dialog_add_product_to_fragment();

                Bundle bundle=new Bundle();
                bundle.putString("message", p_qty);
                //set Fragmentclass Arguments
                Ticket fragobj=new Ticket();
                fragobj.setArguments(bundle);

//                Intent dialogproductqty = new Intent("dialog_product_qty");
//                dialogproductqty.putExtra("dialog_p_qty",p_qty);
//                dialogproductqty.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                dialogproductqty.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(dialogproductqty);
                dismiss();
            }
        });

        return view;
    }
}
