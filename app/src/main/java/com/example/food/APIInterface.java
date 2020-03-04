package com.example.food;

import com.example.food.Getter.Category;
import com.example.food.Getter.Customer;
import com.example.food.Getter.Discount;
import com.example.food.Getter.Employee;
import com.example.food.Getter.Product;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIInterface {

//    @FormUrlEncoded
//    @POST("posst_item.php")
//    Call<ResponseBody> Post_product(
//            @Field("i_name") String i_name,
//           // @Field("i_image") String i_image,
//            @Field("i_category") String i_category,
//            @Field("i_soldby") String i_soldby,
//            @Field("i_price") String i_price,
//            @Field("i_cost") String i_cost,
//            @Field("i_serial") String i_serial,
//            @Field("i_barcode") String i_barcode,
//            @Field("i_stock") String i_stock
//    );

//    @POST("post_item.php")
//    Call<ResponseBody> post_product(
//            @Query("i_name") String i_name,
//            @Query("i_category") String i_category,
//            @Query("i_soldby") String i_soldby,
//            @Query("i_price") String i_price,
//            @Query("i_cost") String i_cost,
//            @Query("i_serial") String i_serial,
//            @Query("i_barcode") String i_barcode,
//            @Query("i_stock") String i_stock
//            @Query("i_color")
//            String i_color,

//            @Part MultipartBody.Part file,
//            @Part("fileToUpload") RequestBody name

//            @Query("i_image")
//            String i_image
//    );


    @Multipart
    @POST("post_item.php")
    Call<ResponseBody> post_product(
            @Query("i_name") String i_name,
            @Query("i_category") String i_category,
            @Query("i_soldby") String i_soldby,
            @Query("i_price") String i_price,
            @Query("i_cost") String i_cost,
            @Query("i_serial") String i_serial,
            @Query("i_barcode") String i_barcode,
            @Query("i_stock") String i_stock,
            @Query("i_color") int i_color,
            @Part MultipartBody.Part file,
            @Part("file") RequestBody name
    );
    @POST("add_customer.php")
    Call<ResponseBody> create_customer(
            @Query("cu_name") String c_name,
            @Query("cu_email") String c_email,
            @Query("cu_number") String c_number,
            @Query("cu_note") String c_note,
            @Query("cu_created") String c_created
            );

    @GET("get_customer.php")
    Call<List<Customer>>getcustomer(
    );


    @GET("get_category.php")
    Call<List<Category>>getcategory(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );

    @POST("post_category.php")
    Call<ResponseBody> create_category(
            @Query("c_name") String name,
            @Query("c_image") int c_color);

    @GET("get_product.php")
    Call<List<Product>>getproduct(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );

    @GET("get_Filtered_Product.php")
    Call<List<Product>>getSelected(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );

    @POST("post_discount.php")
    Call<ResponseBody> create_discount(
            @Query("dis_name") String dis_name,
            @Query("dis_value")String dis_value,
            @Query("dis_type") String dis_type);

    @GET("get_discount.php")
    Call<List<Discount>>getdiscount(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );

    @POST("edit_customer.php")
    Call<ResponseBody> editcustomer(
            @Query("cu_id") int id,
            @Query("cu_name") String name,
            @Query("cu_email") String email,
            @Query("cu_number") String number,
            @Query("cu_note") String note
    );

    @GET("get_employee.php")
    Call<Employee> get_employee(
            @Query("email") String email,
            @Query("pass") String pass
    );
}
