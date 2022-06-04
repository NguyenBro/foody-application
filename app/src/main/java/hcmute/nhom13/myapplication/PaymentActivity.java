package hcmute.nhom13.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import hcmute.nhom13.myapplication.BottomSheet.BottomSheetNote;
import hcmute.nhom13.myapplication.BottomSheet.BottomSheetVoucher;
import hcmute.nhom13.myapplication.adapter.CartPaymentAdapter;
import hcmute.nhom13.myapplication.model.CartFood;
import hcmute.nhom13.myapplication.model.User;

public class PaymentActivity extends AppCompatActivity {
    ListView listViewFood;
    ArrayList<CartFood> arrayFood;
    CartPaymentAdapter adapter;
    Button btnPaymentCard,btnPaymentCash,btnNote,btnVoucher;
    TextView txtName,txtPhone,txtTotal,txtTotalPrice,txtTotalBottom,txtTotalFee1,txtTotalFee2,txtEdit,txtAdd,
            txtFee,txtShipFee,txtVoucher,txtBook;
    ImageView imgBack;
    Double priceOrder ;
    Double fee;
    Double voucher ;
    public static BottomSheetVoucher bottomSheetVoucher;
    String isPay ="Cash";
    String title = "";
    public static int idv;
    int dem=0;
    Double total =0.0;
    public  static int idBill;

    public PaymentActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btnPaymentCard = findViewById(R.id.buttonPaymentCard);
        btnPaymentCash = findViewById(R.id.buttonPaymentCash);
        txtName =findViewById(R.id.textViewUserName_Payment);
        txtPhone = findViewById(R.id.textViewPhone_Payment);
        txtTotal = findViewById(R.id.textViewItem1);
        txtTotalBottom = findViewById(R.id.textViewItem2);
        txtTotalPrice = findViewById(R.id.textViewTotalPrice);
        txtTotalFee1 = findViewById(R.id.textViewTotal1);
        txtTotalFee2 = findViewById(R.id.textViewTotal2);
        txtEdit = findViewById(R.id.textViewEdit);
        txtAdd = findViewById(R.id.textViewAddress);
        btnNote = findViewById(R.id.buttonNotes);
        btnVoucher = findViewById(R.id.buttonVouchers);
        txtFee = findViewById(R.id.textViewFee);
        txtShipFee = findViewById(R.id.textViewShipFee);
        txtVoucher = findViewById(R.id.textViewVoucher);
        txtBook = findViewById(R.id.textViewBook);
        imgBack = findViewById(R.id.imageView5);

        listViewFood =findViewById(R.id.viewPayment);
        bottomSheetVoucher =new BottomSheetVoucher();


        //arrayFood = new ArrayList<>();
        Intent intent =getIntent();
        arrayFood = (ArrayList<CartFood>) intent.getSerializableExtra("arrayFood");
        for(int i=0;i<arrayFood.size();i++){
            if(!arrayFood.get(i).isCheck()){
                arrayFood.remove(i);
                i--;
            }
        }
        adapter =new CartPaymentAdapter(this,R.layout.payment_cart_item,arrayFood);
        listViewFood.setAdapter(adapter);
        setTextViewInfo();
        setTextViewTotal();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentActivity.this,CartActivity.class));
            }
        });

        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetNote bottomSheetNote =new BottomSheetNote();
                bottomSheetNote.show(getSupportFragmentManager(),"TAG");
            }
        });

        btnVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetVoucher.show(getSupportFragmentManager(),"TAG");
            }
        });

        txtBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
                String time =  new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                //printInfomation();
                IntroActivity.database.InsertBill(MainActivity.user.getPhone(),total,fee,voucher,MainActivity.note,date,time,"ordering");
                Cursor data = IntroActivity.database.getData("SELECT * FROM bill WHERE date='"+date+"' AND time='"+time+"' AND status='ordering'");
                while (data.moveToNext()){
                    idBill = data.getInt(0);
                }
                for(int i=0;i<arrayFood.size();i++){
                    IntroActivity.database.InsertDesBill(idBill,arrayFood.get(i).getId(),arrayFood.get(i).getQuantity());
                }
                Double a = phepTinhCong(total,fee);
                Double total_bill = phepTinhCong(a,voucher);

                IntroActivity.database.InsertNotification(MainActivity.user.getPhone(),"ordering",total_bill,date,time);


                Intent intent1 = new Intent(PaymentActivity.this, ConfirmActivity.class);
                intent1.putExtra("item",dem);
                intent1.putExtra("total",total_bill);
                intent1.putExtra("isPay",isPay);
                intent1.putExtra("title",title);
                intent1.putExtra("arrayFood",arrayFood);
                startActivity(intent1);
                MainActivity.note="";

                //Xoa Khoi Cart
                for(int i=0;i<arrayFood.size();i++){
                    IntroActivity.database.QueryData("DELETE FROM cart WHERE id_user='"+MainActivity.user.getPhone()+"' " +
                            "AND id_food='"+arrayFood.get(i).getId()+"'");
                }
            }
        });


        btnPaymentCash.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                isPay ="Cash";
                Drawable img = getResources().getDrawable(R.drawable.ic_baseline_check_circle_green_24);
                btnPaymentCash.setCompoundDrawablesWithIntrinsicBounds(null,null,null,img);
                btnPaymentCard.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

                btnPaymentCash.setBackgroundColor(Color.parseColor("#ffffff"));
                btnPaymentCash.setTextColor(Color.parseColor("#989898"));

                btnPaymentCard.setBackgroundColor(Color.parseColor("#989898"));
                btnPaymentCard.setTextColor(Color.parseColor("#ffffff"));
            }
        });

        btnPaymentCard.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                isPay ="Card";
                Drawable img = getResources().getDrawable(R.drawable.ic_baseline_check_circle_green_24);
                btnPaymentCard.setCompoundDrawablesWithIntrinsicBounds(null,null,null,img);
                btnPaymentCash.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

                btnPaymentCard.setBackgroundColor(Color.parseColor("#ffffff"));
                btnPaymentCard.setTextColor(Color.parseColor("#989898"));

                btnPaymentCash.setBackgroundColor(Color.parseColor("#989898"));
                btnPaymentCash.setTextColor(Color.parseColor("#ffffff"));
            }
        });

        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtNewPhone,edtNewName,edtNewAddress;
                Button btnChange;
                Dialog dialog = new Dialog(PaymentActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_edit_info);
                dialog.setCanceledOnTouchOutside(true);

                edtNewAddress =dialog.findViewById(R.id.editTextAdd_Edit);
                edtNewName = dialog.findViewById(R.id.edtName_Edit);
                edtNewPhone = dialog.findViewById(R.id.edtPhone_Edit);
                btnChange =dialog.findViewById(R.id.buttonChange);

                edtNewName.setText(txtName.getText().toString());
                edtNewAddress.setText(txtAdd.getText().toString());
                edtNewPhone.setText(txtPhone.getText().toString());
                dialog.show();

                btnChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtName.setText(edtNewName.getText().toString());
                        txtPhone.setText(edtNewPhone.getText().toString());
                        txtAdd.setText(edtNewAddress.getText().toString());
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void printInfomation(){
        String date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        String time =  new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        Log.d("PRINTINFO",total+" " + voucher+" " +fee+" " +MainActivity.note+" "+date+" "+time);
    }

    private void setTextViewInfo(){
        User user =MainActivity.user;
        txtPhone.setText(user.getPhone());
        txtName.setText(user.getName());
    }

    public void setTextViewTotal(){

        for(int i=0;i<arrayFood.size();i++){
            if(arrayFood.get(i).isCheck()){
                title = arrayFood.get(i).getName();
                int id = arrayFood.get(i).getId();
                dem = dem + arrayFood.get(i).getQuantity();
                Double price =phepTinhNhan(arrayFood.get(i).getPrice(),arrayFood.get(i).getQuantity());
                total = phepTinhCong(total,price);
            }

        }


        txtTotal.setText("Total "+dem+" Item");
        txtTotalBottom.setText(dem+" Item");

        priceOrder = PriceOrder();
        fee = ShipOrder();
        voucher = VoucherOrder(BottomSheetVoucher.id);

        txtTotalPrice.setText("$"+priceOrder);
        Double t1 = phepTinhCong(priceOrder,fee);
        Double result = phepTinhCong(t1,voucher);


        txtTotalFee1.setText("$"+result);
        txtTotalFee2.setText("$"+result);
//        Log.d("AAA",total+"");
//        txtItem.setText(dem+"");
    }

    public Double PriceOrder(){
        Double total =0.0;
        for(int i=0;i<arrayFood.size();i++){
            if(arrayFood.get(i).isCheck()){
                Double price =phepTinhNhan(arrayFood.get(i).getPrice(),arrayFood.get(i).getQuantity());
                total = phepTinhCong(total,price);
            }

        }

        return total;
    }

    public Double VoucherOrder(int id){
        Double voucher=0.0;
        if(id==1){
            Double price = PriceOrder();
            if(price >= 50.0){
                voucher=phepTinhChia(price,10)*-1;
            }
        }
        else if(id ==2){
            Double price = PriceOrder();
            if(price >= 50.0){
                voucher=ShipOrder() *-1;
            }
        }
        txtVoucher.setText("$"+voucher);
        return voucher;
    }



    public Double ShipOrder(){
        int id = arrayFood.get(0).getId();
        int id_res=0;
        Cursor data = IntroActivity.database.getData("SELECT * FROM food WHERE id='"+id+"'");
        while (data.moveToNext()){
            id_res = data.getInt(1);
        }
        //Log.d("IDRES",id_res+"");

        Double distance=0.0;
        Cursor dataRes = IntroActivity.database.getData("SELECT * FROM restaurant WHERE id='"+id_res+"'");
        while (dataRes.moveToNext()){
            distance = dataRes.getDouble(3);
        }

        Double fee;

        if(distance < 2){
            fee= 4.0;
        }
        else if(distance<5){
            fee= 7.5;
        }
        else if(distance<10){
            fee= 9.0;
        }
        else{
            fee= 15.0;
        }
        txtShipFee.setText("$"+fee);
        txtFee.setText("Shipping Fee : "+distance +"km");
        return fee;

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

    private Double phepTinhChia(Double price,int quantity){
        BigDecimal b1 = new BigDecimal(price);
        BigDecimal b2 = new BigDecimal(quantity);
        BigDecimal resMul = b1.divide(b2, MathContext.DECIMAL32);
        return Double.parseDouble(String.valueOf(resMul));
    }

    public void reLoad(){
        Intent i = new Intent(PaymentActivity.this, PaymentActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);

    }
}