package hcmute.nhom13.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import hcmute.nhom13.myapplication.DetailsActivity;
import hcmute.nhom13.myapplication.MainActivity;
import hcmute.nhom13.myapplication.R;
import hcmute.nhom13.myapplication.model.PopularFood;


public class PopularFoodAdapter extends RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder> {
    Context context;
    List<PopularFood> popularFoodList;

    public PopularFoodAdapter(Context context, List<PopularFood> popularFoodList) {
        this.context = context;
        this.popularFoodList = popularFoodList;
    }
    @NonNull
    @Override
    public PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.popular_food_row_item, parent, false);
        return new PopularFoodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PopularFoodViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(popularFoodList.get(position).getImageUrl(),0,popularFoodList.get(position).getImageUrl().length);
        holder.foodImage.setImageBitmap(bitmap);
        holder.name.setText(popularFoodList.get(position).getName());
        holder.price.setText("$"+popularFoodList.get(position).getPrice().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("objectPopularFood",popularFoodList.get(position));
                //i.putExtra("imagess",popularFoodList.get(position).getImageUrl());
                //Log.d("sssimage",popularFoodList.get(position).getImageUrl().toString());
                context.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return popularFoodList.size();
    }
    public static final class PopularFoodViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView price, name;

        public PopularFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);

        }
    }

}
