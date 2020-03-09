package com.example.food.Receipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.Getter.Receipt;
import com.example.food.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Receipts extends Fragment {
    private RecyclerView receipt_recyclerView;
    private APIInterface apiInterface;
    private List<Receipt> receiptList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.receipts,container,false);
        receipt_recyclerView = (RecyclerView) view.findViewById(R.id.receipts_recyclerview);
        receipt_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        receipt_recyclerView.setHasFixedSize(true);
        return view;
    }

    public void fetchReceipts(){
        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<List<Receipt>> callreceipt = apiInterface.get_receipt();
        callreceipt.enqueue(new Callback<List<Receipt>>() {
            @Override
            public void onResponse(Call<List<Receipt>> call, Response<List<Receipt>> response) {
                receiptList = response.body();
                if (receiptList !=null){

                }
            }

            @Override
            public void onFailure(Call<List<Receipt>> call, Throwable t) {

            }
        });
    }
}
