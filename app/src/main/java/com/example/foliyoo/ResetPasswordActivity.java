package com.example.foliyoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.example.foliyoo.PhoneAuthActivity;
import com.example.foliyoo.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText NewPass, ConfirmPass;
    Button Done;
    ProgressBar progressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        NewPass = findViewById(R.id.new_pass);
        ConfirmPass = findViewById(R.id.confirm_new_pass);
        Done = findViewById(R.id.passdone_click);

        progressBarLoading = findViewById(R.id.progressloading);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile = PhoneAuthActivity.getPhone();
                String token = MainActivity.getToken_key();
                String newpass = NewPass.getText().toString();
                String confpass = ConfirmPass.getText().toString();


                int step = 3;

                if(!newpass.isEmpty() || confpass.isEmpty()){
                    ResetPass(newpass,confpass,mobile,step,token);
                }

                else {
                    NewPass.setError("Please Enter Password");
                    ConfirmPass.setError("Please Enter Password to confirm");
                }

            }
        });
    }

    private void ResetPass(String password, String confirm_password, String mobile, int step, String token) {
        progressBarLoading.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String URL_ForgetPassword = "http://3.6.246.192/driver/forgot_password.php";

       final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password",password);
            jsonObject.put("confirm_password",confirm_password);
            jsonObject.put("mobile",mobile);
            jsonObject.put("step",step);
            jsonObject.put("token",token);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_ForgetPassword, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (jsonObject.getString("password").equals(jsonObject.getString("confirm_password"))){
                        String message = response.getString("message");
                        progressBarLoading.setVisibility(View.GONE);
                        Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        int code = response.getInt("code");
                        String message = response.getString("message");
                        progressBarLoading.setVisibility(View.GONE);
                        Toast.makeText(ResetPasswordActivity.this, code+" "+message, Toast.LENGTH_SHORT).show();
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
