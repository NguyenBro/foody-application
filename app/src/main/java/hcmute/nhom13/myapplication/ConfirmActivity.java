package hcmute.nhom13.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import hcmute.nhom13.myapplication.model.CartFood;

public class ConfirmActivity extends AppCompatActivity {
    CircularProgressBar circularProgressBar;
    TextView txtConfirm,txtFood;
    ImageView imgBack,imgCancel;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Intent intent =getIntent();
        int item = intent.getIntExtra("item",0);
        Double total = intent.getDoubleExtra("total",0.0);
        String isPay = intent.getStringExtra("isPay");
        String title = intent.getStringExtra("title");
        ArrayList<CartFood> arrayFood = (ArrayList<CartFood>) intent.getSerializableExtra("arrayFood");
        circularProgressBar = findViewById(R.id.progress_circular1);
        txtConfirm = findViewById(R.id.textViewDescription_Confirm);
        txtFood = findViewById(R.id.textViewFood_Confirm);
        imgBack = findViewById(R.id.imageView19);
        imgCancel = findViewById(R.id.imageView199);

        //inData();
        txtConfirm.setText("Total: $"+total+" ("+item+" items) - "+isPay);
        txtFood.setText(title);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.blue));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
        circularProgressBar.setProgressBarWidth(15);
        circularProgressBar.setBackgroundProgressBarWidth(3);
        int animationDuration = 12500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(100, animationDuration); // Default duration = 1500ms


        CountDownTimer countDownTimer =new CountDownTimer(12500,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if(flag==0) {
                    IntroActivity.database.QueryData("UPDATE bill SET status='done' WHERE id='"+PaymentActivity.idBill+"'");
                    String date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
                    String time =  new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                    IntroActivity.database.InsertNotification(MainActivity.user.getPhone(),"done",total,date,time);
                    startActivity(new Intent(ConfirmActivity.this, SuccessActivity.class));
                }
                else{
                    Toast.makeText(ConfirmActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        };
        countDownTimer.start();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //countDownTimer.cancel();
                flag=1;
                Intent inte = new Intent(ConfirmActivity.this,PaymentActivity.class);
                inte.putExtra("arrayFood",arrayFood);
                startActivity(inte);
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                IntroActivity.database.QueryData("UPDATE bill SET status='failed' WHERE id='"+PaymentActivity.idBill+"'");
                String date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
                String time =  new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                IntroActivity.database.InsertNotification(MainActivity.user.getPhone(),"failed",total,date,time);
                startActivity(new Intent(ConfirmActivity.this,FailedActivity.class));
            }
        });
    }

    private void inData(){
        Cursor data = IntroActivity.database.getData("SELECT * FROM bill");
        while (data.moveToNext()){
            Log.d("INDATABASE",data.getDouble(2)+" " +data.getDouble(3)+" "+data.getDouble(4)+" " + data.getString(5)+" " +data.getString(6)+" "+data.getString(7));
        }

        Cursor data12 = IntroActivity.database.getData("SELECT * FROM desBill");
        while (data12.moveToNext()){
            Log.d("INDATABASE",data12.getInt(2)+" ");
        }
    }
}