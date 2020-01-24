package com.example.food.Category;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class category_add_fragment extends Fragment {

    EditText category_name;
    Button btn_add, btn_cancel;
    public View view_red, view_pink, view_orange, view_green, view_blue, view_purple, view_darkgreen;
    int red_color, pink_color, orange_color, green_color, blue_color, purple_color, drakgreen_color;
    ImageView iv_tick,iv_tick1,iv_tick2,iv_tick3,iv_tick4,iv_tick5,iv_tick6;
    int colors, visibility = 0 ;

    APIInterface apiInterface;


    public category_add_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.category_add_fragment, container, false);

        category_name = (EditText) view.findViewById(R.id.category_name);
        btn_add = (Button) view.findViewById(R.id.add_category);


        view_red = (View) view.findViewById(R.id.colorbox1);
        view_pink = (View) view.findViewById(R.id.colorbox2);
        view_orange = (View) view.findViewById(R.id.colorbox3);
        view_green = (View) view.findViewById(R.id.colorbox4);
        view_blue = (View) view.findViewById(R.id.colorbox5);
        view_purple = (View) view.findViewById(R.id.colorbox6);
        view_darkgreen = (View) view.findViewById(R.id.colorbox7);

        iv_tick = (ImageView) view.findViewById(R.id.iv_tick);
        iv_tick1 = (ImageView) view.findViewById(R.id.iv_tick1);
        iv_tick2 = (ImageView) view.findViewById(R.id.iv_tick2);
        iv_tick3 = (ImageView) view.findViewById(R.id.iv_tick3);
        iv_tick4 = (ImageView) view.findViewById(R.id.iv_tick4);
        iv_tick5 = (ImageView) view.findViewById(R.id.iv_tick5);
        iv_tick6 = (ImageView) view.findViewById(R.id.iv_tick6);

        view_red.setOnClickListener(onClickListener);
        view_pink.setOnClickListener(onClickListener);
        view_orange.setOnClickListener(onClickListener);
        view_green.setOnClickListener(onClickListener);
        view_blue.setOnClickListener(onClickListener);
        view_purple.setOnClickListener(onClickListener);
        view_darkgreen.setOnClickListener(onClickListener);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String c_name = category_name.getText().toString();
                int c_color = colors;
                create_category(c_name,c_color);


            }
        });

        return view;
    }

    private void create_category(String c_name,int c_color){
    apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseBody> cat =apiInterface.create_category(c_name,c_color);
        cat.enqueue(new Callback<ResponseBody>() {
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){

                //for red color
                case R.id.colorbox1:
                    visibility = iv_tick.getVisibility();
                    iv_tick2.setVisibility(View.GONE);
                    iv_tick1.setVisibility(View.GONE);
                    iv_tick3.setVisibility(View.GONE);
                    iv_tick4.setVisibility(View.GONE);
                    iv_tick5.setVisibility(View.GONE);
                    iv_tick6.setVisibility(View.GONE);
                    if(visibility == View.VISIBLE)
                    {
                        iv_tick2.setVisibility(View.GONE);
                        iv_tick1.setVisibility(View.GONE);
                        iv_tick3.setVisibility(View.GONE);
                        iv_tick4.setVisibility(View.GONE);
                        iv_tick5.setVisibility(View.GONE);
                        iv_tick6.setVisibility(View.GONE);
                    }
                    else
                    {
                        iv_tick.setVisibility(View.VISIBLE);
                        colors= red_color = Color.RED;
                    }
                    break;

                //for pink color
                case R.id.colorbox2:
                    visibility = iv_tick1.getVisibility();
                    iv_tick.setVisibility(View.GONE);
                    iv_tick2.setVisibility(View.GONE);
                    iv_tick3.setVisibility(View.GONE);
                    iv_tick4.setVisibility(View.GONE);
                    iv_tick5.setVisibility(View.GONE);
                    iv_tick6.setVisibility(View.GONE);
                    if(visibility == View.VISIBLE)
                    {
                        iv_tick.setVisibility(View.GONE);
                        iv_tick2.setVisibility(View.GONE);
                        iv_tick3.setVisibility(View.GONE);
                        iv_tick4.setVisibility(View.GONE);
                        iv_tick5.setVisibility(View.GONE);
                        iv_tick6.setVisibility(View.GONE);
                    }
                    else
                    {
                        iv_tick1.setVisibility(View.VISIBLE);
                        colors =  pink_color = Color.rgb(96,24,75);
                    }
                    break;


                //For orange color
                case R.id.colorbox3:
                    visibility = iv_tick2.getVisibility();
                    iv_tick.setVisibility(View.GONE);
                    iv_tick1.setVisibility(View.GONE);
                    iv_tick3.setVisibility(View.GONE);
                    iv_tick4.setVisibility(View.GONE);
                    iv_tick5.setVisibility(View.GONE);
                    iv_tick6.setVisibility(View.GONE);
                    if(visibility == View.VISIBLE)
                    {
                        iv_tick.setVisibility(View.GONE);
                        iv_tick1.setVisibility(View.GONE);
                        iv_tick3.setVisibility(View.GONE);
                        iv_tick4.setVisibility(View.GONE);
                        iv_tick5.setVisibility(View.GONE);
                        iv_tick6.setVisibility(View.GONE);
                    }
                    else
                    {
                        iv_tick2.setVisibility(View.VISIBLE);
                        colors =  orange_color = Color.rgb(94,58,10);
                    }
                    break;

                //For green color
                case R.id.colorbox4:
                    visibility = iv_tick3.getVisibility();
                    iv_tick.setVisibility(View.GONE);
                    iv_tick1.setVisibility(View.GONE);
                    iv_tick2.setVisibility(View.GONE);
                    iv_tick4.setVisibility(View.GONE);
                    iv_tick5.setVisibility(View.GONE);
                    iv_tick6.setVisibility(View.GONE);
                    if(visibility == View.VISIBLE)
                    {
                        iv_tick.setVisibility(View.GONE);
                        iv_tick1.setVisibility(View.GONE);
                        iv_tick2.setVisibility(View.GONE);
                        iv_tick4.setVisibility(View.GONE);
                        iv_tick5.setVisibility(View.GONE);
                        iv_tick6.setVisibility(View.GONE);
                    }
                    else
                    {
                        iv_tick3.setVisibility(View.VISIBLE);
                        colors =  green_color = Color.rgb(85,87,17);
                    }
                    break;


                //For darkgreen color

                case R.id.colorbox5:
                    visibility = iv_tick4.getVisibility();
                    iv_tick.setVisibility(View.GONE);
                    iv_tick1.setVisibility(View.GONE);
                    iv_tick3.setVisibility(View.GONE);
                    iv_tick2.setVisibility(View.GONE);
                    iv_tick5.setVisibility(View.GONE);
                    iv_tick6.setVisibility(View.GONE);
                    if(visibility == View.VISIBLE)
                    {
                        iv_tick.setVisibility(View.GONE);
                        iv_tick1.setVisibility(View.GONE);
                        iv_tick3.setVisibility(View.GONE);
                        iv_tick2.setVisibility(View.GONE);
                        iv_tick5.setVisibility(View.GONE);
                        iv_tick6.setVisibility(View.GONE);
                    }
                    else
                    {
                        iv_tick4.setVisibility(View.VISIBLE);
                        colors =  drakgreen_color = Color.rgb(6,52,10);
                    }
                    break;

                //For blue color
                case R.id.colorbox6:
                    visibility = iv_tick5.getVisibility();
                    iv_tick.setVisibility(View.GONE);
                    iv_tick1.setVisibility(View.GONE);
                    iv_tick3.setVisibility(View.GONE);
                    iv_tick4.setVisibility(View.GONE);
                    iv_tick2.setVisibility(View.GONE);
                    iv_tick6.setVisibility(View.GONE);
                    if(visibility == View.VISIBLE)
                    {
                        iv_tick.setVisibility(View.GONE);
                        iv_tick1.setVisibility(View.GONE);
                        iv_tick3.setVisibility(View.GONE);
                        iv_tick4.setVisibility(View.GONE);
                        iv_tick2.setVisibility(View.GONE);
                        iv_tick6.setVisibility(View.GONE);
                    }
                    else
                    {
                        iv_tick5.setVisibility(View.VISIBLE);
                        colors =  blue_color = Color.rgb(37,38,85);
                    }
                    break;


                //For Purple color
                case R.id.colorbox7:
                    visibility = iv_tick6.getVisibility();
                    iv_tick.setVisibility(View.GONE);
                    iv_tick1.setVisibility(View.GONE);
                    iv_tick3.setVisibility(View.GONE);
                    iv_tick4.setVisibility(View.GONE);
                    iv_tick2.setVisibility(View.GONE);
                    iv_tick5.setVisibility(View.GONE);
                    if(visibility == View.VISIBLE)
                    {
                        iv_tick.setVisibility(View.GONE);
                        iv_tick1.setVisibility(View.GONE);
                        iv_tick3.setVisibility(View.GONE);
                        iv_tick4.setVisibility(View.GONE);
                        iv_tick2.setVisibility(View.GONE);
                        iv_tick5.setVisibility(View.GONE);
                    }
                    else
                    {
                        iv_tick6.setVisibility(View.VISIBLE);
                        colors =  purple_color = Color.rgb(65,3,68);
                    }
                    break;
            }
        }
    };

}
