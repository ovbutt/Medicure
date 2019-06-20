package com.example.ovais.medicure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity implements View.OnClickListener {


    //Defining views
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView forgot_pass, sign_up;

//    RingProgressBar ringProgressBar;
//
//    Handler myHandler;
//
//    int progress = 0;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        //ringProgressBar = (RingProgressBar)findViewById(R.id.rprgrsbar1);

        forgot_pass = (TextView) findViewById(R.id.forgot_pass);
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotIntent = new Intent(LoginActivity.this, Forgot_Pass.class);
                startActivity(forgotIntent);
            }
        });

        sign_up = (TextView) findViewById(R.id.signup_tv);
        sign_up.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent signupIntent = new Intent(LoginActivity.this, Signup.class);
                        startActivity(signupIntent);
                        finish();
                    }
                }
        );

        //Initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);


        //Adding click listener
        buttonLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference

        SharedPreferences sharedPreferences = getSharedPreferences(com.example.ovais.medicure.Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(com.example.ovais.medicure.Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true

        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);
            finish();
            //ringProgress ();

        }
    }

    private void login() {
        //Getting values from edit texts
        final String user_email = editTextEmail.getText().toString().trim();
        final String user_pass = editTextPassword.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.example.ovais.medicure.Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if (response.equalsIgnoreCase(com.example.ovais.medicure.Config.LOGIN_SUCCESS)) {
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = getSharedPreferences(com.example.ovais.medicure.Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(com.example.ovais.medicure.Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(com.example.ovais.medicure.Config.EMAIL_SHARED_PREF, user_email);

                            //Saving values to editor
                            editor.commit();

                            //Starting landing activity
                            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            //ringProgress ();

                        } else {
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(com.example.ovais.medicure.Config.KEY_MAIL, user_email);
                params.put(com.example.ovais.medicure.Config.KEY_PASSWORD, user_pass);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick (View v){
        // validations
        final String user_email = editTextEmail.getText().toString().trim();
        final String validEmail ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        //validations
        if (editTextEmail.getText().length()==0) {
            editTextEmail.requestFocus();
            editTextEmail.setError(Html.fromHtml("<font color='white' >Please Enter Email</font>"));
        }

        else if(!user_email.matches(validEmail))
        {
            editTextEmail.requestFocus();
            editTextEmail.setError(Html.fromHtml("<font color='white'>Please Enter A Valid Email</font>"));
        }

        else if (editTextPassword.getText().length()==0) {
            editTextPassword.requestFocus();
            editTextPassword.setError(Html.fromHtml("<font color='white'>Please Enter Password</font>"));
        }
        else if(v == buttonLogin) {
            //Calling the login function
            login();
        }
    }

}