package hcmute.nhom13.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import hcmute.nhom13.myapplication.model.PopularFood;
import hcmute.nhom13.myapplication.model.User;

public class DetailsActivity extends AppCompatActivity {
    ImageView imgCart,imgFood,imgBack,imgSub,imgAdd;
    TextView txtQuantity,txtTotal,txtName,txtDes;
    PopularFood popularFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        imgCart = findViewById(R.id.imageViewAddToCart);
        imgFood = findViewById(R.id.imageViewDetailFood);
        imgBack = findViewById(R.id.imageView5);
        imgSub = findViewById(R.id.imageViewTru_Detail);
        imgAdd =findViewById(R.id.imageViewCong_Detail);
        txtQuantity = findViewById(R.id.textViewSoLuong_Detail);
        txtTotal = findViewById(R.id.textViewTongTien_Detail);
        txtName = findViewById(R.id.textViewName_Detail);
        txtDes = findViewById(R.id.textViewDes_Detail);
        setEvents();

        Intent intent = getIntent();
        popularFood = (PopularFood) intent.getSerializableExtra("objectPopularFood");
        //Set Price
        txtTotal.setText("$"+popularFood.getPrice());
        txtName.setText(popularFood.getName());
        txtDes.setText(popularFood.getDescription());
        //byte[] hinhAnh = intent.getByteArrayExtra("imagess");
        //Log.d("OOO",popularFood.getName());
        byte[] hinhanh = popularFood.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
        imgFood.setImageBitmap(bitmap);


        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_res;
                int quanlity = Integer.parseInt(txtQuantity.getText().toString());
                String id_user =MainActivity.user.getPhone();
                int id_food = popularFood.getId();
                final String[] id_restaurant = new String[1];
                int res =0;
                int dem=0;
//                Cursor data2= IntroActivity.database.getData("SELECT food.id_res FROM food,cart WHERE food.id = cart.id_food AND cart.id_user ='"+id_user+"'");
//                while (data2.moveToNext()){
//                    //Log.d("CARTAAA",data.getString(0)+data.getInt(1)+data.getInt(2));
//                    res = data2.getInt(0);
//                }
                String url1="https://nguyenbro14092001.000webhostapp.com/qlsinhvien/Cart/getIdRestaurantCart.php";
                //================
                RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
                StringRequest arrayRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals(String.valueOf(popularFood.getId_res())) || response.equals("0")){
                            //Toast.makeText(DetailsActivity.this, "dung", Toast.LENGTH_SHORT).show();
                            insertFoodToCart(quanlity);
                            Toast.makeText(DetailsActivity.this, "Loading...", Toast.LENGTH_LONG).show();
                            CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
                                @Override
                                public void onTick(long l) {

                                }

                                @Override
                                public void onFinish() {
                                    startActivity(new Intent(DetailsActivity.this,CartActivity.class));
                                }
                            };

                            countDownTimer.start();

                        }else if(!response.equals(String.valueOf(popularFood.getId_res()))){
                            //Toast.makeText(DetailsActivity.this, "sai", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(DetailsActivity.this);
                            alert.setTitle("Add Food");
                            alert.setMessage("Do you want to delete old restaurant dishes from your shopping cart?");
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deleteCart();
                                    insertFoodToCart(quanlity);
                                    Toast.makeText(DetailsActivity.this, "Loading...", Toast.LENGTH_LONG).show();
                                    CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {
                                        @Override
                                        public void onTick(long l) {

                                        }

                                        @Override
                                        public void onFinish() {
                                            startActivity(new Intent(DetailsActivity.this,CartActivity.class));
                                        }
                                    };

                                    countDownTimer.start();
                                }
                            });

                            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            alert.show();

                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailsActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
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

                //=============

//                if(res != popularFood.getId_res()){
//                    IntroActivity.database.QueryData("DELETE FROM cart");
//                }
//                else{
//                    Cursor data= IntroActivity.database.getData("SELECT * FROM cart WHERE id_user ='"+id_user+"' AND id_food='"+id_food+"'");
//                    while (data.moveToNext()){
//                        //Log.d("CARTAAA",data.getString(0)+data.getInt(1)+data.getInt(2));
//                        dem++;
//                    }
//                }
//
//
//                if(dem == 0){
//                    //Toast.makeText(DetailsActivity.this, "them vao", Toast.LENGTH_SHORT).show();
//                    IntroActivity.database.InsertFoodToCart(MainActivity.user.getPhone(),popularFood.getId(),quanlity);
//                }
//
//                startActivity(new Intent(DetailsActivity.this,CartActivity.class));
            }
        });
    }

    private void insertFoodToCart(int quanlity) {
        //Toast.makeText(this, "Vao k", Toast.LENGTH_SHORT).show();
        String url1="https://nguyenbro14092001.000webhostapp.com/qlsinhvien/Cart/getCartByUserAndFood.php";
        //================
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(DetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                if(response.equals("0")){
                    insert(quanlity);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
                Log.d("aaa", error.toString());
            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<String,String>();
                params.put("id_user",MainActivity.user.getPhone());
                params.put("id_food",String.valueOf(popularFood.getId()));

                return params;
            }

        };


//                //res = Integer.parseInt(id_restaurant[0]);
//                Toast.makeText(DetailsActivity.this, res+"", Toast.LENGTH_SHORT).show();
        requestQueue.add(arrayRequest);
    }

    private void insert(int quanlity) {
        String url1="https://nguyenbro14092001.000webhostapp.com/qlsinhvien/Cart/insertCart.php";
        //================
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    Toast.makeText(DetailsActivity.this, "Done", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(DetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
                Log.d("aaa", error.toString());
            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<String,String>();
                params.put("id_user",MainActivity.user.getPhone());
                params.put("id_food",String.valueOf(popularFood.getId()));
                params.put("quanlity",String.valueOf(quanlity));

                return params;
            }

        };


//                //res = Integer.parseInt(id_restaurant[0]);
//                Toast.makeText(DetailsActivity.this, res+"", Toast.LENGTH_SHORT).show();
        requestQueue.add(arrayRequest);
    }

    private void deleteCart() {
        String url1="https://nguyenbro14092001.000webhostapp.com/qlsinhvien/Cart/deleteCartByIdUser.php";
        //================
        RequestQueue requestQueue = Volley.newRequestQueue(DetailsActivity.this);
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("aaa",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
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
    }

    private void setEvents(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this,MainActivity.class));
            }
        });

        imgSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtQuantity.getText().toString().equals("0")){
                    int quantity = Integer.parseInt(txtQuantity.getText().toString());
                    int newQuantity = quantity-1;
                    txtQuantity.setText(String.valueOf(newQuantity));
                    //Double total = newQuantity * popularFood.getPrice();
                    Double total = phepTinh(popularFood.getPrice(),newQuantity);
                    txtTotal.setText(String.valueOf(total));
                }
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(txtQuantity.getText().toString());
                int newQuantity = quantity+1;
                txtQuantity.setText(String.valueOf(newQuantity));
                //Double total = newQuantity * popularFood.getPrice();
                Double total = phepTinh(popularFood.getPrice(),newQuantity);
                txtTotal.setText(String.valueOf(total));
            }
        });
    }

    private Double phepTinh(Double price,int quantity){
        BigDecimal b1 = new BigDecimal(price);
        BigDecimal b2 = new BigDecimal(quantity);
        BigDecimal resMul = b1.multiply(b2, MathContext.DECIMAL32);
        return Double.parseDouble(String.valueOf(resMul));
    }


}
