package com.example.food.Customer;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dialog_single_customer extends DialogFragment {
    TextView visitdate,save_new_customer;
//    EditText customer_name;
    TextInputEditText customer_name,customer_email,customer_number,customer_note,created_customer;
    TextInputLayout customer_email_wrapper,customer_name_layout;
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    APIInterface apiInterface;

    String c_name,c_email,c_number,c_note, mydate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_single_customer,container,false);
        visitdate = (TextView) v.findViewById(R.id.visitdate);
        save_new_customer = (TextView) v.findViewById(R.id.save_new_customer);

        customer_name = (TextInputEditText) v.findViewById(R.id.customer_name);
        customer_email = (TextInputEditText) v.findViewById(R.id.customer_email);
        customer_number = (TextInputEditText) v.findViewById(R.id.customer_number);
        customer_note = (TextInputEditText) v.findViewById(R.id.customer_note);

        customer_email_wrapper = (TextInputLayout) v.findViewById(R.id.customer_email_wrapper);
        customer_name_layout = (TextInputLayout) v.findViewById(R.id.customer_name_layout);


        customer_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                mydate = java.text.DateFormat.getDateInstance().format(new Date());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                c_email = String.valueOf(customer_email.getText());
                if (!validateEmail(c_email)){
                    customer_email_wrapper.setError("Not a valid email address!");
                }
                else
                    customer_email_wrapper.setErrorEnabled(false);
            }
        });
        customer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                mydate = java.text.DateFormat.getDateInstance().format(new Date());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                c_name = String.valueOf(customer_name.getText());
            }
        });
        customer_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                mydate = java.text.DateFormat.getDateInstance().format(new Date());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                c_number = String.valueOf(customer_number.getText());
            }
        });
        customer_note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                c_note = String.valueOf(customer_note.getText());
            }
        });
        mydate = java.text.DateFormat.getDateInstance().format(new Date());
        Toast.makeText(getContext(), mydate, Toast.LENGTH_SHORT).show();
        save_new_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_customer(c_name,c_email,c_number,c_note,mydate);
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog!= null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private void add_customer(String name,String email,String number,String note,String created){
        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseBody> create = apiInterface.create_customer(name,email,number,note,created);
        create.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(), "User Registered", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
