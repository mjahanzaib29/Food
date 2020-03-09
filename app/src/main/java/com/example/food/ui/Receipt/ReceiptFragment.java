package com.example.food.ui.Receipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.food.R;
import com.example.food.Receipt.Receipts;

public class ReceiptFragment extends Fragment {

    private ReceiptViewModel receiptViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        receiptViewModel =
                ViewModelProviders.of(this).get(ReceiptViewModel.class);
        View root = inflater.inflate(R.layout.fragment_receipt, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        receiptViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        Receipts receipts = new Receipts();
        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
        transaction1.replace(R.id.receipt_fragment_container, receipts);
        transaction1.commit();


        return root;
    }
}