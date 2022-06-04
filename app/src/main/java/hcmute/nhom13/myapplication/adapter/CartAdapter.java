package hcmute.nhom13.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import hcmute.nhom13.myapplication.CartActivity;
import hcmute.nhom13.myapplication.IntroActivity;
import hcmute.nhom13.myapplication.MainActivity;
import hcmute.nhom13.myapplication.R;
import hcmute.nhom13.myapplication.model.CartFood;

public class CartAdapter extends BaseAdapter {
    private CartActivity context;
    private  int layout;
    private List<CartFood> listFood;

    public CartAdapter(CartActivity context, int layout, List<CartFood> listFood) {
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
        TextView txtTitle,txtPrice,txtTotal,txtQuantity;
        ImageView imgPlus,imgMinus,imgFood,imgDelete;
        CheckBox ckbEnbale;
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
            holder.txtTitle = view.findViewById(R.id.textViewTitle);
            holder.txtPrice = view.findViewById(R.id.textViewPrice);
            holder.txtTotal = view.findViewById(R.id.textViewTotal);
            holder.txtQuantity = view.findViewById(R.id.textViewQuantity);

            holder.imgFood = view.findViewById(R.id.imageViewFood);
            holder.imgPlus = view.findViewById(R.id.imageViewPlus);
            holder.imgMinus = view.findViewById(R.id.imageViewMinus);
            holder.imgDelete = view.findViewById(R.id.imageViewDelete);
            holder.ckbEnbale = view.findViewById(R.id.checkBoxCart);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        CartFood cartFood = listFood.get(i);
        Bitmap bitmap = BitmapFactory.decodeByteArray(cartFood.getImage(),0,cartFood.getImage().length);
        holder.txtTitle.setText(cartFood.getName());
        holder.txtPrice.setText("$"+cartFood.getPrice());
        Double total = phepTinh(cartFood.getPrice(),cartFood.getQuantity());
        holder.txtTotal.setText("$"+total);
        holder.txtQuantity.setText(String.valueOf(cartFood.getQuantity()));
        holder.imgFood.setImageBitmap(bitmap);


        holder.ckbEnbale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cartFood.setCheck(true);
                }
                else{
                    cartFood.setCheck(false);
                }

                context.setTextViewTotal();
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DeleteFoodToCart(cartFood.getId());
            }
        });

        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                int newQuantity = Integer.parseInt(holder.txtQuantity.getText().toString()) +1;
                context.UpdateQuantity(cartFood,newQuantity);
                holder.txtQuantity.setText(String.valueOf(cartFood.getQuantity()));
                Double total = phepTinh(cartFood.getPrice(),newQuantity);
                holder.txtTotal.setText("$"+total);
                context.getCart();
                context.setTextViewTotal();

            }
        });

        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(holder.txtQuantity.getText().toString()) >1) {
                    int newQuantity = Integer.parseInt(holder.txtQuantity.getText().toString()) - 1;
                    context.UpdateQuantity(cartFood, newQuantity);
                    holder.txtQuantity.setText(String.valueOf(cartFood.getQuantity()));
                    Double total = phepTinh(cartFood.getPrice(), newQuantity);
                    holder.txtTotal.setText("$" + total);
                    context.getCart();
                    context.setTextViewTotal();
                }
            }
        });


        return view;
    }

    private Double phepTinh(Double price,int quantity){
        BigDecimal b1 = new BigDecimal(price);
        BigDecimal b2 = new BigDecimal(quantity);
        BigDecimal resMul = b1.multiply(b2, MathContext.DECIMAL32);
        return Double.parseDouble(String.valueOf(resMul));
    }
}
