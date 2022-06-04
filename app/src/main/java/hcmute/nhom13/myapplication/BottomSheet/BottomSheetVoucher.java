package hcmute.nhom13.myapplication.BottomSheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import hcmute.nhom13.myapplication.CartActivity;
import hcmute.nhom13.myapplication.MainActivity;
import hcmute.nhom13.myapplication.PaymentActivity;
import hcmute.nhom13.myapplication.R;
import hcmute.nhom13.myapplication.adapter.VoucherAdapter;
import hcmute.nhom13.myapplication.model.CartFood;
import hcmute.nhom13.myapplication.model.Voucher;

public class BottomSheetVoucher extends BottomSheetDialogFragment {

    EditText edtVoucher;
    ImageView imgChooseVoucher;
    ListView ltvVoucher;
    ArrayList<Voucher> arrayVoucher;
    VoucherAdapter adapter;
    View view;
    public static int id;



    public BottomSheetVoucher(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.bottom_sheet_voucher,container,false);
        edtVoucher =view.findViewById(R.id.editTextVoucher);
        imgChooseVoucher = view.findViewById(R.id.imageViewChooseVoucher);
        ltvVoucher = view.findViewById(R.id.listViewVoucher);
        arrayVoucher = new ArrayList<>();
        arrayVoucher.add(new Voucher(1,"10% discount for orders over $50","HSD:01/08/2022",R.drawable.image_discount));
        arrayVoucher.add(new Voucher(2,"Free Ship for orders over $50","HSD:01/07/2022",R.drawable.image_freeship));
        adapter = new VoucherAdapter(getActivity(),R.layout.adapter_vouchers,arrayVoucher,new PaymentActivity());
        ltvVoucher.setAdapter(adapter);

        imgChooseVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=0;
                Intent i = new Intent(view.getContext(), PaymentActivity.class);
                ArrayList<CartFood> arrayFood = CartActivity.arrayFood;
                i.putExtra("arrayFood", arrayFood);
                view.getContext().startActivity(i);
            }
        });


        return view;
    }

    public void reLoad(){
        TextView txtVoucher = getActivity().findViewById(R.id.textViewVoucher);
        if(id==1){
            txtVoucher.setText("0");
        }
    }
}
