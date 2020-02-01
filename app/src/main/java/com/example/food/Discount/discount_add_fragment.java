package com.example.food.Discount;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.R;
import com.example.food.ui.Items.ItemFragment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class discount_add_fragment extends Fragment {

    APIInterface apiInterface;
    EditText dis_name,dis_value;
    Switch dis_type;
    public  String type_dis = "%", name_dis = "" , value_dis = "" ;
    Button add_discount, cancel_discount;


    public discount_add_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.discount_add_fragment, container, false);
        dis_name = (EditText) view.findViewById(R.id.discount_name);
        dis_value = (EditText) view.findViewById(R.id.discount_value);
        dis_type = (Switch) view.findViewById(R.id.switcher);
        add_discount = (Button) view.findViewById(R.id.add_discount);
        cancel_discount = (Button) view.findViewById(R.id.cancel_discount);

        dis_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                if(isChecked){
                    type_dis = "Price";
                    Toast.makeText(getActivity(), type_dis, Toast.LENGTH_SHORT).show();
                }else{
                    type_dis = "%";
                    Toast.makeText(getActivity(), type_dis, Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_dis = dis_name.getText().toString();
                value_dis = (dis_value.getText().toString());
                String name = name_dis;
                String value = value_dis;
                create_discount(name, value,type_dis);

            }
        });
        cancel_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemFragment itemFragment = new ItemFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main,itemFragment);
                transaction.commit();
            }
        });

        return view;
    }

    private void create_discount(String name,String value,String type){

        apiInterface = APIClient.getApiClient().create(APIInterface.class);

        Call<ResponseBody> create_dis= apiInterface.create_discount(name, value, type);
        create_dis.enqueue(new Callback<ResponseBody>() {
            @Override

            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
