package hcmute.nhom13.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import hcmute.nhom13.myapplication.model.User;

public class SignUpActivity extends AppCompatActivity {
    ImageView imgBack;
    EditText edtPhone,edtName,edtPass,edtBirth,edtEmail;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        imgBack = findViewById(R.id.imageViewBack);
        edtPhone = findViewById(R.id.edtPhone_SignUp);
        edtName =findViewById(R.id.edtUserName_SignUp);
        edtPass =findViewById(R.id.edtPassword_SignUp);
        edtBirth =findViewById(R.id.edtDate_SignUp);
        edtEmail = findViewById(R.id.edtEmail_SignUp);
        btnSignUp = findViewById(R.id.button);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,IntroActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEditTextisEmpty()){
                    String phone =edtPhone.getText().toString();
                    String name =edtName.getText().toString();
                    String pass = edtPass.getText().toString();
                    String birth = edtBirth.getText().toString();
                    String email = edtEmail.getText().toString();
                    String url ="http://nguyenbro14092001.000webhostapp.com/qlsinhvien/User/insertUser.php";
//                    IntroActivity.database.InsertUser(phone,name,pass,birth,email);
//                    Toast.makeText(SignUpActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                    RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
                    StringRequest arrayRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.toString().equals("success")) {
                                Toast.makeText(SignUpActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this,IntroActivity.class));
                            }else{
                                Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignUpActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
                            Log.d("aaa", error.toString());
                        }
                    }){
                        @NonNull
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params =new HashMap<String,String>();
                            params.put("phone",phone);
                            params.put("password",pass);
                            params.put("name",name);
                            params.put("birth",birth);
                            params.put("email",email);


                            return params;
                        }

                    };
                    requestQueue.add(arrayRequest);


                }
                else{
                    Toast.makeText(SignUpActivity.this, "Input is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkEditTextisEmpty() {
        if(!edtPhone.getText().toString().equals("") && !edtName.getText().toString().equals("") &&
                !edtPass.getText().toString().equals("") && !edtBirth.getText().toString().equals("") &&
        !edtEmail.getText().toString().equals("")){
            return true;
        }
        return false;
    }
}