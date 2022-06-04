package hcmute.nhom13.myapplication.InsertData;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import hcmute.nhom13.myapplication.IntroActivity;
import hcmute.nhom13.myapplication.MainActivity;
import hcmute.nhom13.myapplication.R;

public class InsertDataActivity extends AppCompatActivity {
    EditText edtId,edtName,edtPrice,edtDes;
    Button btnAddRes,btnClose,btnLoad,btnSave;
    ImageView imgFood;
    int REQUEST_FOLDER = 2222;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        AnhXa();

        btnAddRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertDataActivity.this,InsertRestaurantActivity.class));
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsertDataActivity.this, MainActivity.class));
                //IntroActivity.database.QueryData("DELETE FROM food WHERE id ='14'");
                //IntroActivity.database.QueryData("DELETE FROM food WHERE id ='15'");
                Cursor data =IntroActivity.database.getData("SELECT * FROM food");
                //byte[] hinhAnh = new byte[0];
                while (data.moveToNext()){
                    int id = data.getInt(0);
                    int id_res = data.getInt(1);
                    String name =data.getString(2);
                    double price =data.getDouble(3);
                    String des = data.getString(4);
                    //hinhAnh = data.getBlob(5);
                    Log.d("BBB",id + " " +id_res+ " "+name+ " "+price+ " "+des);
                }
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
                if(edtId.getText().toString().equals("") || edtName.getText().toString().equals("") && edtPrice.getText().toString().equals("") || edtDes.getText().toString().equals("")){
                    Toast.makeText(InsertDataActivity.this, "thất bại", Toast.LENGTH_SHORT).show();
                }
                else{
                    int id_res = Integer.parseInt(edtId.getText().toString());
                    String name = edtName.getText().toString();
                    Double price = Double.parseDouble(edtPrice.getText().toString());
                    String description = edtDes.getText().toString();
                    byte[] image = TransImage();

                    IntroActivity.database.InsertFood(id_res,name,price,description,image);
                    Toast.makeText(InsertDataActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();

//                    edtId.setText("");
//                    edtName.setText("");
//                    edtPrice.setText("");
//                    edtDes.setText("");
                }
            }
        });


    }

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
            imgFood.setImageBitmap(bitmap);
        }
    }
    private void AnhXa(){

        btnAddRes =findViewById(R.id.buttonAddRestaurant);
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDes = findViewById(R.id.edtDescription);
        btnSave = findViewById(R.id.buttonSave);
        btnClose = findViewById(R.id.buttonClose);
        btnLoad = findViewById(R.id.buttonLoadImage);
        imgFood = findViewById(R.id.imageView12);
    }

    private byte[] TransImage(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgFood.getDrawable();
        Bitmap bitmap =bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
        return byteArray.toByteArray();
    }
}