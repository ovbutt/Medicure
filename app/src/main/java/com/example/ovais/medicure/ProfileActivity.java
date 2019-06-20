package com.example.ovais.medicure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class ProfileActivity extends AppCompatActivity {

    private Button button_hb, button_all, button_bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button_bi = (Button)findViewById(R.id.buttonBi);
        button_bi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,BasicInfoActivity.class);
                startActivity(intent);
            }
        });
        button_all = (Button)findViewById(R.id.buttonAll);
        button_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,AllergyActivity.class);
                startActivity(intent);
            }
        });
        button_hb = (Button)findViewById(R.id.buttonHb);
        button_hb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,HealthBackgroundActivity.class);
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
