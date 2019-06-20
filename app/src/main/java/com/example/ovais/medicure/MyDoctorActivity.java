package com.example.ovais.medicure;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ovais.medicure.R;

//MyDoctor5

public class MyDoctorActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DoctorsDBHelper dbHelper;
    private DoctorsAdapter adapter;
    private String filter = "";
    private TextView plustv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctor);

        plustv = (TextView)findViewById(R.id.textViewplus);
        //setTextView();
        //initialize the variables
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //populate recyclerview
        populaterecyclerView(filter);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddUserActivity();
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


    private void populaterecyclerView(String filter){
        dbHelper = new DoctorsDBHelper(this);
        adapter = new DoctorsAdapter(dbHelper.peopleList(filter), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        setTextView();

    }

    private void goToAddUserActivity(){
        Intent intent = new Intent(MyDoctorActivity.this, AddMyDoctor.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void setTextView()
    {
        if (mRecyclerView.getAdapter().getItemCount() == 0)
        {
            plustv.setVisibility(View.VISIBLE);

        }
        else
        {
            plustv.setVisibility(View.GONE);
        }
    }
}
