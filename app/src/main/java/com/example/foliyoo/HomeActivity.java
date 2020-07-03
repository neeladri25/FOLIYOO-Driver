package com.example.foliyoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    Intent intent;

    NavigationView navigationView;
    LineChart mChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        toolbar = findViewById(R.id.toolbar_action);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mChart = findViewById(R.id.chart);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
        mv.setChartView(mChart);
        mChart.setMarker(mv);
        renderData();






        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });



      //  optionmenu.setOnClickListener(new View.OnClickListener() {
           // @Override

            //public void onClick(View view) {
               // if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                //    mDrawerLayout.closeDrawer(Gravity.LEFT);
             //  } else {
                //    mDrawerLayout.openDrawer(Gravity.LEFT);
              //  }

       //     }
      //  });

        }



    private void renderData() {
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setAxisMaximum(10f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        LimitLine ll1 = new LimitLine(215f, "Maximum Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(70f, "Minimum Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(350f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);

        mChart.getAxisRight().setEnabled(false);
        setData();
    }
    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(1, 50));
        values.add(new Entry(2, 100));
        values.add(new Entry(3, 80));
        values.add(new Entry(4, 120));
        values.add(new Entry(5, 110));
        values.add(new Entry(7, 150));
        values.add(new Entry(8, 250));
        values.add(new Entry(9, 190));

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Sample Data");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);
        }
    }











    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_profile:

                intent = new Intent(HomeActivity.this, MyProfileActivity.class);
                startActivity(intent);
                closeDrawer();
                break;

            case R.id.action_location:

                intent = new Intent(HomeActivity.this, LocationActivity.class);
                startActivity(intent);
                closeDrawer();
                break;

            case R.id.action_mybooking:

                intent = new Intent(HomeActivity.this, MyBookingsActivity.class);
                startActivity(intent);
                closeDrawer();
                break;

            case R.id.action_assignedbooking:
                    intent = new Intent(HomeActivity.this,AssignedBookingsActivity.class);
                    startActivity(intent);
                    closeDrawer();
                    break;

            case R.id.action_share:

                intent = new Intent(HomeActivity.this, ShareActivity.class);
                startActivity(intent);
                closeDrawer();
                break;

            case R.id.action_speedCalculator:

                intent = new Intent(HomeActivity.this, SpeedCalActivity.class);
                startActivity(intent);
                closeDrawer();
                break;

            case R.id.action_paymentcalculator:

                intent = new Intent(HomeActivity.this, PaymentCalActivity.class);
                startActivity(intent);
                closeDrawer();
                break;

            case R.id.action_rating:

                intent = new Intent(HomeActivity.this, RatingsActivity.class);
                startActivity(intent);
                closeDrawer();
                break;

            case R.id.action_support:

                intent = new Intent(HomeActivity.this, SupportActivity.class);
                startActivity(intent);
                closeDrawer();
                break;

            case R.id.action_aboutus:

                intent = new Intent(HomeActivity.this,AboutUsActivity.class);
                startActivity(intent);
                closeDrawer();
                break;

            case R.id.action_logout:

                intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                closeDrawer();
                 break;


        }
        return false;

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);

        }

        //super.onBackPressed();
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }


    public void closeDrawer()
    {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

}



