package com.example.food.Discount;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.example.food.Adapter.Discount_Adapter;
import com.example.food.Getter.Discount;
import com.example.food.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiscountFragment extends Fragment {
    private RecyclerView recyclerView;
    private APIInterface apiInterface;
    private List<Discount> discountList;
    private Discount_Adapter discount_adapter;
    Button discount_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discount, container, false);
        setHasOptionsMenu(true);
        discount_btn = (Button) view.findViewById(R.id.discount_add);
        recyclerView =(RecyclerView) view.findViewById(R.id.recyclerview_discount);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        fetchContact("users", "");

            discount_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    discount_add_fragment daf = new discount_add_fragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.main, daf);
                    transaction.commit();
                }
            });

        return view;
    }

    public void fetchContact(String type,String key) {
        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<List<Discount>> call = apiInterface.getdiscount(type, key);
        call.enqueue(new Callback<List<Discount>>() {
            @Override
            public void onResponse(Call<List<Discount>> call, Response<List<Discount>> response) {
                discountList = response.body();
                if (!(discountList == null)) {
                    discount_adapter = new Discount_Adapter(getActivity(), discountList);
                    RecyclerView.ItemDecoration itemDecoration = new
                            DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(itemDecoration);
                    recyclerView.setAdapter(discount_adapter);
                    discount_adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "response triger", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Discount>> call, Throwable t) {

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
                fetchContact("users", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchContact("users", newText);
                return false;
            }
        });
        return ;
    }



}
