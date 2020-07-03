package com.example.foliyoo;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.foliyoo.MainActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneAuthActivity extends AppCompatActivity {

    Button next_auth, back;
    EditText et;
    ProgressBar loading;

    public static int otp;
    public static String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        back = findViewById(R.id.back_btn);
        next_auth = findViewById(R.id.next_phnauth);
        et = findViewById(R.id.phoneAuth);
        loading = findViewById(R.id.ldngbar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneAuthActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        next_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone;
                phone = et.getText().toString().trim();

                String token = MainActivity.getToken_key();

                int step = 1;

                if (phone.length() < 10 || !phone.isEmpty()) {
                    et.setError("Please Enter Phone Number");
                } else {
                        Phone_ForgotPass(phone,step,token);
                }
            }
        });

    }

    private void Phone_ForgotPass(String mobile, int step, String token) {
        loading.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String URL_ForgotPassword = "http://3.6.246.192/driver/forgot_password.php";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("mobile",mobile);
            jsonBody.put("step",step);
            jsonBody.put("token",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_ForgotPassword, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("otp")){
                        otp = response.getInt("otp");
                        String message = response.getString("message");
                        loading.setVisibility(View.GONE);
                        Intent intent = new Intent(PhoneAuthActivity.this,OTP.class);
                        startActivity(intent);
                        Toast.makeText(PhoneAuthActivity.this, otp+" "+message, Toast.LENGTH_SHORT).show();
                    }
                    else if (response.has("code")){
                        int code = response.getInt("code");
                        String msg = response.getString("message");
                        loading.setVisibility(View.GONE);
                        Toast.makeText(PhoneAuthActivity.this, code+" "+msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
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

    public static int getOtp(){
        return otp;
    }

    public static String getPhone(){
        return phone;
    }
}
