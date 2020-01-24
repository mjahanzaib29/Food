package com.example.food.Products;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.food.APIClient;
import com.example.food.APIInterface;
import com.example.food.Getter.Category;
import com.example.food.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class product_add_fragment extends Fragment {

    RadioGroup radioGroup,radioGroup2;
    HorizontalScrollView scrollView;
    LinearLayout image_layout, enter_Stock,enter_expiry;
    ImageView item_imageview;
    public static EditText barcode;
    RadioButton radioButtoncolor;
    int checkedRadio;
    Button ButtonInvisible, item_choosephoto,item_takephoto,item_add;
    Switch stock_switch,stock_expiry;
    EditText item_name,item_price,item_cost,item_serial,item_barcode,item_stock,item_expiry;
    Spinner item_category;
    String item_soldby = "Each";
    private static int RESULT_LOAD_IMAGE = 1;
    final static int TAKE_PICTURE = 2;
    APIInterface apiInterface;
    private Bitmap bitmap;
    String image = "";
    int colors, visibility = 0 ;


    public product_add_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.product_add_fragment, container, false);
        item_choosephoto =(Button) root.findViewById(R.id.item_choosephoto);
        item_takephoto = (Button) root.findViewById(R.id.item_takephoto);
        item_imageview =(ImageView) root.findViewById(R.id.item_imageview);
        item_name = (EditText) root.findViewById(R.id.item_name);
        item_category = (Spinner) root.findViewById(R.id.item_category);
        item_price = (EditText) root.findViewById(R.id.item_price) ;
        item_cost = (EditText) root.findViewById(R.id.item_Cost);
        item_serial = (EditText) root.findViewById(R.id.item_serialno);
        item_barcode =(EditText) root.findViewById(R.id.item_barcode) ;
        item_stock = (EditText) root.findViewById(R.id.item_stock);
        item_expiry = (EditText) root.findViewById(R.id.item_expiry);

        fetchCategoryName();

        item_choosephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        item_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });

        item_add =(Button) root.findViewById(R.id.item_add);
        scrollView = (HorizontalScrollView) root.findViewById(R.id.color_scroll);
        image_layout = (LinearLayout) root.findViewById(R.id.image_layout);
        enter_Stock = (LinearLayout) root.findViewById(R.id.Enter_stock);
        enter_expiry = (LinearLayout) root.findViewById(R.id.Enter_expiry);
//        ButtonInvisible = (Button) root.findViewById(R.id.button1);
//        ButtonInvisible.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                barcodescaner bsf = new barcodescaner();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.item_fragment, bsf);
//                transaction.commit();
//            }
//        });

        radioGroup = (RadioGroup) root.findViewById(R.id.color_image);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                checkedRadio= radioGroup.getCheckedRadioButtonId();
                radioButtoncolor = getView().findViewById(checkedRadio);

                if(radioButtoncolor.getId() == R.id.color){
                    image_layout.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }else if(radioButtoncolor.getId() == R.id.imagerb){
                    scrollView.setVisibility(View.GONE);
                    image_layout.setVisibility(View.VISIBLE);
                }
            }
        });

//        item_soldby = (TextView) root.findViewById(R.id.item_soldby);
        radioGroup2 = (RadioGroup) root.findViewById(R.id.RadioGroup_sortby);
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                checkedRadio= radioGroup2.getCheckedRadioButtonId();
                radioButtoncolor = getView().findViewById(checkedRadio);

                if(radioButtoncolor.getId() == R.id.each){
                    item_soldby=("Each");

                }
                else if(radioButtoncolor.getId() == R.id.weight){
                    item_soldby=("Weight");

                }

            }
        });

        stock_switch = (Switch) root.findViewById(R.id.stock_switch);
        stock_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    enter_Stock.setVisibility(View.VISIBLE);

                }
                else {
                    enter_Stock.setVisibility(View.GONE);

                }

            }
        });
        stock_expiry = (Switch) root.findViewById(R.id.stock_expiry);
        stock_expiry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    enter_expiry.setVisibility(View.VISIBLE);

                }
                else {
                    enter_expiry.setVisibility(View.GONE);

                }

            }
        });

        item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i_name,i_category,i_soldby,i_price,i_cost,i_serial,i_barcode,i_stock,i_color,i_image;
                i_name = item_name.getText().toString();
                i_category = item_category.getSelectedItem().toString();
                i_soldby = item_soldby;
                i_price = item_price.getText().toString();
                i_cost = item_cost.getText().toString();
                i_serial = item_serial.getText().toString();
                i_barcode = item_barcode.getText().toString();
                i_stock = item_stock.getText().toString();
                //i_color = item_color.getText().toString();
                int c_color = colors;
//                i_image = item_imageview.toString();
//                image  = imageToString();

                post_product(i_name,i_category,i_soldby,i_price,i_cost,i_serial,i_barcode,i_stock,c_color);
//                post_product(i_name,i_category,i_soldby,i_price,i_cost,i_serial,i_barcode,i_stock);
              //  post_product(i_name,i_soldby,i_price,i_cost,i_serial,i_barcode,i_stock);

            }
        });

        return root;
    }


    //WORK FOR POSTING ITEM
    //    private void post_product(String i_name,String i_category,String i_sortby,String i_price,String i_cost,String i_serial,String i_barcode,String i_stock,String i_color,String i_image)



    private void post_product(String i_name, String i_category, String i_soldby, String i_price, String i_cost, String i_serial, String i_barcode, String i_stock, int i_color){
        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        Call<ResponseBody> uploadobjectCall = apiInterface.post_product(i_name,i_category,i_soldby,i_price,i_cost,i_serial,i_barcode,i_stock,i_color,fileToUpload, filename);
//
//
//
//        Call<ResponseBody> uploadobjectCall = apiInterface.post_product(i_name,i_category,i_sortby,i_price,i_cost,i_serial,i_barcode,i_stock,i_color,i_image);

        uploadobjectCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(), "Trigger", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Not Trigger", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String mediaPath;
    //For Gallery Open
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                item_imageview.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//    public String getRealPathFromURI(Uri uri) {
//        String path = "";
//        if (getActivity().getContentResolver() != null) {
//            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                path = cursor.getString(idx);
//                cursor.close();
//            }
//        }
//        return path;
//    }

//    private String imageToString(){
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//        byte[] imgbyts = byteArrayOutputStream.toByteArray();
//        return Base64.encodeToString(imgbyts,Base64.DEFAULT);
//    }

    private List<Category> categoryList;


    private void fetchCategoryName(){

        apiInterface = APIClient.getApiClient().create(APIInterface.class);
        String type = null,key = null;
        Call<List<Category>> call = apiInterface.getcategory(type,key);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categoryList = response.body();
                showListinSpinner();
                Toast.makeText(getActivity(), "Category get", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), "response not get", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showListinSpinner(){
        //String array to store all the book names
        String[] items = new String[categoryList.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<categoryList.size(); i++){
            //Storing names to string array
            items[i] = categoryList.get(i).getC_name();
        }

        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        item_category.setAdapter(adapter);
        //Creating an array adapter for list view

    }


//    // FOR CAMERA ACCESS WORK
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_PERMISSION_CODE)
//        {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            }
//            else
//            {
//                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

//    @Override
//    public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//// CAMERA
//        if (reqCode == CAMERA_REQUEST && resultCode == RESULT_OK)
//        {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            item_imageview.setImageBitmap(photo);
//        }
//
////GALLERY
//        if (resultCode == RESULT_OK) {
//            try {
//                final Uri imageUri = data.getData();
//                if(imageUri != null){
//                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
//                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                    item_imageview.setImageBitmap(selectedImage);
//                }
//            }
//            catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//
//        }else {
//            Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
//        }
//    }



}
