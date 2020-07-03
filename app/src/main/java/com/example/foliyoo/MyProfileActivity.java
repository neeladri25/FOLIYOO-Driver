package com.example.foliyoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyProfileActivity extends AppCompatActivity {
    Button back,editprofile;
    TextView editprofiletext;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        editprofiletext = findViewById(R.id.textView100);
        editprofile = findViewById(R.id.imageButton8);
        back = findViewById(R.id.bck);

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this,EditProfile.class);
                startActivity(intent);
            }
        });

        editprofiletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this,EditProfile.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

}
