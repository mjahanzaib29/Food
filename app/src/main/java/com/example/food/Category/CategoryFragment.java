package com.example.food.Category;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.food.Adapter.Category_Adapter;
import com.example.food.Getter.Category;
import com.example.food.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private APIInterface apiInterface;
    private List<Category> categoryList;
    private Category_Adapter category_adapter;
    Button category_add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        setHasOptionsMenu(true);
        category_add = (Button) view.findViewById(R.id.category_add);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        fetchfilteredcategory("users", "");


        category_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_add_fragment caf = new category_add_fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // CHANGE HOW ADD CATEGORY SHOWS .. JUST CHANGE main
                transaction.replace(R.id.main, caf);
                transaction.commit();
            }
        });


        return view;
    }

    public void fetchfilteredcategory(String type,String key) {
        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<List<Category>> call = apiInterface.getcategory(type,key);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categoryList = response.body();
                if (!(categoryList == null)) {
                    category_adapter = new Category_Adapter(getActivity().getApplicationContext(), categoryList);
                    recyclerView.setAdapter(category_adapter);
                    category_adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "response triger", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), "response not triger", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
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


                fetchfilteredcategory("users", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchfilteredcategory("users", newText);
                return false;
            }
        });
        return ;
    }

}
