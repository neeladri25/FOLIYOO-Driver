package com.example.foliyoo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.foliyoo.MainActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText email_phone_login, password_login;
    TextView forgotpassword;
    Button login;
    ProgressBar loading;
    Context context = this;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loading = findViewById(R.id.progressbar);
        email_phone_login = findViewById(R.id.email_phone_login);
        password_login = findViewById(R.id.password_login);
        forgotpassword = findViewById(R.id.forgot_password);
        login = findViewById(R.id.login_click);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String token = MainActivity.getToken_key();
                String mEmail = email_phone_login.getText().toString();
                String mPass =  password_login.getText().toString();
                if (!mEmail.isEmpty() || !mPass.isEmpty())
                {
                    Login(mEmail,mPass,token);
                }
               else {
                   email_phone_login.setError("Please enter Email");
                   password_login.setError("Please enter Password");
               }
            }
        });



        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,PhoneAuthActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void Login(String email, String password, String token) {
        loading.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String URL_Login = "http://3.6.246.192/driver/login.php";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email",email);
            jsonBody.put("password",password);
            jsonBody.put("token",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_Login, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("id")) {
                        String message = response.getString("message");
                        loading.setVisibility(View.GONE);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        Log.d(" ",message);
                        Toast.makeText(LoginActivity.this,message, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    else if (response.has("code"))
                    {
                        int code = response.getInt("code");
                        String msg = response.getString("message");
                        loading.setVisibility(View.GONE);
                        Log.d(" ",msg);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e) {
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



    public static int getID(){
        return id;
    }
}
