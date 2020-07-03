package com.example.foliyoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class AcceptReject_BookingActivity extends AppCompatActivity {

    Button accept_click,cancel_click;
    public static int BookingID;
    ProgressBar progressBar;
    TextView bookingIDdisp;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_reject__booking);

        accept_click = findViewById(R.id.accept);
        cancel_click = findViewById(R.id.cancel);
        progressBar = findViewById(R.id.pbloading);
        bookingIDdisp = findViewById(R.id.bookingid);
        relativeLayout = findViewById(R.id.bookingaccept);


        int driver_id = LoginActivity.getID();
        String token = MainActivity.getToken_key();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String URL_Booking = "http://3.6.246.192/driver/bookings.php?driver_id="+driver_id+"&token="+token;


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, URL_Booking, null, new Response.Listener<JSONObject>() {
            @Override
        public void onResponse(JSONObject response) {
                try {
                    if (response.has("bookings")) {
                        JSONObject jsonObject = new JSONObject();
                        JSONArray array = jsonObject.getJSONArray("bookings");
                        for (int i = 0; i < array.length(); i++) {
                            BookingID = array.getJSONObject(i).getInt("booking_id");
                            String PickupPoint = array.getJSONObject(i).getString("pickup_point");
                            String DeliveryPoint = array.getJSONObject(i).getString("delivery_point");
                            relativeLayout.setVisibility(View.VISIBLE);
                        }

                        Intent intent = new Intent(AcceptReject_BookingActivity.this,ConfirmationActivity.class);
                        startActivity(intent);
                    }
                    else if (response.has("code")){
                        int code = response.getInt("code");
                        String message = response.getString("message");
                        Toast.makeText(AcceptReject_BookingActivity.this, code+" "+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);



        bookingIDdisp.setText(BookingID);


        accept_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int booking_id = AcceptReject_BookingActivity.BookingID;
                String action = "accept";
                String token = MainActivity.getToken_key();

                AcceptBooking(booking_id,action,token);
            }
        });


    }

    private void AcceptBooking(int booking_id, String action, String token) {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        String URL_UpdateBooking = "http://3.6.246.192/driver/update_booking.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("booking_id",booking_id);
            jsonObject.put("action",action);
            jsonObject.put("token",token);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        JsonObjectRequest objectRequest = new JsonObjectRequest(URL_UpdateBooking, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
        try {
                if (response.has("otp")){
                    int otp = response.getInt("otp");
                    String message = response.getString("message");
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AcceptReject_BookingActivity.this, otp+" "+message, Toast.LENGTH_SHORT).show();

            }

                else if (response.has("code")){
                    int code = response.getInt("code");
                    String message = response.getString("message");
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AcceptReject_BookingActivity.this,code+" "+message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            e.printStackTrace();
        }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }
        );rq.add(objectRequest);

    }


}

