package com.example.ovais.medicure;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDoctorActivity extends AppCompatActivity {

    private static final String TAG = "ViewDoctorActivity";
    private static final int REQUEST_CALL = 1;
    private Button callButt;

    TextView nametv,ratingtv,spectv,feetv,addresstv,timetv,phonetv,landlinetv,emailtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);
        Log.d(TAG,"onCreate : started.");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        callButt = (Button)findViewById(R.id.callButton);
        callButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });

        getIncomingIntents();
    }

    private void getIncomingIntents(){
        Log.d(TAG,"get incoming intents: checking for incoming intents");
        if(getIntent().hasExtra("name") && getIntent().hasExtra("rating") && getIntent().hasExtra("specialization") && getIntent().hasExtra("fees") && getIntent().hasExtra("address") && getIntent().hasExtra("timings") && getIntent().hasExtra("contact") && getIntent().hasExtra("landline") && getIntent().hasExtra("email"));
        {
            Log.d(TAG,"get incoming intents: found incoming intents");

            String name = getIntent().getStringExtra("name");
            String rating = getIntent().getStringExtra("rating");
            String specialization = getIntent().getStringExtra("specialization");
            String fees = getIntent().getStringExtra("fees");
            String address = getIntent().getStringExtra("address");
            String timings = getIntent().getStringExtra("timings");
            String contact = getIntent().getStringExtra("contact");
            String landline = getIntent().getStringExtra("landline");
            String email = getIntent().getStringExtra("email");

            setData( name, rating, specialization, fees, address, timings, contact, landline, email);
        }
    }

    private void setData(String name, String rating,  String specialization, String fees,String address, String timings, String contact, String landline,String email)
    {
        Log.d(TAG,"setData: setting data");

        nametv = (TextView)findViewById(R.id.name_tv);
        nametv.setText(name);

        ratingtv= (TextView)findViewById(R.id.rating_tv);
        ratingtv.setText(rating);

        spectv = (TextView)findViewById(R.id.specialization_tv);
        spectv.setText(specialization);

        feetv = (TextView)findViewById(R.id.fees_tv);
        feetv.setText(fees);

        addresstv= (TextView)findViewById(R.id.address_tv);
        addresstv.setText(address);

        timetv= (TextView)findViewById(R.id.timing_tv);
        timetv.setText(timings);

        phonetv= (TextView)findViewById(R.id.pnum_tv);
        phonetv.setText(contact);

        landlinetv= (TextView)findViewById(R.id.landline_tv);
        landlinetv.setText(landline);

        emailtv = (TextView)findViewById(R.id.email_tv1);
        emailtv.setText(email);
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

    private void makePhoneCall()
    {
        String number = phonetv.getText().toString();

        if (number.trim().length() > 0)
        {
            if (ContextCompat.checkSelfPermission(ViewDoctorActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ViewDoctorActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }
            else
            {
                String dial =  "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else{
            Toast.makeText(ViewDoctorActivity.this,"Phone Number not found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )  {
                makePhoneCall();
            }
            else
                Toast.makeText(this,"Permission DENIED",Toast.LENGTH_SHORT).show();
        }
    }
}