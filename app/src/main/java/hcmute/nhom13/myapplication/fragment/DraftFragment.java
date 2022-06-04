package hcmute.nhom13.myapplication.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hcmute.nhom13.myapplication.IntroActivity;
import hcmute.nhom13.myapplication.MainActivity;
import hcmute.nhom13.myapplication.R;
import hcmute.nhom13.myapplication.adapter.BillAdapter;
import hcmute.nhom13.myapplication.model.Bill;


public class DraftFragment extends ListFragment {

    ArrayList<Bill> arrayBill;
    BillAdapter adapter;
    public DraftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        arrayBill = new ArrayList<>();
        Cursor data = IntroActivity.database.getData("SELECT * FROM bill WHERE id_user='"+ MainActivity.user.getPhone() +"' AND status='failed' ORDER BY id DESC");
        while (data.moveToNext()){
            //Log.d("INDATABASE",data.getDouble(2)+" " +data.getDouble(3)+" "+data.getDouble(4)+" " + data.getString(5)+" " +data.getString(6)+" "+data.getString(7));
            int id = data.getInt(0);
            String id_user = data.getString(1);
            Double total = data.getDouble(2);
            Double fee = data.getDouble(3);
            Double voucher = data.getDouble(4);
            String note = data.getString(5);
            String date = data.getString(6);
            String time = data.getString(7);
            String status = data.getString(8);
            Bill bill =new Bill(id,id_user,total,fee,voucher,note,date,time,status);
            arrayBill.add(bill);
        }

        adapter = new BillAdapter(getContext(),R.layout.adapter_history,arrayBill);
        setListAdapter(adapter);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_draft, container, false);
    }
}