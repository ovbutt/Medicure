package com.example.ovais.medicure;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class AddDietAlert extends AppCompatActivity implements View.OnClickListener {


    private EditText dietTitle , dietNotes;
    private TextView dietTime, dietDate;
    private Button mAddBtn;
    private DietAlertDBHelper dbHelper;

    Calendar currentTime;
    int hour, minute, day, month, year;
    int h, min, d, m, y;
    String format;
    String time, date;

    final static int RQS_1 = (int) System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet_alert);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dietTitle = (EditText)findViewById(R.id.dietTitleEt);
        dietTime = (TextView) findViewById(R.id.dietTimeTv);
        dietDate = (TextView) findViewById(R.id.dietDateTv);
        dietNotes = (EditText)findViewById(R.id.dietNotesEt);

        mAddBtn = (Button)findViewById(R.id.buttonSaveDiet);
        mAddBtn.setOnClickListener(this);

        timePicker();
        datePicker();

        //Listen to add time to TextView
        dietTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(AddDietAlert.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfDay) {
                        timeFormat(hourOfDay);
                        dietTime.setText(hourOfDay + ":" + minuteOfDay + " " + format);
                        time = dietTime.getText().toString();
                        h = hourOfDay;
                        min = minuteOfDay;

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        dietDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDietAlert.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        dietDate.setText(i2 + "-" + i1 + "-" + i);
                        date = dietDate.getText().toString();
                        d = i2;
                        m = i1-1;
                        y = i;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void savePerson() {
        String title = dietTitle.getText().toString().trim();
        String time = dietTime.getText().toString().trim();
        String date = dietDate.getText().toString().trim();
        String notes = dietNotes.getText().toString().trim();
        dbHelper = new DietAlertDBHelper(this);
        DietAlert dietAlert = new DietAlert(title, time, date, notes);
        dbHelper.saveNewPerson(dietAlert);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        Toast.makeText(this, "Diet Alert Added",Toast.LENGTH_LONG).show();
        goBackHome();

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

    private void goBackHome(){
        startActivity(new Intent(AddDietAlert.this, DietAlertActivity.class));
        finish();
    }
    private void timePicker()
    {
        currentTime = Calendar.getInstance();

        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);
        timeFormat(hour);
    }

    private void timeFormat (int hour)
    {
        if (hour >= 0 && hour <=12 )
        {
            format = "AM";
        }
        else
        {
            format = "PM";
        }

    }
    private void datePicker ()

    {
        day = currentTime.get(Calendar.DAY_OF_MONTH);
        month = currentTime.get(Calendar.MONTH);
        year = currentTime.get(Calendar.YEAR);

    }
    private void setAlarm (Calendar targetCal){

        //Toast.makeText(this,"Alaram set at:" + targetCal.getTime() ,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), DietAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis() , pendingIntent);
    }
    private void setAlarmTimeAndDate ()
    {
        Calendar current = Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        cal.set(y,m,d,h,min, 0);

        if (cal.compareTo(current) <= 0)
        {
            Toast.makeText(getApplicationContext(),
                    "Invalid Date or Time",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            savePerson();
            setAlarm(cal);
            //showNotification();
        }
    }

    @Override
    public void onClick(View view) {

        if (dietTime.getText().length()==0) {
            dietTime.requestFocus();
            dietTime.setError(Html.fromHtml("<font color='white'>Please select time</font>"));
        }
        else if (dietDate.getText().length()==0) {
            dietDate.requestFocus();
            dietDate.setError(Html.fromHtml("<font color='white'>Please select date</font>"));
        }

        else if (view == mAddBtn) {
            setAlarmTimeAndDate();
        }
    }
}
