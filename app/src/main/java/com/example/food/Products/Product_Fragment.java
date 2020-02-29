package com.example.food.Products;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.Adapter.Product_Adapter2;
import com.example.food.Getter.Product;
import com.example.food.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Product_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private APIInterface apiInterface;
    private List<Product> productList;
    private Product_Adapter2 product_adapter;
    private GridLayoutManager gridLayoutManager;
    FloatingActionButton product_add;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
//        IT CALLS SEARCH OPTION
        setHasOptionsMenu(true);
        product_add = (FloatingActionButton) view.findViewById(R.id.product_add);
        recyclerView =(RecyclerView) view.findViewById(R.id.recylerview_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
//        IT PASSES WHAT TO SEARCH AND USER ENTERED QUERY
        fetchfilteredproducts("users", "");

        product_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_add_fragment paf = new product_add_fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main, paf);
                transaction.commit();

            }
        });

        return view;
    }

    public void fetchfilteredproducts(String type, String key){
        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<List<Product>> call= apiInterface.getproduct(type,key);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                if (!(productList == null)) {
                    product_adapter = new Product_Adapter2(getActivity().getApplicationContext(), productList);
                RecyclerView.ItemDecoration itemDecoration = new
                        DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(itemDecoration);
//                recyclerView.setItemAnimator(new SlideInUpAnimator());
                    recyclerView.setAdapter(product_adapter);
                    product_adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "response triger", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();

        // Inflate the menu; this adds items to the action bar if it is present.
        //MenuInflater inflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,menuInflater);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchfilteredproducts("users", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchfilteredproducts("users", newText);
                return false;
            }
        });
        return ;
    }

}
