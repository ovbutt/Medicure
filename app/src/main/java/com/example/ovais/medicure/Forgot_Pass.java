package com.example.ovais.medicure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Forgot_Pass extends Activity implements View.OnClickListener{

    EditText etname, etemail,textView,etpass, etconfirmpass;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__pass);

        etname = (EditText) findViewById(R.id.pass);
        etpass = (EditText) findViewById(R.id.cnfpass);
        etconfirmpass = (EditText) findViewById(R.id.retype);
        update=(Button)findViewById(R.id.update);
        update.setOnClickListener(this);
    }

    private void Changepass() {
        final String user_nicename = etname.getText().toString().trim();
        final String user_pass = etpass.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Forgot_Pass.this, "Password Updating...", "Please Wait...", false, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("Password Reset Successfully")){
                    Toast.makeText(Forgot_Pass.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Forgot_Pass.this,LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Forgot_Pass.this, s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.R_USER_ID, user_nicename);
                hashMap.put(Config.R_USER_PASS, user_pass);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_USER_CHANGE_PASS, hashMap);
                return s;

            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    @Override
    public void onClick(View view) {
        final String user_email = etname.getText().toString().trim();

        //validations
        if (etname.getText().length()==0) {
            etname.requestFocus();
            etname.setError(Html.fromHtml("<font color='white'>Please Enter Username</font>"));
        }

        else if (etpass.getText().length()==0) {
            etpass.requestFocus();
            etpass.setError(Html.fromHtml("<font color='white'>Please Enter Password</font>"));
        }
        else if (etconfirmpass.getText().length()==0) {
            etconfirmpass.requestFocus();
            etconfirmpass.setError(Html.fromHtml("<font color='white'>Please Retype Password</font>"));
        }
        else if(view == update) {
            checkPassword();
        }

    }

    // check for passwords if it matches or not
    private void checkPassword() {
        final String user_pass = etpass.getText().toString().trim();
        final String user_cnfrmpass = etconfirmpass.getText().toString().trim();

        if (user_pass.equals(user_cnfrmpass)) {
            Changepass();
        } else {
            etconfirmpass.requestFocus();
            etconfirmpass.setError(Html.fromHtml("<font color='white'>Password Do Not Match</font>"));

            // Clear the password fields
            etpass.setText("");
            etconfirmpass.setText("");

        }

    }
}
