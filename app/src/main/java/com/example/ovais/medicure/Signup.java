package com.example.ovais.medicure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Signup extends Activity implements View.OnClickListener {

    private TextView textView_si;
    EditText etname, etemail, etpass, etmobile, etconfirmpass;
    private Button signup_butt;

    String getuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        etname = (EditText) findViewById(R.id.etname);
        etemail = (EditText) findViewById(R.id.etemail);
        etpass = (EditText) findViewById(R.id.etpass);
        etmobile = (EditText) findViewById(R.id.etmobile);
        etconfirmpass = (EditText) findViewById(R.id.etcnfrmpass);
        textView_si = (TextView)findViewById(R.id.textView_si);
        textView_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinIntent = new Intent(Signup.this, LoginActivity.class);
                startActivity(signinIntent);
                finish();
            }
        });

        signup_butt = (Button) findViewById(R.id.button_signup);
        signup_butt.setOnClickListener(this);
        }

    // Check if username already exist or not
    public void getData() {
        getuser = etemail.getText().toString().trim();
        String id = getuser;

        String url = Config.URL_VERIFY_USER+id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signup.this, error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // get already exist user and parse the JSON
    private void showJSON(String response){
        String username_result="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            username_result = collegeData.getString(Config.VF_USER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(getuser.equals(username_result))
        {
            etemail.requestFocus();
            etemail.setError("Username Already Exist");
        }
        else
        {
            registerUser();
        }
    }
    //We have declared the REGISTER_URL and the KEYS with which the data is to be sent to the server
    //Now lets complete our registerUser method
    //First we will get the values which is to be sent

    //Validating User

    private void registerUser() {



        final String user_nicename = etname.getText().toString().trim();
        final String user_email = etemail.getText().toString().trim();
        final String user_pass = etpass.getText().toString().trim();
        final String user_mbl = etmobile.getText().toString().trim();
        {
            //Now will create a StringRequest almost the same as we did before

            class UpdateEmployee extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(Signup.this, "Signing You Up", "Please Wait...", false, false);
                }
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    if (s.equals("Successfully Register")) {
                        Toast.makeText(Signup.this, "Registration Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Signup.this, NavigationActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(Signup.this, s, Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                protected String doInBackground(Void... params) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.SU_USER_NAME, user_nicename);
                    hashMap.put(Config.SU_USER_EMAIL, user_email);
                    hashMap.put(Config.SU_USER_MOBILE, user_mbl);
                    hashMap.put(Config.SU_USER_PASS, user_pass);

                    RequestHandler rh = new RequestHandler();
                    String s = rh.sendPostRequest(Config.URL_CREATE_USER, hashMap);
                    return s;
                }
            }
            UpdateEmployee ue = new UpdateEmployee();
            ue.execute();
        }
    }

    @Override
    public void onClick(View v) {

        final String validEmail ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String user_email = etemail.getText().toString().trim();
        /**
         //         * Validation
         //
         //         */
        boolean invalid = false;
        if (etname.getText().length()==0) {
            etname.requestFocus();
            etname.setError(Html.fromHtml("<font color='white'>Please Enter Your Name</font>"));
        }
        else if (etemail.getText().length()==0) {
            etemail.requestFocus();
            etemail.setError(Html.fromHtml("<font color='white'>Please Enter Email</font>"));
        }
        else if(!user_email.matches(validEmail))
        {
            etemail.requestFocus();
            etemail.setError(Html.fromHtml("<font color='white'>Please Enter A Valid Email</font>"));
        }
        else if (etpass.getText().length()==0) {
            etpass.requestFocus();
            etpass.setError(Html.fromHtml("<font color='white'>Please Enter Password</font>"));
        }
        else if (etpass.getText().length() <= 5) {
            etpass.requestFocus();
            etpass.setError(Html.fromHtml("<font color='white'>Password Should be 6 Digits Atleast</font>"));
        }
        else if (etconfirmpass.getText().length()==0) {
            etconfirmpass.requestFocus();
            etconfirmpass.setError(Html.fromHtml("<font white='red'>Please Retype Password</font>"));
        }
        else if (etmobile.getText().length() < 11) { //if mobile number entered is less than 11 digits
            invalid = true;
            etmobile.requestFocus();
            etmobile.setError(Html.fromHtml("<font color='white'>Please Enter A Valid Mobile Number</font>"));
        }
        else if (etmobile.getText().length() > 11) { //if mobile number is greater than 11 digits
            invalid = true;
            etmobile.requestFocus();
            etmobile.setError(Html.fromHtml("<font color='white'>Please Enter Valid Mobile No</font>"));
        }

            else if (v == signup_butt) {

            checkPassword();
        }
    }
    // Compare password if match then good else shows error
    private void checkPassword() {
        final String user_pass = etpass.getText().toString().trim();
        final String user_cnfrmpass = etconfirmpass.getText().toString().trim();
        if (user_pass.equals(user_cnfrmpass)) {
            getData();
        } else {
            etconfirmpass.requestFocus();
            etconfirmpass.setError("Password Do Not Match");

            // Clear the password fields
            etpass.setText("");
            etconfirmpass.setText("");
        }
    }
}