package hcmute.nhom13.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import hcmute.nhom13.myapplication.R;
import hcmute.nhom13.myapplication.model.CartFood;
import hcmute.nhom13.myapplication.model.PopularFood;

public class CartPaymentAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<CartFood> listFood;

    public CartPaymentAdapter(Context context, int layout, List<CartFood> listFood) {
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
        TextView txtTitle,txtPrice,txtQuantity;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            holder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            //Anh xa
            holder.txtTitle = view.findViewById(R.id.textViewName_PaymentAdapter);
            holder.txtPrice = view.findViewById(R.id.textViewPrice_PaymentAdapter);


            holder.txtQuantity = view.findViewById(R.id.textViewQuantity_PaymentAdapter);


            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        CartFood popularFood = listFood.get(i);
        holder.txtTitle.setText(popularFood.getName());
        Double total = phepTinhNhan(popularFood.getPrice(), popularFood.getQuantity());
        holder.txtPrice.setText("$"+total);
        holder.txtQuantity.setText("X"+popularFood.getQuantity());

        return view;
    }

    private Double phepTinhCong(Double price,Double price2){
        BigDecimal b1 = new BigDecimal(price);
        BigDecimal b2 = new BigDecimal(price2);
        BigDecimal resMul = b1.add(b2, MathContext.DECIMAL32);
        return Double.parseDouble(String.valueOf(resMul));
    }

    private Double phepTinhNhan(Double price,int quantity){
        BigDecimal b1 = new BigDecimal(price);
        BigDecimal b2 = new BigDecimal(quantity);
        BigDecimal resMul = b1.multiply(b2, MathContext.DECIMAL32);
        return Double.parseDouble(String.valueOf(resMul));
    }
}
