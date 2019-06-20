package com.example.ovais.medicure;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BasicInfoActivity extends AppCompatActivity {

    private static final String TAG = "BasicInfoActivity";
    String insert_URL = "http://ovaisbutt.000webhostapp.com/medicure/postProfile.php";

    private Spinner genderspinner;
    private EditText mDisplayDate, bw_et, bh_et;
    private TextView bmitv, bmictv , emailtv, nametv;
    private Button btnsave;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    float bmi;
    String useremail;
    String name1, email1, dob, gender, bodyweight, bodyheight, bmi1 ,bmicat;

    RequestQueue requestQueue;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

        emailtv = (TextView)findViewById(R.id.email_tv);
        nametv = (TextView)findViewById(R.id.name_tv);
        genderspinner = (Spinner)findViewById(R.id.spinner_gender);
        btnsave = (Button)findViewById(R.id.butt_save);
        bh_et = (EditText)findViewById(R.id.bodyHeight_et);
        bw_et = (EditText) findViewById(R.id.bodyWeight_et);
        bmitv = (TextView) findViewById(R.id.bmi_tv);
        bmictv = (TextView) findViewById(R.id.bmic_tv);
        mDisplayDate = (EditText) findViewById(R.id.tvDate);

        getProfile();

        useremail = (SharedPrefManager.getInstance(this).getUserEmail().trim());
        //useremail = "ovaisbutt@gmail.com";
        emailtv.setText(useremail);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calanderButton();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBMI();

                StringRequest request = new StringRequest(Request.Method.POST, insert_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BasicInfoActivity.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BasicInfoActivity.this,"Error:" + error,Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("dob", mDisplayDate.getText().toString());
                        parameters.put("gender", genderspinner.getSelectedItem().toString());
                        parameters.put("bodyweight", bw_et.getText().toString());
                        parameters.put("bodyheight", bh_et.getText().toString());
                        parameters.put("bmi", bmitv.getText().toString());
                        parameters.put("bmicat", bmictv.getText().toString());
                        parameters.put("useremail",useremail);

                        return parameters;
                    }
                };
                    MySingelton.getInstance(BasicInfoActivity.this).addToReuestQueue(request);
            }

        });
        setSpinner();
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setSpinner()
    {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String[] gender = {"Male","Female","Not Specified"};

        Spinner spinner = (Spinner)findViewById(R.id.spinner_gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,gender);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    public void calculateBMI()
    {
        float m = (float) 0.3048;
        String weight = bw_et.getText().toString();
        final float wt = Float.parseFloat(weight);

        String height = bh_et.getText().toString();
        final float ht = Float.parseFloat(height)*m;

        bmi = wt/(ht*ht);

        bmitv.setText(Float.toString(bmi));

        setBMIC();
    }

    public void setBMIC()
    {
        if (bmi<=15)
        {
            bmictv.setText("Very severely underweight");
            bmictv.setBackgroundColor(Color.YELLOW);

        }

        else if(bmi<=16 && bmi>=15)
        {
            bmictv.setText("Severely underweight");
            bmictv.setBackgroundColor(Color.YELLOW);

        }
        else if(bmi<=18.5 && bmi>16)
        {
            bmictv.setText("Underweight");
            bmictv.setBackgroundColor(Color.YELLOW);

        }
        else if(bmi<=25 && bmi>18.5)
        {
            bmictv.setText("Normal (healthy weight)");
            bmictv.setBackgroundColor(Color.GREEN);

        }
        else if(bmi<=30 && bmi>25)
        {
            bmictv.setText("Overweight");
            bmictv.setBackgroundColor(Color.RED);


        }
        else if(bmi<=35 && bmi>30)
        {
            bmictv.setText("Moderately obese");
            bmictv.setBackgroundColor(Color.RED);
        }
        else if(bmi<=40 && bmi>35)
        {
            bmictv.setText("Severely obese");
            bmictv.setBackgroundColor(Color.RED);
        }
        else if(bmi>40)
        {
            bmictv.setText("Very severely obese");
            bmictv.setBackgroundColor(Color.RED);
        }

    }

    public void calanderButton()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                BasicInfoActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void getProfile ()
    {
        String showing_URL = "http://ovaisbutt.000webhostapp.com/medicure/getProfile.php?useremail="+useremail;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, showing_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray profile = new JSONArray(response);
                    for (int i = 0; i < profile.length(); i++) {
                        JSONObject profileObject = profile.getJSONObject(i);

                        name1 = profileObject.getString("user_nicename");
                        //email1 = profileObject.getString("user_email");
                        dob = profileObject.getString("dob");
                        gender = profileObject.getString("gender");
                        bodyweight = profileObject.getString("bodyweight");
                        bodyheight = profileObject.getString("bodyheight");
                        bmi1 = profileObject.getString("bmi");
                        bmicat = profileObject.getString("bmicat");

                        nametv.setText(name1);
                        //emailtv.setText(email1);
                        mDisplayDate.setText(dob);
                        bw_et.setText(bodyweight);
                        bh_et.setText(bodyheight);
                        bmitv.setText(bmi1);
                        bmictv.setText(bmicat);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BasicInfoActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        MySingelton.getInstance(BasicInfoActivity.this).addToReuestQueue(stringRequest);
        Volley.newRequestQueue(this).add(stringRequest);

    }
}