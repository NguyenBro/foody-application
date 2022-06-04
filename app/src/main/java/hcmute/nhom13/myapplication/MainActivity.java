package hcmute.nhom13.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomappbar.BottomAppBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import hcmute.nhom13.myapplication.Database.Database;
import hcmute.nhom13.myapplication.InsertData.InsertDataActivity;
import hcmute.nhom13.myapplication.adapter.RestaurantAdapter;
import hcmute.nhom13.myapplication.adapter.PopularFoodAdapter;
import hcmute.nhom13.myapplication.model.Notification;
import hcmute.nhom13.myapplication.model.Restaurant;
import hcmute.nhom13.myapplication.model.PopularFood;
import hcmute.nhom13.myapplication.model.User;

public class MainActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    RecyclerView popularRecycler, restaurantRecycler;
    PopularFoodAdapter popularFoodAdapter;
    RestaurantAdapter restaurantAdapter;
    ImageView imgAvatar,imgSetUp,imgNotification,imgNoti;
    public static User user;
    public static String note;
    public static int numNoti;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgAvatar = findViewById(R.id.imageViewAvatar);
        imgSetUp = findViewById(R.id.imageView3);
        imgNotification =findViewById(R.id.imageView2);
        imgNoti = findViewById(R.id.imageView14);
        popularRecycler = findViewById(R.id.popular_recycler);
        restaurantRecycler = findViewById(R.id.asia_recycler);
        bottomAppBar =findViewById(R.id.bottomAppBar2);
        setSupportActionBar(bottomAppBar);
        Intent intent =getIntent();
        if((User)intent.getSerializableExtra("user") != null){
            user = (User)intent.getSerializableExtra("user");
        }
//        else{
//            Toast.makeText(this, "222", Toast.LENGTH_SHORT).show();
//        }

        imageOntification();

        imgSetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, InsertDataActivity.class));
            }
        });

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgNoti.setVisibility(View.GONE);
                startActivity(new Intent(MainActivity.this,NotificationActivity.class));
            }
        });
        // now here we will add some dummy data to out model class



        List<PopularFood> popularFoodList = new ArrayList<>();
        String url ="https://nguyenbro14092001.000webhostapp.com/qlsinhvien/Food/getDataFood.php";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest arrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.toString().equals("[]")) {
                    try {
                        JSONArray jsonArray =new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("ID");
                            int id_res = jsonObject.getInt("ID_RES");
                            String name = jsonObject.getString("NAME");
                            double price = jsonObject.getDouble("PRICE");
                            String des = jsonObject.getString("DESCRIPTION");
                            String stringByte =jsonObject.getString("IMAGE");
                            byte[] image = Base64.getDecoder().decode(stringByte);
                            PopularFood popularFood = new PopularFood(id,id_res,name,price,des,image);
                            popularFoodList.add(popularFood);
//                            Log.d("aaa",image.toString());
//                            Log.d("aaa",stringByte);
                        }


                        setPopularRecycler(popularFoodList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "No Data Food", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
                Log.d("aaa", error.toString());
            }
        });
        requestQueue.add(arrayRequest);
//        Cursor dataFood =IntroActivity.database.getData("SELECT * FROM food WHERE id < '15'");
//        //byte[] hinhAnh = new byte[0];
//        while (dataFood.moveToNext()) {
//            int id = dataFood.getInt(0);
//            int id_res = dataFood.getInt(1);
//            String name = dataFood.getString(2);
//            double price = dataFood.getDouble(3);
//            String des = dataFood.getString(4);
//            byte[] hinhAnh = new byte[0];
//            hinhAnh = dataFood.getBlob(5);
//            PopularFood popularFood = new PopularFood(id,id_res,name,price,des,hinhAnh);
//            popularFoodList.add(popularFood);
//            //Log.d("BBB", id + " " + id_res + " " + name + " " + price + " " + des);
//        }


//        popularFoodList.add(new PopularFood("Float Cake Vietnam", 7.05, R.drawable.popularfood1));
//        popularFoodList.add(new PopularFood("Chiken Drumstick", 17.05, R.drawable.popularfood2));
//        popularFoodList.add(new PopularFood("Fish Tikka Sticks", 25.05, R.drawable.popularfood3));
//        popularFoodList.add(new PopularFood("Float Cake Vietnam", 7.05, R.drawable.popularfood1));
//        popularFoodList.add(new PopularFood("Chiken Drumstick", 17.05, R.drawable.popularfood2));
//        popularFoodList.add(new PopularFood("Fish Tikka Sticks", 25.05, R.drawable.popularfood3));


//=============================

        List<Restaurant> asiaFoodList = new ArrayList<>();
        String urlR ="https://nguyenbro14092001.000webhostapp.com/qlsinhvien/Restaurant/getDataRestaurant.php";
        RequestQueue requestQueueRes = Volley.newRequestQueue(MainActivity.this);
        StringRequest arrayRequestRes = new StringRequest(Request.Method.POST, urlR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.toString().equals("[]")) {
                    try {
                        JSONArray jsonArray =new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("ID");
                            String name = jsonObject.getString("NAME");
                            String address = jsonObject.getString("ADDRESS");
                            Double distance = jsonObject.getDouble("DISTANCE");
                            Double rating = jsonObject.getDouble("RATING");
                            String stringByte =jsonObject.getString("IMAGE");
                            byte[] image = Base64.getDecoder().decode(stringByte);
                            Restaurant restaurant = new Restaurant(id,name,String.valueOf(distance),image,String.valueOf(rating),address);
                            asiaFoodList.add(restaurant);
                        }

                        setAsiaRecycler(asiaFoodList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "No Data Food", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
                Log.d("aaa", error.toString());
            }
        });
        requestQueueRes.add(arrayRequestRes);

//        Cursor data =IntroActivity.database.getData("SELECT * FROM restaurant");
//        while (data.moveToNext()){
//            int id = data.getInt(0);
//            String name = data.getString(1);
//            String address =data.getString(2);
//            double distance =data.getDouble(3);
//            double rating = data.getDouble(4);
//            byte[] hinhAnh = new byte[0];
//            hinhAnh = data.getBlob(5);
//            Restaurant restaurant =new Restaurant(id,name,String.valueOf(distance),hinhAnh,String.valueOf(rating),address);
//            asiaFoodList.add(restaurant);
//            //Log.d("AAA",id + " " +name+ " "+address+ " "+distance+ " "+rating);
//        }

//
//        asiaFoodList.add(new Restaurant("Briand Restaurant", "0.7km", R.drawable.asiafood1, "4.5", "Hai Ba Trung,District 1"));
//        asiaFoodList.add(new Restaurant("Sun Restaurant", "3.4km", R.drawable.asiafood2, "4.2", "VoVanTan,District 3"));
//        asiaFoodList.add(new Restaurant("Yashiyama Restaurant", "2.6km", R.drawable.asiafood1, "4.5", "Le Van Viet,District 9"));
//        asiaFoodList.add(new Restaurant("Friends Restaurant", "0.5km", R.drawable.asiafood2, "4.2", "Hoang Van Thu,District Phu Nhuan"));
//        asiaFoodList.add(new Restaurant("First Restaurant", "3km", R.drawable.asiafood1, "4.5", "3/2,District 10"));
//        asiaFoodList.add(new Restaurant("Milan Restaurant", "5.5km", R.drawable.asiafood2, "4.2", "Dien Bien Phu,District 3"));


    }


    private void setPopularRecycler(List<PopularFood> popularFoodList) {


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        popularFoodAdapter = new PopularFoodAdapter(this, popularFoodList);
        popularRecycler.setAdapter(popularFoodAdapter);

    }

    private void setAsiaRecycler(List<Restaurant> restaurantList) {


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        restaurantRecycler.setLayoutManager(layoutManager);
        restaurantAdapter = new RestaurantAdapter(this, restaurantList);
        restaurantRecycler.setAdapter(restaurantAdapter);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                //Toast.makeText(this, "gg", Toast.LENGTH_SHORT).show();
                break;
            case R.id.heart:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                break;
            case R.id.history:
                //Toast.makeText(this, "ff", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, HistoryOrderActivity.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void imageOntification(){
        int count =0;
        Cursor data = IntroActivity.database.getData("SELECT * FROM notification WHERE id_user ='"+user.getPhone()+"' ORDER BY id DESC");
        while (data.moveToNext()){
            count++;
        }

        if(count > numNoti){
            imgNoti.setVisibility(View.VISIBLE);
            numNoti = count;
        }
    }

    // Hi all,
    // Today we are going to build a food app.
    // so the first things frist lets add font and image assets
    // so lets see the design
    // now we will setup recyclerview
    // first we make a model class then adapter class.
    // now we will create a recyclerview row item layout file
    // so our adapter class is ready
    // now we will bind data with recyclerview
    // so we have successfully setup popular recyclerview
    // now same setup we need to do for asia food
    // will make model class then adapter and setup recyclerview
    // so lets do it fast.
    // so asia food setup done.
    // Now we will setup Bottom app bar
    // bottom app bar setup done if you want you can increase menu item in menu file
    // now we will setup details activity and on click listener in recyclerview row item
    // so this tutorial has been completed if you have any
    // question and query please do comment
    // Like share and subscribe
    // Thankyou for watching
    // see you in the next tutorial

}
