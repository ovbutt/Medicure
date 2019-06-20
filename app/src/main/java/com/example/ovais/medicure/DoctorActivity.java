package com.example.ovais.medicure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ovais.medicure.MyDoctorActivity;

public class DoctorActivity extends AppCompatActivity {

    private Button buttad, buttmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        buttad = (Button)findViewById(R.id.buttonAppdoc);
        buttmd = (Button)findViewById(R.id.buttonMyDoc);

        buttad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorActivity.this,AppointDoctorActivity.class);
                startActivity(intent);
            }
        });

        buttmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorActivity.this,MyDoctorActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
