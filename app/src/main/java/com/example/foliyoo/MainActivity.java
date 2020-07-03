package com.example.foliyoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
   Handler handler;
   public static String token_key;
   SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if (preferences.getLong("expires_at", System.currentTimeMillis()) <= System.currentTimeMillis()) {

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL = "http://3.6.246.192/auth/token.php";
            JSONObject jsonBody = new JSONObject();
            editor = preferences.edit();

            try {
                jsonBody.put("username", "foliyoo_rest");
                jsonBody.put("password", "@Foliyoo5*");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("correct", jsonBody.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Date d = simpleDateFormat.parse(response.getString("expires_at"));


                        editor.putString("token", response.getString("token"));
                        editor.putLong("expires_at", d.getTime());
                        editor.commit();
                        Log.d("Show:",response.toString());

                        token_key = response.getString("token").toString();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.e("Error: ", error.getMessage());
                        }
                    });

            requestQueue.add(jsonObjectRequest);
        }
        else {
            Log.d("thus",""+preferences.getLong("expires_at",0));
        }
    }

    public static String getToken_key(){
        return token_key;
    }


}
