package hcmute.nhom13.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hcmute.nhom13.myapplication.model.User;

public class UserProfileActivity extends AppCompatActivity {
    TextView username;
    EditText mail,phone,date;
    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        username = findViewById(R.id.username_profile);
        phone =findViewById(R.id.phone_profile);
        mail = findViewById(R.id.mail_profile);
        date = findViewById(R.id.date_profile);
        btnLogout =findViewById(R.id.logout_button);
        User user = MainActivity.user;

        username.setText(user.getName());
        mail.setText(user.getEmail());
        phone.setText(user.getPhone());
        date.setText(user.getBirth());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfileActivity.this,IntroActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}