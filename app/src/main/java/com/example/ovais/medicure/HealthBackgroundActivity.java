package com.example.ovais.medicure;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class HealthBackgroundActivity extends AppCompatActivity {

    RadioGroup radioGroupbp, radioGroupdiab, radioGroupsmoke;
    Button buttSave;

    final String KEY_SAVED_RADIO_BUTTON1_INDEX = "SAVED_RADIO_BUTTON1_INDEX";
    final String KEY_SAVED_RADIO_BUTTON2_INDEX = "SAVED_RADIO_BUTTON2_INDEX";
    final String KEY_SAVED_RADIO_BUTTON3_INDEX = "SAVED_RADIO_BUTTON3_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_background);

        buttSave = (Button)findViewById(R.id.buttonSaveHB) ;
        buttSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HealthBackgroundActivity.this,"Selections saved",Toast.LENGTH_SHORT).show();
            }
        });

        radioGroupbp = (RadioGroup)findViewById(R.id.radioGroup_bp);
        radioGroupbp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)radioGroupbp.findViewById(checkedId);
                int checkedIndex = radioGroupbp.indexOfChild(checkedRadioButton);

                SavePreferences(KEY_SAVED_RADIO_BUTTON1_INDEX, checkedIndex);
            }
        });
        radioGroupdiab = (RadioGroup)findViewById(R.id.radioGroup_dia);
        radioGroupdiab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)radioGroupdiab.findViewById(checkedId);
                int checkedIndex = radioGroupdiab.indexOfChild(checkedRadioButton);

                SavePreferences(KEY_SAVED_RADIO_BUTTON2_INDEX, checkedIndex);
            }
        });
        radioGroupsmoke = (RadioGroup)findViewById(R.id.radioGroup_smo);
        radioGroupsmoke.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)radioGroupsmoke.findViewById(checkedId);
                int checkedIndex = radioGroupsmoke.indexOfChild(checkedRadioButton);

                SavePreferences(KEY_SAVED_RADIO_BUTTON3_INDEX, checkedIndex);
            }
        });

        LoadPreferences();

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

    private void SavePreferences(String key, int value){
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.putInt(key, value);
        editor.putInt(key, value);
        editor.commit();
        editor.apply();
    }

    private void LoadPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int savedRadioIndex0 = sharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON1_INDEX, 0);
        int savedRadioIndex1 = sharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON2_INDEX, 0);
        int savedRadioIndex2 = sharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON3_INDEX, 0);

        RadioButton savedCheckedRadioButton0 = (RadioButton)radioGroupbp.getChildAt(savedRadioIndex0);
        savedCheckedRadioButton0.setChecked(true);
        RadioButton savedCheckedRadioButton1 = (RadioButton)radioGroupdiab.getChildAt(savedRadioIndex1);
        savedCheckedRadioButton1.setChecked(true);
        RadioButton savedCheckedRadioButton2 = (RadioButton)radioGroupsmoke.getChildAt(savedRadioIndex2);
        savedCheckedRadioButton2.setChecked(true);
    }
}
