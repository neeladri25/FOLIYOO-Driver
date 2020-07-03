package com.example.foliyoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foliyoo.PhoneAuthActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.GONE;

public class OTP extends AppCompatActivity {

    EditText et1,et2,et3,et4,et5,et6;
    Button back,submit;
    ProgressBar prgrsbarloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        submit = findViewById(R.id.submit_clk);
        back = findViewById(R.id.back__btn);
        et1 = findViewById(R.id.otpET1);
        et2 = findViewById(R.id.otpET2);
        et3 = findViewById(R.id.otpET3);
        et4 = findViewById(R.id.otpET4);
        et5 = findViewById(R.id.otpET5);
        et6 = findViewById(R.id.otpET6);
        prgrsbarloading = findViewById(R.id.prgrsldng);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (et1.getText().toString().length() == 1){
                        et2.requestFocus();
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et2.getText().toString().length() == 0){
                    et1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et2.getText().toString().length() == 1){
                    et3.requestFocus();
                }
            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et3.getText().toString().length() == 0){
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et3.getText().toString().length() == 1){
                    et4.requestFocus();
                }
            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et4.getText().toString().length() == 0){
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et4.getText().toString().length() == 1){
                    et5.requestFocus();
                }
            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et5.getText().toString().length() == 0){
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et5.getText().toString().length() == 1){
                    et6.requestFocus();
                }
            }
        });

        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et6.getText().toString().length() == 0){
                    et5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String no1 = et1.getText().toString().trim();
                String no2 = et2.getText().toString().trim();
                String no3 = et3.getText().toString().trim();
                String no4 = et4.getText().toString().trim();
                String no5 = et5.getText().toString().trim();
                String no6 = et6.getText().toString().trim();

                String eOtp = no1+no2+no3+no4+no5+no6;

                int otpnow = Integer.parseInt(eOtp);

                int recvotp = PhoneAuthActivity.getOtp();

                String token = MainActivity.getToken_key();

                int step = 2;

                if (no1.length()==0||no2.length()==0||no3.length()==0||no4.length()==0||no5.length()==0||no6.length()==0){
                    Toast.makeText(OTP.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    OTP_ForgotPass(recvotp,otpnow,step,token);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTP.this,PhoneAuthActivity.class);
                startActivity(intent);
            }
        });
    }

    private void OTP_ForgotPass(final int otp, final int user_otp, int step, String token) {
        prgrsbarloading.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String URL_ForgotPassword = "http://3.6.246.192/driver/forgot_password.php";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("otp",otp);
            jsonBody.put("user_otp",user_otp);
            jsonBody.put("step",step);
            jsonBody.put("token",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_ForgotPassword, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (jsonBody.getInt("otp") == jsonBody.getInt("user_otp")){
                        String message = response.getString("message");
                        prgrsbarloading.setVisibility(GONE);
                        Toast.makeText(OTP.this, message, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(OTP.this,ResetPasswordActivity.class);
                        startActivity(intent);
                    }
                    else {
                        int code = response.getInt("code");
                        String msg = response.getString("message");
                        prgrsbarloading.setVisibility(GONE);
                        Toast.makeText(OTP.this, code+" "+msg, Toast.LENGTH_SHORT).show();
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
}
