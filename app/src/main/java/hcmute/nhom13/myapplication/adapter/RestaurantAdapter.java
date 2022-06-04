package hcmute.nhom13.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import hcmute.nhom13.myapplication.DetailsActivity;
import hcmute.nhom13.myapplication.R;
import hcmute.nhom13.myapplication.RestaurantActivity;
import hcmute.nhom13.myapplication.model.Restaurant;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.AsiaFoodViewHolder> {

    Context context;
    List<Restaurant> restaurantList;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public AsiaFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_row_item, parent, false);
        return new AsiaFoodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(AsiaFoodViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(restaurantList.get(position).getImageUrl(),0,restaurantList.get(position).getImageUrl().length);
        holder.foodImage.setImageBitmap(bitmap);
        holder.name.setText(restaurantList.get(position).getName());
        holder.price.setText(restaurantList.get(position).getPrice()+"km");
        holder.rating.setText(restaurantList.get(position).getRating());
        holder.restorantName.setText(restaurantList.get(position).getRestorantname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, RestaurantActivity.class);
                i.putExtra("idRestaurant",restaurantList.get(position).getId());
                i.putExtra("nameRestaurant",restaurantList.get(position).getName());
                i.putExtra("addressRestaurant",restaurantList.get(position).getAddress());
                i.putExtra("distanceRestaurant",restaurantList.get(position).getDistance()+"km");
                i.putExtra("ratingRestaurant",restaurantList.get(position).getRating());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }


    public static final class AsiaFoodViewHolder extends RecyclerView.ViewHolder{


        ImageView foodImage;
        TextView price, name, rating, restorantName;

        public AsiaFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.food_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            restorantName = itemView.findViewById(R.id.restorant_name);



        }
    }

}
