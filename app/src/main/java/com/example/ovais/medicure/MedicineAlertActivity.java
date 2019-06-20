package com.example.ovais.medicure;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

public class MedicineAlertActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MedicineAlertDBHelper dbHelper;
    private MedicineAlertAdapter adapter;
    private String filter = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_alert);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MedicineAlertActivity.this,AddMedicineAlert.class);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //populate recyclerview
        populaterecyclerView(filter);
    }
    private void populaterecyclerView(String filter){
        dbHelper = new MedicineAlertDBHelper(this);
        adapter = new MedicineAlertAdapter(dbHelper.peopleList(filter), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
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
