package com.example.foliyoo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;


public class RatingsActivity extends AppCompatActivity {

    RatingBar r1,r2,r3,r4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        r1 = findViewById(R.id.overallrating);
        r2 = findViewById(R.id.drivingrating);
        r3 = findViewById(R.id.vehiclerating);
        r4 = findViewById(R.id.behaviourrating);

        r1.setNumStars(5);
        r2.setNumStars(5);
        r3.setNumStars(5);
        r4.setNumStars(5);
    }
}
