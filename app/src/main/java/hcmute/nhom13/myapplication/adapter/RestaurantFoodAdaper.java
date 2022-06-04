package hcmute.nhom13.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.nhom13.myapplication.R;
import hcmute.nhom13.myapplication.model.CartFood;
import hcmute.nhom13.myapplication.model.PopularFood;

public class RestaurantFoodAdaper extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<PopularFood> listFood;

    public RestaurantFoodAdaper(Context context, int layout, List<PopularFood> listFood) {
        this.context = context;
        this.layout = layout;
        this.listFood = listFood;
    }

    @Override
    public int getCount() {
        return listFood.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTitle,txtPrice;
        ImageView imgFood;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            holder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            //Anh xa
            holder.txtTitle = view.findViewById(R.id.name);
            holder.txtPrice = view.findViewById(R.id.price);


            holder.imgFood = view.findViewById(R.id.food_image);


            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        PopularFood popularFood = listFood.get(i);
        holder.txtTitle.setText(popularFood.getName());
        holder.txtPrice.setText("$"+popularFood.getPrice());
        Bitmap bitmap = BitmapFactory.decodeByteArray(popularFood.getImageUrl(),0,popularFood.getImageUrl().length);
        holder.imgFood.setImageBitmap(bitmap);

        return view;
    }
}
