package com.example.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.food.Category.CategoryFragment;
import com.example.food.Discount.DiscountFragment;
import com.example.food.Products.Product_Fragment;


public class I_navigation extends Fragment {

    Button item_btn, category_btn, discount_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_i_navigation, container, false);

        item_btn = (Button) view.findViewById(R.id.btn_item);
        category_btn = (Button) view.findViewById(R.id.btn_category);
        discount_btn = (Button) view.findViewById(R.id.btn_Discount);

        Product_Fragment product_fragment = new Product_Fragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.recylerview, product_fragment);
        transaction.commit();

        item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Product_Fragment product_fragment = new Product_Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.recylerview, product_fragment);
                transaction.commit();
            }
        });

        category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryFragment categoryFragment = new CategoryFragment();
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                transaction1.replace(R.id.recylerview, categoryFragment);
                transaction1.commit();

            }
        });

        discount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscountFragment discountFragment = new DiscountFragment();
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                transaction2.replace(R.id.recylerview, discountFragment);
                transaction2.commit();
            }
        });

        return view;
    }

}
