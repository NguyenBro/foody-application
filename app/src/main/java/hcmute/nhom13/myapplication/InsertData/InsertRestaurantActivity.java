package hcmute.nhom13.myapplication.InsertData;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import hcmute.nhom13.myapplication.MainActivity;
import hcmute.nhom13.myapplication.R;

public class InsertRestaurantActivity extends AppCompatActivity {
    EditText edtName,edtAddress,edtDistance,edtRating;
    ImageView imgRestaurant;
    Button btnClose,btnSave,btnLoad;
    int REQUEST_FOLDER = 1111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_restaurant);
        AnhXa();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertRestaurantActivity.this, MainActivity.class));

//                Cursor data =IntroActivity.database.getData("SELECT * FROM restaurant");
//                //byte[] hinhAnh = new byte[0];
//                while (data.moveToNext()){
//                    int id = data.getInt(0);
//                    String name = data.getString(1);
//                    String address =data.getString(2);
//                    double distance =data.getDouble(3);
//                    double rating = data.getDouble(4);
//                    //hinhAnh = data.getBlob(5);
//                    Log.d("aaaa",id + " " +name+ " "+address+ " "+distance+ " "+rating);
//
//                }

//                Bitmap bitmap =BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
//                imgRestaurant.setImageBitmap(bitmap);


            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_FOLDER);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtName.getText().toString().equals("") || edtAddress.getText().toString().equals("") || edtDistance.getText().toString().equals("") || edtRating.getText().toString().equals("")){
                    Toast.makeText(InsertRestaurantActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
                else{
                    String name = edtName.getText().toString().trim();
                    String address = edtAddress.getText().toString().trim();
                    Double distance = Double.parseDouble(edtDistance.getText().toString().trim());
                    Double rating =Double.parseDouble(edtRating.getText().toString().trim());
                    byte[] image =TransImage();

                    String s = Base64.encodeToString(image,Base64.DEFAULT);
                    String url ="https://nguyenbro14092001.000webhostapp.com/qlsinhvien/Restaurant/insertRestaurant.php";
//                    IntroActivity.database.InsertUser(phone,name,pass,birth,email);
//                    Toast.makeText(SignUpActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                    RequestQueue requestQueue = Volley.newRequestQueue(InsertRestaurantActivity.this);
                    StringRequest arrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.toString().equals("success")) {
                                Toast.makeText(InsertRestaurantActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            }else if(response.toString().equals("error")){
                                Toast.makeText(InsertRestaurantActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(InsertRestaurantActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(InsertRestaurantActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
                            Log.d("aaa", error.toString());
                        }
                    }){
                        @NonNull
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params =new HashMap<String,String>();
                            params.put("name",name);
                            params.put("address",address);
                            params.put("distance",String.valueOf(distance));
                            params.put("rating",String.valueOf(rating));
                            params.put("image",s);

                            return params;
                        }


                    };

                    Log.d("aaa",s);
                    //requestQueue.add(arrayRequest);

//                    IntroActivity.database.InsertRestaurant(name,address,distance,rating,image);
//                    Toast.makeText(InsertRestaurantActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private byte[] TransImage(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgRestaurant.getDrawable();
        Bitmap bitmap =bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
        return byteArray.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_FOLDER && resultCode==RESULT_OK && data!=null){
            Uri uri =data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imgRestaurant.setImageBitmap(bitmap);
        }
    }

    private void AnhXa(){
        edtName = findViewById(R.id.edtNameRestaurant);
        edtAddress=findViewById(R.id.edtAddressRestaurant);
        edtDistance = findViewById(R.id.edtDistanceRestaurant);
        edtRating = findViewById(R.id.edtRatingRestaurant);
        imgRestaurant = findViewById(R.id.imageViewRestaurant);
        btnClose = findViewById(R.id.buttonCloseRestaurant);
        btnSave = findViewById(R.id.buttonSaveRestaurant);
        btnLoad = findViewById(R.id.buttonLoadRestaurant);
    }
}