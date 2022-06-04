package hcmute.nhom13.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import hcmute.nhom13.myapplication.adapter.CartAdapter;
import hcmute.nhom13.myapplication.adapter.RestaurantFoodAdaper;
import hcmute.nhom13.myapplication.model.CartFood;
import hcmute.nhom13.myapplication.model.PopularFood;

public class CartActivity extends AppCompatActivity {
    ListView listViewFood;
    public static ArrayList<CartFood> arrayFood;
    CartAdapter adapter;
    Button btnCheckOut;
    ImageView imgBack;
    TextView txtItem,txtTotal,txtDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        listViewFood = findViewById(R.id.listViewFood);
        btnCheckOut = findViewById(R.id.buttonCheckOut);
        imgBack = findViewById(R.id.imageViewBack_Cart);
        txtItem = findViewById(R.id.textViewItem_Cart);
        txtTotal = findViewById(R.id.textViewTotal_Cart);
        txtDelete =findViewById(R.id.textViewClear);

        arrayFood = new ArrayList<>();
        getCart();

//        Cursor data= IntroActivity.database.getData("SELECT * FROM cart ");
//        while (data.moveToNext()){
//            Log.d("CARTAAA",data.getString(0)+data.getLong(1)+data.getLong(2));
//        }


        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayIsCheck()) {
                    MainActivity.note="";
                    Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                    intent.putExtra("arrayFood", arrayFood);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CartActivity.this, "Please choose food", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });

        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteCart();
            }
        });

    }

    public void getCart(){
        arrayFood.clear();
        String id_user = MainActivity.user.getPhone();
//        Cursor data= IntroActivity.database.getData("SELECT cart.id_food,cart.quantity,food.name,food.double,food.food_image FROM cart,food  WHERE cart.id_food = food.id AND cart.id_user='"+id_user+"'");
//        while (data.moveToNext()){
//            cartFood = new CartFood();
//            cartFood.setId(data.getInt(0));
//            cartFood.setQuantity(data.getInt(1));
//            cartFood.setName(data.getString(2));
//            cartFood.setPrice(data.getDouble(3));
//            cartFood.setImage(data.getBlob(4));
//            Log.d("CARTAAA",cartFood.getId()+cartFood.getName()+cartFood.getPrice()+cartFood.getQuantity());
//            arrayFood.add(cartFood);
//        }
        //arrayFood.add(new CartFood("Pizza",R.drawable.pizza1,20.0,1));
        String url ="https://nguyenbro14092001.000webhostapp.com/qlsinhvien/Cart/getDataCart.php";
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.toString().equals("[]")) {
                    try {
                        //Toast.makeText(IntroActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        JSONArray arrayResponse = new JSONArray(response);
                        for (int i = 0; i < arrayResponse.length(); i++) {
                            JSONObject jsonObject = arrayResponse.getJSONObject(i);
                            CartFood cartFood = new CartFood();
                            cartFood.setId(jsonObject.getInt("ID_FOOD"));
                            cartFood.setName(jsonObject.getString("NAME"));
                            cartFood.setQuantity(jsonObject.getInt("QUANLITY"));
                            cartFood.setPrice(jsonObject.getDouble("PRICE"));
                            String stringByte =jsonObject.getString("IMAGE");
                            byte[] image = Base64.getDecoder().decode(stringByte);
                            cartFood.setImage(image);
                            arrayFood.add(cartFood);
                        }
                        adapter =new CartAdapter(CartActivity.this,R.layout.viewholder_cart,arrayFood);
                        listViewFood.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
                Log.d("aaa", error.toString());
            }
        }){
            @NonNull
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<String,String>();
                params.put("id_user",id_user);

                return params;
            }

        };
        requestQueue.add(arrayRequest);


    }

    public void setTextViewTotal(){
        int dem=0;
        Double total =0.0;
        for(int i=0;i<arrayFood.size();i++){
            if(arrayFood.get(i).isCheck()){
                dem = dem + arrayFood.get(i).getQuantity();
                Double price =phepTinhNhan(arrayFood.get(i).getPrice(),arrayFood.get(i).getQuantity());
                total = phepTinhCong(total,price);
            }

        }

        txtTotal.setText("$"+total);
        Log.d("AAA",total+"");
        txtItem.setText(dem+"");
    }

    public boolean arrayIsCheck(){
        int dem =0;
        for(int i=0;i<arrayFood.size();i++){
            if(arrayFood.get(i).isCheck()){
                dem++;
            }
        }
        if(dem!=0){
            return true;
        }
        return false;
    }

    public void DeleteFoodToCart(int id_food){
        String id_user = MainActivity.user.getPhone();
        IntroActivity.database.QueryData("DELETE FROM cart WHERE id_user='"+id_user+"' AND id_food='"+id_food+"'" );
        arrayFood.clear();
        getCart();
        setTextViewTotal();

    }

    public void UpdateQuantity(CartFood cartFood,int newQuantity){
        String id_user = MainActivity.user.getPhone();
        int id_food = cartFood.getId();
        IntroActivity.database.QueryData("UPDATE  cart SET quantity='"+newQuantity+"' WHERE id_user='"+id_user+"' AND id_food='"+id_food+"'");
        arrayFood.clear();
    }

    public void DeleteCart(){

        String id_user = MainActivity.user.getPhone();
        //IntroActivity.database.QueryData("DELETE FROM cart WHERE id_user='"+id_user+"'" );

        String url1="https://nguyenbro14092001.000webhostapp.com/qlsinhvien/Cart/deleteCartByIdUser.php";
        //================
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")) {
                    arrayFood.clear();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
                Log.d("aaa", error.toString());
            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<String,String>();
                params.put("id_user",MainActivity.user.getPhone());

                return params;
            }

        };


//                //res = Integer.parseInt(id_restaurant[0]);
//                Toast.makeText(DetailsActivity.this, res+"", Toast.LENGTH_SHORT).show();
        requestQueue.add(arrayRequest);
        CountDownTimer countDownTimer = new CountDownTimer(1500,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(CartActivity.this,CartActivity.class));
                getCart();
                setTextViewTotal();
            }
        };

        countDownTimer.start();

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