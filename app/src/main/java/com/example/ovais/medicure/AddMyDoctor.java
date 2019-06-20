package com.example.ovais.medicure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ovais.medicure.DoctorsDBHelper;
import com.example.ovais.medicure.Doctors;
import com.example.ovais.medicure.R;

//MyDoctor4

public class AddMyDoctor extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mSpecEditText;
    private EditText mPhNumEditText;
    private EditText mEmailEditText;
    private EditText mAddressEditText;
    private EditText mLandLineEditText;
    private Button mAddBtn;

    private DoctorsDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_doctor);

        //init
        mNameEditText = (EditText)findViewById(R.id.docName);
        mSpecEditText = (EditText)findViewById(R.id.docSpec);
        mPhNumEditText = (EditText)findViewById(R.id.docPhoneNum);
        mLandLineEditText = (EditText)findViewById(R.id.docLandLine);
        mEmailEditText = (EditText)findViewById(R.id.docEmail);
        mAddressEditText = (EditText)findViewById(R.id.docAddress);
        mAddBtn = (Button)findViewById(R.id.addNewUserButton);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //listen to add button click
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                savePerson();
                Intent intent = new Intent(AddMyDoctor.this , MyDoctorActivity.class);
                startActivity(intent);
                finish();

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

    private void savePerson(){
        String name = mNameEditText.getText().toString().trim();
        String specialization = mSpecEditText.getText().toString().trim();
        String phnum = mPhNumEditText.getText().toString().trim();
        String landline = mLandLineEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String address = mAddressEditText.getText().toString().trim();
        dbHelper = new DoctorsDBHelper(this);

        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }

        if(specialization.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an age", Toast.LENGTH_SHORT).show();
        }

        if(phnum.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an occupation", Toast.LENGTH_SHORT).show();
        }

        if(landline.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an image link", Toast.LENGTH_SHORT).show();
        }
        if(email.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an image link", Toast.LENGTH_SHORT).show();
        }
        if(address.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an image link", Toast.LENGTH_SHORT).show();
        }

        //create new doctors
        Doctors doctors = new Doctors(name, specialization, phnum, landline, email, address);
        dbHelper.saveNewPerson(doctors);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete

    }

}