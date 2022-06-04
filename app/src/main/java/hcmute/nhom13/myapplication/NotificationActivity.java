package hcmute.nhom13.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import hcmute.nhom13.myapplication.adapter.NotificationAdapter;
import hcmute.nhom13.myapplication.model.Notification;

public class NotificationActivity extends AppCompatActivity {
    ImageView imgBack;
    ListView ltvNotification;
    ArrayList<Notification> arrayNotification;
    NotificationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        imgBack  =findViewById(R.id.imageView24);
        ltvNotification = findViewById(R.id.listViewNotification);
        arrayNotification =new ArrayList<>();
        MainActivity.numNoti=0;
        Cursor data = IntroActivity.database.getData("SELECT * FROM notification WHERE id_user ='"+MainActivity.user.getPhone()+"' ORDER BY id DESC");
        while (data.moveToNext()){
            int id = data.getInt(0);
            String type = data.getString(2);
            Double total = data.getDouble(3);
            String date = data.getString(4);
            String time = data.getString(5);
            Notification notification =new Notification(id,MainActivity.user.getPhone(),type,date,time,total);
            arrayNotification.add(notification);
            MainActivity.numNoti++;

        }

//        arrayNotification.add(new Notification(1,"a","done","2022/05/08","20:45:20",40.6));
//        arrayNotification.add(new Notification(2,"b","failed","2022/05/09","00:10:20",140.6));
//        arrayNotification.add(new Notification(3,"c","ordering","2022/05/05","20:45:20",40.6));

        adapter =new NotificationAdapter(this,R.layout.adapter_notification,arrayNotification);
        ltvNotification.setAdapter(adapter);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationActivity.this,MainActivity.class));
            }
        });
    }
}