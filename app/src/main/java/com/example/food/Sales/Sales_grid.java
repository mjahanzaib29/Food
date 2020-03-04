package com.example.food.Sales;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.Adapter.Category_Adapter;
import com.example.food.Adapter.Discount_Adapter2;
import com.example.food.Adapter.Product_Adapter1;
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

public class Sales_grid extends Fragment {

    private RecyclerView recyclerViewp,recyclerViewc,recyclerViewd ;
    private APIInterface apiInterface;
    private List<Product> productList;
    private Product_Adapter1 product_adapter;

    private List<Category> categoryList;
    private Category_Adapter category_adapter;

    private List<Discount> discountList;
    private Discount_Adapter2 discount_adapter;

    private GridLayoutManager gridLayoutManager;
    Spinner spinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.sales_grid, container, false);
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setHasOptionsMenu(true);

//        gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),SPAN_COUNT_THREE);
        recyclerViewp =(RecyclerView) root.findViewById(R.id.Sales_grid_recycle);
        recyclerViewc =(RecyclerView) root.findViewById(R.id.Sales_grid_recycle2);
        recyclerViewd =(RecyclerView) root.findViewById(R.id.Sales_grid_recycle3);
        spinner = (Spinner) root.findViewById(R.id.spinner_nav);

                //this show data in grid Edit spancount for more grids
//        recyclerViewp.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerViewp.setLayoutManager(gridLayoutManager);
//        int numberOfColumns = 5;
//        recyclerViewp.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
//        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);

//        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(getContext(), 200);
//        recyclerViewp.setLayoutManager(layoutManager);

        recyclerViewp.setLayoutManager(new AutoFitGridLayoutManager(getActivity().getApplicationContext(),250));
        recyclerViewc.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//        recyclerViewd.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerViewd.setLayoutManager(new AutoFitGridLayoutManager(getActivity().getApplicationContext(),250));
        recyclerViewp.setHasFixedSize(true);
        recyclerViewc.setHasFixedSize(true);
        recyclerViewd.setHasFixedSize(true);

//        FetchProducts("users", "");
        FetchCategory("users", "");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Selection = (String) parent.getItemAtPosition(position);
                if (Selection == "All products"){
                    FetchProducts("users", "");
                    recyclerViewd.setVisibility(View.GONE);
                    recyclerViewp.setVisibility(View.VISIBLE);

                }
                else if(Selection == "Discounts"){
                    FetchDiscount("users","");
                    recyclerViewd.setVisibility(View.VISIBLE);
                    recyclerViewp.setVisibility(View.GONE);
                }
                else if (Selection != "Discounts" && Selection != "All products"){
                    CatSearch("users", Selection);
                    Toast.makeText(getContext(), Selection, Toast.LENGTH_SHORT).show();
                    recyclerViewd.setVisibility(View.GONE);
                    recyclerViewp.setVisibility(View.VISIBLE);
                }

                return;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                FetchProducts("users","");
            }
        });

        return root;
    }

    //        PRODUCTS
    public void FetchProducts(String type, String key) {
        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<List<Product>> call = apiInterface.getproduct(type, key);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                if (!(productList == null)) {
                    product_adapter = new Product_Adapter1(getActivity().getBaseContext(), productList);
//                RecyclerView.ItemDecoration itemDecoration = new
//                        DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//                recyclerView.addItemDecoration(itemDecoration);
//                recyclerView.setItemAnimator(new SlideInUpAnimator());
                    recyclerViewp.setAdapter(product_adapter);
                    product_adapter.notifyDataSetChanged();
//                    Toast.makeText(getActivity(), "All Products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
//        Spinner Search by Category
        public void CatSearch(String type,String key){
        apiInterface = APIClient.getApiClient().create(APIInterface.class);
            Call<List<Product>> call = apiInterface.getSelected(type, key);
            call.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    productList = response.body();
                    if (!(productList == null)) {
                        product_adapter = new Product_Adapter1(getActivity().getApplicationContext(), productList);
//                RecyclerView.ItemDecoration itemDecoration = new
//                        DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//                recyclerView.addItemDecoration(itemDecoration);
//                recyclerView.setItemAnimator(new SlideInUpAnimator());
                        recyclerViewp.setAdapter(product_adapter);
                        product_adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Selected Category products", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                }
            });

        }

//        DISCOUNT
        public void FetchDiscount(String type, String key) {
            apiInterface = APIClient.getApiClient().create(APIInterface.class);
            Call<List<Discount>> calldis = apiInterface.getdiscount(type, key);
            calldis.enqueue(new Callback<List<Discount>>() {
                @Override
                public void onResponse(Call<List<Discount>> calldis, Response<List<Discount>> response) {
                    discountList = response.body();
                    if (!(discountList == null)) {
                        discount_adapter = new Discount_Adapter2(getActivity().getApplicationContext(), discountList);
                        recyclerViewd.setAdapter(discount_adapter);
                        discount_adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Discounts", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Discount>> calldis, Throwable t) {

                }
            });
        }



    //    Category
        public void FetchCategory(String type, String key){
            apiInterface = APIClient.getApiClient().create(APIInterface.class);
            Call<List<Category>> callcat = apiInterface.getcategory(type,key);
            callcat.enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> callcat, Response<List<Category>> response) {
                    categoryList = response.body();
                    if (!(categoryList == null)) {
    //                    category_adapter = new Category_Adapter(getActivity().getApplicationContext(), categoryList);
    //                    recyclerViewc.setAdapter(category_adapter);
//                        category_adapter.notifyDataSetChanged();
                        showListinSpinner();
    //                    Toast.makeText(getActivity(), "response triger", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> callcat, Throwable t) {
//                    Toast.makeText(getActivity(), "response not triger", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
            menu.clear();

            //        inflater.inflate(R.menu.meun_main, menu);
            inflater.inflate(R.menu.main, menu);

            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    FetchProducts("users", query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    FetchProducts("users", newText);
                    return false;
                }
            });

            super.onCreateOptionsMenu(menu, inflater);
        }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_switch_layout) {
//            switchLayout();
//            switchIcon(item);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void switchLayout() {
//        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_THREE) {
//            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
//        } else {
//            gridLayoutManager.setSpanCount(SPAN_COUNT_THREE);
//        }
//        product_adapter.notifyItemRangeChanged(0, product_adapter.getItemCount());
//    }
//
//    private void switchIcon(MenuItem item) {
//        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_THREE) {
//            item.setIcon(getResources().getDrawable(R.drawable.ic_span_3));
//        } else {
//            item.setIcon(getResources().getDrawable(R.drawable.ic_span_1));
//        }
//    }

            //WORK FOR DROPDOWN IN SPINNER
            private void showListinSpinner(){
            //String array to store all the book names
            String[] items = new String[categoryList.size()];
                int i=0;
                items[0] = "All products";
                items[1] = "Discounts";

            //Traversing through the whole list to get all the names
            for(i=2; i<categoryList.size(); i++){
                //Storing names to string array
                items[i] = categoryList.get(i).getC_name();
            }

            //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter = null;
            try {
                adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, items);
            }
            catch (Exception e){}

            //setting adapter to spinner

                spinner.setAdapter(adapter);
            //Creating an array adapter for list view
        }
        String Selection;
        private void SpinnerSelection(){

        }

}
