package com.example.food.ui.Sales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.food.R;
import com.example.food.Sales.Sales_grid;
import com.example.food.Sales.Ticket;

public class SalesFragment extends Fragment {

    private SalesViewModel salesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        salesViewModel =
                ViewModelProviders.of(this).get(SalesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sales, container, false);

        Sales_grid sg = new Sales_grid();
        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
        transaction1.replace(R.id.listofall,sg);
        transaction1.commit();

        Ticket ticket = new Ticket();
        FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
        transaction1.replace(R.id.ticket,ticket);
        transaction2.commit();

        return root;
    }

}