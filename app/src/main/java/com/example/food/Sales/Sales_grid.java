package com.example.food.Sales;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.Adapter.Category_Adapter;
import com.example.food.Adapter.Discount_Adapter;
import com.example.food.Adapter.Product_Adapter;
import com.example.food.Getter.Category;
import com.example.food.Getter.Discount;
import com.example.food.Getter.Product;
import com.example.food.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.food.Adapter.Product_Adapter.SPAN_COUNT_ONE;
import static com.example.food.Adapter.Product_Adapter.SPAN_COUNT_THREE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sales_grid extends Fragment {

    private RecyclerView recyclerViewp,recyclerViewc,recyclerViewd ;
    private APIInterface apiInterface;
    private List<Product> productList;
    private Product_Adapter product_adapter;

    private List<Category> categoryList;
    private Category_Adapter category_adapter;

    private List<Discount> discountList;
    private Discount_Adapter discount_adapter;

    private GridLayoutManager gridLayoutManager;

    public Sales_grid() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.sales_grid, container, false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setHasOptionsMenu(true);

        gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),SPAN_COUNT_ONE);

        recyclerViewp =(RecyclerView) root.findViewById(R.id.Sales_grid_recycle);
        recyclerViewc =(RecyclerView) root.findViewById(R.id.Sales_grid_recycle2);
        recyclerViewd =(RecyclerView) root.findViewById(R.id.Sales_grid_recycle3);
        //this show data in grid Edit spancount for more grids
//        recyclerViewp.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        recyclerViewp.setLayoutManager(gridLayoutManager);
//        int numberOfColumns = 5;
//        recyclerViewp.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
//        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(getContext(), 300);
        recyclerViewp.setLayoutManager(layoutManager);

//        recyclerViewp.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerViewc.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerViewd.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerViewp.setHasFixedSize(true);
        recyclerViewc.setHasFixedSize(true);
        recyclerViewd.setHasFixedSize(true);

        fetchallitems("users", "");
        return root;
    }

    public void fetchallitems(String type, String key){

//        PRODUCTS
        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<List<Product>> call= apiInterface.getproduct(type,key);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                if (!(productList == null)) {
                    product_adapter = new Product_Adapter(getActivity().getApplicationContext(), productList);
//                RecyclerView.ItemDecoration itemDecoration = new
//                        DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//                recyclerView.addItemDecoration(itemDecoration);
//                recyclerView.setItemAnimator(new SlideInUpAnimator());
                    recyclerViewp.setAdapter(product_adapter);
                    product_adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "response triger", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

//        CATEGORY

        Call<List<Category>> callcat = apiInterface.getcategory(type,key);
        callcat.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> callcat, Response<List<Category>> response) {
                categoryList = response.body();
                if (!(categoryList == null)) {
                    category_adapter = new Category_Adapter(getActivity().getApplicationContext(), categoryList);
                    recyclerViewc.setAdapter(category_adapter);
                    category_adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "response triger", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Category>> callcat, Throwable t) {
                Toast.makeText(getActivity(), "response not triger", Toast.LENGTH_SHORT).show();
            }
        });


//        DISCOUNT
        Call<List<Discount>> calldis = apiInterface.getdiscount(type, key);
        calldis.enqueue(new Callback<List<Discount>>() {
            @Override
            public void onResponse(Call<List<Discount>> calldis, Response<List<Discount>> response) {
                discountList = response.body();
                if (!(discountList == null)) {
                    discount_adapter = new Discount_Adapter(getActivity().getApplicationContext(), discountList);
                    recyclerViewd.setAdapter(discount_adapter);
                    discount_adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "response triger", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Discount>> calldis, Throwable t) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.meun_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_switch_layout) {
            switchLayout();
            switchIcon(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchLayout() {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_ONE) {
            gridLayoutManager.setSpanCount(SPAN_COUNT_THREE);
        } else {
            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
        }
        product_adapter.notifyItemRangeChanged(0, product_adapter.getItemCount());
    }

    private void switchIcon(MenuItem item) {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_THREE) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_3));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_1));
        }
    }

}
