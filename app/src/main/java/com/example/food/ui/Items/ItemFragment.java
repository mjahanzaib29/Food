package com.example.food.ui.Items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.food.I_details;
import com.example.food.I_navigation;
import com.example.food.R;

public class ItemFragment extends Fragment {

    private ItemViewModel itemViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item, container, false);

        I_navigation i_navigation = new I_navigation();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.listofitem, i_navigation);
        transaction.commit();

        I_details i_details = new I_details();
        FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
        transaction1.replace(R.id.recylerview,i_details);
        transaction1.commit();

        return root;
    }
}