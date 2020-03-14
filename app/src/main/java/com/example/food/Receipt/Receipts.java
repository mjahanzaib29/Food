package com.example.food.Receipt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.Adapter.Receipt_Adapter;
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
    Receipt_Adapter receipt_adapter;
    String received_receiptid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.receipts,container,false);
        receipt_recyclerView = (RecyclerView) view.findViewById(R.id.receipts_recyclerview);
        receipt_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        receipt_recyclerView.setHasFixedSize(true);
        fetchReceipts();
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
                    receipt_adapter = new Receipt_Adapter(getActivity(), receiptList);
                    RecyclerView.ItemDecoration itemDecoration = new
                            DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                    receipt_recyclerView.addItemDecoration(itemDecoration);
                    receipt_recyclerView.setAdapter(receipt_adapter);
                    receipt_adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "response triger", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Receipt>> call, Throwable t) {

            }
        });
    }

    public BroadcastReceiver receiptid = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            received_receiptid = intent.getStringExtra("receipt_id");
//            fetchReceipts(received_receiptid);
        }
    };
}
