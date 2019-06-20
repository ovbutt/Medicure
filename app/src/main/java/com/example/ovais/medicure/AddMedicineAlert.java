package com.example.ovais.medicure;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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

public class AddMedicineAlert extends AppCompatActivity implements View.OnClickListener{

    private EditText medicineTitle;
    private TextView medicinetime;
    private TextView medicinedate;
    private EditText medicinequantity;
    private EditText medicinenotes;

    private Button mAddBtn;
    private MedicineAlertDBHelper dbHelper;

    Calendar currentTime;
    int hour, minute, day, month, year;
    int h, min, d, m, y;
    String format;
    String time, date;

    final static int RQS_1 = (int) System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine_alert);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        medicineTitle = (EditText)findViewById(R.id.medicineNameET);
        medicinetime = (TextView) findViewById(R.id.medicineDateTV);
        medicinedate = (TextView) findViewById(R.id.medicineTimeTV);
        medicinequantity = (EditText)findViewById(R.id.medicineQuantityET);
        medicinenotes = (EditText)findViewById(R.id.medicineNotesET);

        mAddBtn = (Button)findViewById(R.id.addMedicineAlert);

        //listen to add button click
        mAddBtn.setOnClickListener(this);

        timePicker();
        datePicker();

        //Listen to add time to TextView
        medicinetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(AddMedicineAlert.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfDay) {
                        timeFormat(hourOfDay);
                        medicinetime.setText(hourOfDay + ":" + minuteOfDay + " " + format);
                        time = medicinetime.getText().toString();
                        h = hourOfDay;
                        min = minuteOfDay;

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        medicinedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMedicineAlert.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        medicinedate.setText(i2 + "-" + i1 + "-" + i);
                        date = medicinedate.getText().toString();
                        d = i2;
                        m = i1-1;
                        y = i;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

    }

    private void savePerson(){
        String title = medicineTitle.getText().toString().trim();
        String time = medicinetime.getText().toString().trim();
        String date = medicinedate.getText().toString().trim();
        String quantity = medicinequantity.getText().toString().trim();
        String notes = medicinenotes.getText().toString().trim();
        dbHelper = new MedicineAlertDBHelper(this);
        MedicineAlert medicineAlert = new MedicineAlert(title, time, date, quantity, notes);
        dbHelper.saveNewPerson(medicineAlert);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        Toast.makeText(this, "Medicine Alert Added",Toast.LENGTH_LONG).show();
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(AddMedicineAlert.this, MedicineAlertActivity.class));
        finish();
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
//    public void showNotification()
//    {
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        Intent notificationIntent = new Intent(this, AppointmentAlert.class);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(this);
//        stackBuilder.addNextIntent(notificationIntent);
//
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//
//        Notification notification = builder.setContentTitle("Appointment Notification")
//                .addAction(R.drawable.arroba, "DISMISS ALARM",pendingIntent)
//                .setContentText("You have an appointment with the doctor")
//                .setAutoCancel(true)
//                .setSound(alarmSound)
//                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setContentIntent(pendingIntent)
//                .build();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(RQS_1, notification);
//    }

    private void setAlarm (Calendar targetCal){

//        ComponentName receiver = new ComponentName(getBaseContext(), AddAppointmentAlert.class);
//        PackageManager pm = getBaseContext().getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);

        //Toast.makeText(this,"Alaram set at:" + targetCal.getTime() ,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), MedicineAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis() , pendingIntent);
    }

//    public void cancelAlarm()
//    {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent myIntent = new Intent(getApplicationContext(), AppointmentAlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                getApplicationContext(), 1, myIntent, 0);
//
//        alarmManager.cancel(pendingIntent);
//    }



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

        if (medicinetime.getText().length()==0) {
            medicinetime.requestFocus();
            medicinetime.setError(Html.fromHtml("<font color='white'>Please select time</font>"));
        }
        else if (medicinedate.getText().length()==0) {
            medicinedate.requestFocus();
            medicinedate.setError(Html.fromHtml("<font color='white'>Please select date</font>"));
        }

        else if (view == mAddBtn) {
            setAlarmTimeAndDate();
        }
    }
}
