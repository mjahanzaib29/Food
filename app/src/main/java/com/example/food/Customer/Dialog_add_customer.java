package com.example.food.Customer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.Adapter.Customer_Adapter;
import com.example.food.Getter.Customer;
import com.example.food.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dialog_add_customer extends DialogFragment {
    Button cancel;
    TextView add_customer;
    RecyclerView recyclerView;
    private APIInterface apiInterface;
    private List<Customer> customerList;
    Customer_Adapter customer_adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_customer,container,false);
        cancel = (Button) v.findViewById(R.id.cancel_customer);
        add_customer = (TextView) v.findViewById(R.id.add_customer_btn);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_customer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<List<Customer>> call= apiInterface.getcustomer();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                customerList = response.body();
                if (!(customerList == null)) {
                    customer_adapter = new Customer_Adapter(getActivity().getApplicationContext(), customerList);
                    RecyclerView.ItemDecoration itemDecoration = new
                        DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(itemDecoration);
                    recyclerView.setAdapter(customer_adapter);
                    customer_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_single_customer dialog_add_customer = new Dialog_single_customer();
                if (dialog_add_customer!=null) {
                    dialog_add_customer.show(getFragmentManager(), "Dialog_add_customer");
                }
            }
        });

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<List<Customer>> call= apiInterface.getcustomer();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                customerList = response.body();
                if (!(customerList == null)) {
                    customer_adapter = new Customer_Adapter(getActivity().getApplicationContext(), customerList);
                    RecyclerView.ItemDecoration itemDecoration = new
                            DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(itemDecoration);
                    recyclerView.setAdapter(customer_adapter);
                    customer_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
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
}
