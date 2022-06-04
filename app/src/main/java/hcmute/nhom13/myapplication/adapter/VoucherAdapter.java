package hcmute.nhom13.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hcmute.nhom13.myapplication.BottomSheet.BottomSheetVoucher;
import hcmute.nhom13.myapplication.CartActivity;
import hcmute.nhom13.myapplication.PaymentActivity;
import hcmute.nhom13.myapplication.R;
import hcmute.nhom13.myapplication.model.CartFood;
import hcmute.nhom13.myapplication.model.Voucher;

public class VoucherAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<Voucher> listVoucher;
    private PaymentActivity paymentActivity;

    public VoucherAdapter(Context context, int layout, List<Voucher> listVoucher,PaymentActivity paymentActivity) {
        this.context = context;
        this.layout = layout;
        this.listVoucher = listVoucher;
        this.paymentActivity = paymentActivity;
    }

    @Override
    public int getCount() {
        return listVoucher.size();
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
        TextView txtBody,txtHsd,txtUse;
        ImageView imgVoucher;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder holder;
        if(view ==null){
            holder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            //Anh xa
            holder.txtBody = view.findViewById(R.id.textViewBodyy);
            holder.txtHsd = view.findViewById(R.id.textViewHSD);
            holder.txtUse = view.findViewById(R.id.textViewUseNow);
            holder.imgVoucher = view.findViewById(R.id.imageViewVoucher);



            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        Voucher voucher = listVoucher.get(i);
        holder.txtBody.setText(voucher.getBody());
        holder.txtHsd.setText(voucher.getHsd());
        holder.imgVoucher.setImageResource(voucher.getImage());

        holder.txtUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PaymentActivity.
                if(voucher.getId()==1){
                    PaymentActivity.idv=1;
                    BottomSheetVoucher.id=1;
                    PaymentActivity.bottomSheetVoucher.dismiss();
                    //paymentActivity.reLoad();
                    Intent i = new Intent(context, PaymentActivity.class);
                    ArrayList<CartFood> arrayFood = CartActivity.arrayFood;
                    i.putExtra("arrayFood", arrayFood);
                    context.startActivity(i);

                    


                }else if(voucher.getId()==2){
                    PaymentActivity.idv=2;
                    BottomSheetVoucher.id=2;
                    PaymentActivity.bottomSheetVoucher.dismiss();
                    Intent i = new Intent(context, PaymentActivity.class);
                    ArrayList<CartFood> arrayFood = CartActivity.arrayFood;
                    i.putExtra("arrayFood", arrayFood);
                    context.startActivity(i);
                }else{
                    Toast.makeText(context, "con lai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
