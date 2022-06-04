package hcmute.nhom13.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hcmute.nhom13.myapplication.Database.Database;
import hcmute.nhom13.myapplication.Volley.MyJsonArrayRequest;
import hcmute.nhom13.myapplication.model.User;

public class IntroActivity extends AppCompatActivity {
    Button btnStart;
    public static Database database;
    TextView txtSignUp;
    EditText edtPhone,edtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        btnStart = findViewById(R.id.btnStart);
        txtSignUp = findViewById(R.id.textViewSignUp);
        edtPass = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtUserName);
        edtPhone.setText("0362963051");
        edtPass.setText("123456");

        database =new Database(this,"foody.sqlite",null,1);

        //Tao BAng
        //database.QueryData("DROP TABLE desBill");
        database.QueryData("CREATE TABLE IF NOT EXISTS restaurant(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),address VARCHAR(50),distance DOUBLE,rating DOUBLE,res_image BLOB)");
        database.QueryData("CREATE TABLE IF NOT EXISTS food(id INTEGER PRIMARY KEY AUTOINCREMENT,id_res INTEGER,name VARCHAR(50),double DOUBLE,description VARCHAR(250),food_image BLOB)");
        database.QueryData("CREATE TABLE IF NOT EXISTS cart(id_user VARCHAR(15),id_food INTEGER ,quantity INTEGER,PRIMARY KEY(id_user,id_food))");
        database.QueryData("CREATE TABLE IF NOT EXISTS user(phone VARCHAR(15) PRIMARY KEY,name VARCHAR(50),password VARCHAR(50),birth VARCHAR(10),email VARCHAR(50))");
        database.QueryData("CREATE TABLE IF NOT EXISTS bill(id INTEGER PRIMARY KEY AUTOINCREMENT,id_user VARCHAR(15),price DOUBLE,fee DOUBLE,voucher DOUBLE,note VARCHAR(150),date VARCHAR(15),time VARCHAR(15),status VARCHAR(10))");
        database.QueryData("CREATE TABLE IF NOT EXISTS desBill(id INTEGER PRIMARY KEY AUTOINCREMENT,id_bill INTEGER,id_food INTEGER,quantity INTEGER)");
        database.QueryData("CREATE TABLE IF NOT EXISTS notification(id INTEGER PRIMARY KEY AUTOINCREMENT,id_user VARCHAR(15),type VARCHAR(15),total DOUBLE,date VARCHAR(15),time VARCHAR(15))");


        //database.QueryData("INSERT INTO user VALUES('0362963051','nguyen','123456','14/09/2001','doanthanh14092001@gmail.com')");
        //Them Du Lieu
        //database.QueryData("DELETE FROM bill");
        //database.QueryData("DELETE FROM food WHERE id='30'");

//        Cursor data =database.getData("SELECT * FROM user");
//        while (data.moveToNext()){
//            String name = data.getString(1);
//            Log.d("DATATTT",name);
//        }
        //inData();


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtPass.getText().toString().equals("") && !edtPhone.getText().toString().equals(""))
                {
                    String phone =edtPhone.getText().toString();
                    String pass = edtPass.getText().toString();
                    String url ="http://nguyenbro14092001.000webhostapp.com/qlsinhvien/User/checkUserByIdAndPassword.php";
//                    Cursor data= database.getData("SELECT * FROM user WHERE phone ='"+phone+"' AND password='"+pass+"'");
//                    int dem=0;
//                    User user =null;
//                    while (data.moveToNext()){
//                        user =new User(data.getString(0),data.getString(1),data.getString(2),data.getString(3),data.getString(4));
//                        dem++;
//                    }
//                    if(dem != 0){
//                        Toast.makeText(IntroActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(IntroActivity.this,MainActivity.class);
//                        intent.putExtra("user",user);
//                        startActivity(intent);
//                    }
//                    else{
//                        Toast.makeText(IntroActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                        edtPass.setText("");
//                    }

                    RequestQueue requestQueue = Volley.newRequestQueue(IntroActivity.this);
                    StringRequest arrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(!response.toString().equals("[]")) {
                                try {
                                    //Toast.makeText(IntroActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                    JSONArray arrayResponse = new JSONArray(response);
                                User user =new User(arrayResponse.getJSONObject(0).getString("PHONE"),arrayResponse.getJSONObject(0).getString("NAME"),
                                        arrayResponse.getJSONObject(0).getString("PASSWORD"),arrayResponse.getJSONObject(0).getString("BIRTH"),
                                        arrayResponse.getJSONObject(0).getString("EMAIL"));
                                Toast.makeText(IntroActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Toast.makeText(IntroActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                edtPass.setText("");
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(IntroActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
                            Log.d("aaa", error.toString());
                        }
                    }){
                        @NonNull
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params =new HashMap<String,String>();
                            params.put("phone",phone);
                            params.put("password",pass);

                            return params;
                        }

                    };
                    requestQueue.add(arrayRequest);

                }
                else{
                    Toast.makeText(IntroActivity.this, "Phone And Password is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this,SignUpActivity.class));
            }
        });

    }

    private void inData(){
        Cursor data = IntroActivity.database.getData("SELECT * FROM bill");
        while (data.moveToNext()){
            Log.d("NNN",data.getDouble(2)+" " +data.getDouble(3)+" "+data.getDouble(4)+" " + data.getString(5)+" " +data.getString(6)+" "+data.getString(7)+data.getString(8));
        }

        Cursor data12 = IntroActivity.database.getData("SELECT * FROM desBill");
        while (data12.moveToNext()){
            Log.d("NNN",data12.getInt(2)+" ");
        }
    }

}