package hcmute.nhom13.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import hcmute.nhom13.myapplication.CartActivity;
import hcmute.nhom13.myapplication.R;
import hcmute.nhom13.myapplication.model.Bill;
import hcmute.nhom13.myapplication.model.CartFood;

public class BillAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<Bill> listBill;

    public BillAdapter(Context context, int layout, List<Bill> listBill) {
        this.context = context;
        this.layout = layout;
        this.listBill = listBill;
    }

    @Override
    public int getCount() {
        return listBill.size();
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
        TextView txtTime,txtDate,txtTotal;
        ImageView imgImage,imgDone,imgFailed;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view ==null){
            holder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            //Anh xa
            holder.txtTime = view.findViewById(R.id.textViewTime);
            holder.txtDate = view.findViewById(R.id.textViewDate);
            holder.txtTotal = view.findViewById(R.id.textViewTotal_Bill);

            holder.imgImage = view.findViewById(R.id.imageView17);
            holder.imgDone = view.findViewById(R.id.imageViewDone);
            holder.imgFailed = view.findViewById(R.id.imageViewFailed);


            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        Bill bill =listBill.get(i);
        if(bill.getStatus().equals("done")){
            holder.imgImage.setImageResource(R.drawable.image_done);
            holder.imgDone.setVisibility(View.VISIBLE);
            holder.imgFailed.setVisibility(View.GONE);
        }else if(bill.getStatus().equals("failed")){
            holder.imgImage.setImageResource(R.drawable.draft);
            holder.imgDone.setVisibility(View.GONE);
            holder.imgFailed.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgImage.setImageResource(R.drawable.shipper);
            holder.imgDone.setVisibility(View.GONE);
            holder.imgFailed.setVisibility(View.GONE);
        }

        holder.txtTime.setText(bill.getTime());
        holder.txtDate.setText(bill.getDate());
        //Double totol = bill.getPrice()+bill.getFee()+bill.getVoucher();
        Double a = phepTinhCong(bill.getPrice(),bill.getFee());
        Double total = phepTinhCong(a,bill.getVoucher());
        holder.txtTotal.setText("$"+total);



        return view;
    }

    private Double phepTinhCong(Double price,Double price2){
        BigDecimal b1 = new BigDecimal(price);
        BigDecimal b2 = new BigDecimal(price2);
        BigDecimal resMul = b1.add(b2, MathContext.DECIMAL32);
        return Double.parseDouble(String.valueOf(resMul));
    }
}
