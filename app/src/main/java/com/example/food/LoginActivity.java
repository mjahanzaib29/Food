package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.food.Getter.Employee;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;
    TextInputEditText customer_email,customer_passowrd;
    ProgressBar simpleProgressBar;
    int progress = 0;
    SharedPreferences pref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        customer_email = (TextInputEditText ) findViewById(R.id.customer_email);
        customer_passowrd = (TextInputEditText ) findViewById(R.id.customer_password);
        login_btn = (Button) findViewById(R.id.login_button);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);

        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(LoginActivity.this,MainActivity.class);
        if (pref.contains("Name") && pref.contains("Email")){
            startActivity(intent);
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email= customer_email.getText().toString();
               String pass = customer_passowrd.getText().toString();

                // Check if username, password is filled
                if(email.trim().length() > 0 && pass.trim().length() > 0) {
                        login(email,pass);
                }

            }
        });

    }
    private void setProgressValue(final int progress) {

        // set the progress
        simpleProgressBar.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
    }
     public void login(final String email , final String password){
        APIInterface apiInterface = APIClient.getApiClient().create(APIInterface.class);
         Call<Employee> call = apiInterface.get_employee(email,password);
         call.enqueue(new Callback<Employee>() {
             @Override
             public void onResponse(Call<Employee> call, Response<Employee> response) {
                 if (response.body().getResponse().equals("Bingo")){
                     String ename = response.body().getE_name();
                     String eemail= response.body().getE_email();
                     SharedPreferences.Editor editor = pref.edit();
                     editor.putString("Name",ename);
                     editor.putString("Email",eemail);
                     editor.apply();
                     startActivity(intent);
                     simpleProgressBar.setVisibility(View.VISIBLE);
                     setProgressValue(progress);
                     Intent i = new Intent(getApplicationContext(), MainActivity.class);
                     startActivity(i);
                     finish();

                 }
                 else {
                     Snackbar snackbar = Snackbar.make(login_btn, "Wrong email or password", Snackbar.LENGTH_LONG);
                     snackbar.show();
                 }
             }

             @Override
             public void onFailure(Call<Employee> call, Throwable t) {
                 Snackbar snackbar = Snackbar.make(login_btn, "Wrong email or password", Snackbar.LENGTH_LONG);
                 snackbar.show();
             }
         });
     }
}
