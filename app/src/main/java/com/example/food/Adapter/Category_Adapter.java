package com.example.food.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Getter.Category;
import com.example.food.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.MYViewHolder> {
    private Context category_context;
    private List<Category> categoryList;

    public Category_Adapter(Context category_context, List<Category> categoryList) {
        this.category_context = category_context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_category,parent,false);
        return new MYViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_Adapter.MYViewHolder holder, int position) {
    Category category = categoryList.get(position);
    holder.category_name.setText(category.getC_name());
        Bitmap bmp= Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(category.getC_image());
        canvas.drawCircle(50, 50, 100, paint);
        holder.civ.setImageBitmap(bmp);

    //holder.civ.setsrc(category.getC_image());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MYViewHolder extends RecyclerView.ViewHolder {
        private TextView category_name;
        private CircleImageView civ;
        //private View view;

        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_view_name);
            civ = (CircleImageView) itemView.findViewById(R.id.category_list_image);
        }
    }
}
