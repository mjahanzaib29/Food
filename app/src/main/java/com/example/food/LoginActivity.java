package com.example.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;
    TextInputEditText customer_email,customer_passowrd;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        customer_email = (TextInputEditText ) findViewById(R.id.customer_email);
        customer_passowrd = (TextInputEditText ) findViewById(R.id.customer_password);
        login_btn = (Button) findViewById(R.id.login_button);
// Session Manager
        session = new SessionManager(getApplicationContext());
        // Alert Dialog Manager
        AlertDialog alert;

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email= customer_email.getText().toString();
               String pass = customer_passowrd.getText().toString();
               AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());


                // Check if username, password is filled
                if(email.trim().length() > 0 && pass.trim().length() > 0) {
                    // For testing puspose username, password is checked with sample data
                    // username = test
                    // password = test
                    if (email.equals("test") && pass.equals("test")) {

                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession("Android Hive", "anroidhive@gmail.com");

                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                }

            }
        });

    }
}
