package com.example.food.Customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.food.R;

public class Dialog_customer_profile extends DialogFragment {
    TextView btn_add_to_ticket,c_View_name,c_View_email,c_View_phone,c_View_note,c_View_points,c_View_visits,c_View_lastvisit,btn_edit_profile,btn_redeempoints;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.dialog_customer_profile,container,false);

        btn_add_to_ticket = (TextView) v.findViewById(R.id.btn_add_to_ticket);
        btn_edit_profile = (TextView) v.findViewById(R.id.btn_edit_profile);
        btn_redeempoints = (TextView) v.findViewById(R.id.btn_redeem_points);

        c_View_name = (TextView) v.findViewById(R.id.customer_Viewname);
        c_View_email = (TextView) v.findViewById(R.id.c_Viewemail);
        c_View_phone = (TextView) v.findViewById(R.id.c_Viewnumber);
        c_View_note = (TextView) v.findViewById(R.id.c_Viewnote);

        c_View_points = (TextView) v.findViewById(R.id.c_VIewpoints);
        c_View_visits = (TextView) v.findViewById(R.id.c_Viewvisits);
        c_View_lastvisit = (TextView) v.findViewById(R.id.c_Viewdate);
        return v;
    }
}
