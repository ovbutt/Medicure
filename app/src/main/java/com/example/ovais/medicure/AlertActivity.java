package com.example.ovais.medicure;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AlertActivity extends AppCompatActivity {

    private Button buttmed, buttdiet, buttapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttmed = (Button)findViewById(R.id.buttonMed);
        buttdiet = (Button)findViewById(R.id.buttonDiet);
        buttapp = (Button)findViewById(R.id.buttonApp);

        buttmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlertActivity.this,MedicineAlertActivity.class);
                startActivity(intent);
            }
        });
        buttdiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlertActivity.this,DietAlertActivity.class);
                startActivity(intent);
            }
        });
        buttapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlertActivity.this,AppointmentAlertActivity.class);
                startActivity(intent);
            }
        });
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
