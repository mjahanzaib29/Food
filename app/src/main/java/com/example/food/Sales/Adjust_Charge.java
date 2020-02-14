package com.example.food.Sales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food.R;

public class Adjust_Charge extends AppCompatActivity {
    TextView cash_total,cash_change;
    EditText change_received;
    Button charge;
    double finalamount;
    String total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust__charge);
        cash_total = (TextView) findViewById(R.id.cash_total);
        cash_change = (TextView) findViewById(R.id.cash_change);
        change_received = (EditText) findViewById(R.id.ET_cash_change);
        charge = (Button) findViewById(R.id.btn_charge);

        Intent i = getIntent();
        total = i.getStringExtra("totals");
        if (total!=null){
            cash_total.setText(total);
            change_received.setText(total);
            int totalint = Integer.parseInt(total);
            int received = Integer.parseInt(change_received.getText().toString());
            finalamount = totalint - received;

        }

        change_received.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            int received;
            @Override
            public void afterTextChanged(Editable s) {
                if (String.valueOf(change_received)!= null) {
                    int totalint = Integer.parseInt(total);
                    try {
                        received = Integer.parseInt(change_received.getText().toString());
                    }catch (NumberFormatException e){
                        System.out.println("Cannot be 0");
                    }

                    finalamount = received - totalint;
                    cash_change.setText(String.valueOf(finalamount));
                }
            }
        });

        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
