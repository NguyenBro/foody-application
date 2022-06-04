package hcmute.nhom13.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import hcmute.nhom13.myapplication.adapter.CartAdapter;
import hcmute.nhom13.myapplication.adapter.PopularFoodAdapter;
import hcmute.nhom13.myapplication.adapter.RestaurantFoodAdaper;
import hcmute.nhom13.myapplication.model.CartFood;
import hcmute.nhom13.myapplication.model.PopularFood;
import hcmute.nhom13.myapplication.model.User;

public class RestaurantActivity extends AppCompatActivity {
    GridView gridViewFood;
    ArrayList<PopularFood> arrayFood;
    RestaurantFoodAdaper adapter;
    TextView txtNameRestaurant,txtAddress,txtRating,txtDistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        gridViewFood = findViewById(R.id.gridviewFood);

        txtNameRestaurant = findViewById(R.id.textViewNameRestaurant);
        txtAddress = findViewById(R.id.textViewDiaChi_Restaurant);
        txtDistance = findViewById(R.id.textViewKhoangCach_Restaurant);
        txtRating = findViewById(R.id.textViewXepHang_Restaurant);


        Intent intent =getIntent();
        txtNameRestaurant.setText(intent.getStringExtra("nameRestaurant"));
        txtAddress.setText(intent.getStringExtra("addressRestaurant"));
        txtRating.setText(intent.getStringExtra("ratingRestaurant"));
        txtDistance.setText(intent.getStringExtra("distanceRestaurant"));
        int id_restaurant = intent.getIntExtra("idRestaurant",0);

        arrayFood = new ArrayList<>();
        String url ="https://nguyenbro14092001.000webhostapp.com/qlsinhvien/Food/getDataFoodByIdRestaurant.php";
        RequestQueue requestQueue = Volley.newRequestQueue(RestaurantActivity.this);
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.toString().equals("[]")) {
                    try {
                        //Toast.makeText(IntroActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        JSONArray arrayResponse = new JSONArray(response);
                        for(int i=0;i<arrayResponse.length();i++){
                            JSONObject jsonObject = arrayResponse.getJSONObject(i);
                            int id = jsonObject.getInt("ID");
                            int id_res = jsonObject.getInt("ID_RES");
                            String name = jsonObject.getString("NAME");
                            double price = jsonObject.getDouble("PRICE");
                            String des = jsonObject.getString("DESCRIPTION");
                            String stringByte =jsonObject.getString("IMAGE");
                            byte[] image = Base64.getDecoder().decode(stringByte);
                            PopularFood popularFood = new PopularFood(id,id_res,name,price,des,image);
                            arrayFood.add(popularFood);
                        }
                        adapter =new RestaurantFoodAdaper(RestaurantActivity.this,R.layout.popular_food_row_item,arrayFood);
                        gridViewFood.setAdapter((ListAdapter) adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(RestaurantActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RestaurantActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
                Log.d("aaa", error.toString());
            }
        }){
            @NonNull
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<String,String>();
                params.put("id_res",String.valueOf(id_restaurant));

                return params;
            }

        };
        requestQueue.add(arrayRequest);

//        Cursor dataFood =IntroActivity.database.getData("SELECT * FROM food WHERE id_res='"+id_restaurant+"'");
//        //byte[] hinhAnh = new byte[0];
//        while (dataFood.moveToNext()) {
//            int id = dataFood.getInt(0);
//            int id_res = dataFood.getInt(1);
//            String name = dataFood.getString(2);
//            double price = dataFood.getDouble(3);
//            String des = dataFood.getString(4);
//            byte[] hinhAnh = new byte[0];
//            hinhAnh = dataFood.getBlob(5);
//           // Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
//            PopularFood popularFood = new PopularFood(id,id_res,name,price,des,hinhAnh);
//            arrayFood.add(popularFood);
//            //Log.d("BBB", id + " " + id_res + " " + name + " " + price + " " + des);
//        }

//        arrayFood.add(new PopularFood("Float Cake Vietnam", 7.05, R.drawable.popularfood1));
//        arrayFood.add(new PopularFood("Chiken Drumstick", 17.05, R.drawable.popularfood2));
//        arrayFood.add(new PopularFood("Fish Tikka Stick", 25.05, R.drawable.popularfood3));
//        arrayFood.add(new PopularFood("Float Cake Vietnam", 7.05, R.drawable.popularfood1));
//        arrayFood.add(new PopularFood("Chiken Drumstick", 17.05, R.drawable.popularfood2));
//        arrayFood.add(new PopularFood("Fish Tikka Stick", 25.05, R.drawable.popularfood3));


        gridViewFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RestaurantActivity.this,DetailsActivity.class);
                PopularFood popularFood = arrayFood.get(i);
                //Log.d("sssimage",i+"");
                intent.putExtra("objectPopularFood",popularFood);
                //intent.putExtra("imagess",popularFood.getImageUrl());
                startActivity(intent);
            }
        });
    }
}