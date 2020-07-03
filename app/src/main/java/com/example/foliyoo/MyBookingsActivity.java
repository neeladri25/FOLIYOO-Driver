package com.example.foliyoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyBookingsActivity extends AppCompatActivity {

Button back;
RelativeLayout relativeLayout;
TextView appText;
RelativeLayout previousbookings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        appText = findViewById(R.id.foliyoo_mybooking);
        previousbookings = findViewById(R.id.previous_bookings);

        back = findViewById(R.id.backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyBookingsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });




    }
}
