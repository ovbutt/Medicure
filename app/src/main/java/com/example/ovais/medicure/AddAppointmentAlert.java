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

public class AddAppointmentAlert extends AppCompatActivity implements View.OnClickListener {

    private EditText appointmentTitle;
    private EditText appoitnemntdocname;
    private EditText appointmentdocnum;
    private EditText appointmentaddress;
    private TextView appointmenttime;
    private TextView appointmentdate;
    private EditText appointmentnotes;
    private Button mAddBtn;

    Calendar currentTime;
    int hour, minute, day, month, year;
    int h, min, d, m, y;
    String format;
    String time, date;

    private AppointmentAlertDBHelper dbHelper;
    final static int RQS_1 = (int) System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment_alert);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appointmentTitle = (EditText) findViewById(R.id.appointmentTitle);
        appoitnemntdocname = (EditText) findViewById(R.id.appointmentDocName);
        appointmentdocnum = (EditText) findViewById(R.id.appointmentDocPhoneNum);
        appointmentaddress = (EditText) findViewById(R.id.appointmentDocAddress);
        appointmenttime = (TextView) findViewById(R.id.appointmentTime);
        appointmentdate = (TextView) findViewById(R.id.appointmentDate);
        appointmentnotes = (EditText) findViewById(R.id.appointmentNotes);

        mAddBtn = (Button) findViewById(R.id.addNewAppointmentButton);

        timePicker();
        datePicker();

        //Listen to add time to TextView
        appointmenttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(AddAppointmentAlert.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfDay) {
                        timeFormat(hourOfDay);
                        appointmenttime.setText(hourOfDay + ":" + minuteOfDay + " " + format);
                        time = appointmenttime.getText().toString();
                        h = hourOfDay;
                        min = minuteOfDay;

                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        appointmentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAppointmentAlert.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        appointmentdate.setText(i2 + "-" + i1 + "-" + i);
                        date = appointmentdate.getText().toString();
                        d = i2;
                        m = i1-1;
                        y = i;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        //listen to add button click
        mAddBtn.setOnClickListener(this);
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
        String title = appointmentTitle.getText().toString().trim();
        String name = appoitnemntdocname.getText().toString().trim();
        String phnum = appointmentdocnum.getText().toString().trim();
        String address = appointmentaddress.getText().toString().trim();
        String time = appointmenttime.getText().toString().trim();
        String date = appointmentdate.getText().toString().trim();
        String notes = appointmentnotes.getText().toString().trim();
        dbHelper = new AppointmentAlertDBHelper(this);

        //create new appointmentAlert
        AppointmentAlert appointmentAlert = new AppointmentAlert(title, name, phnum, address, time, date, notes);
        dbHelper.saveNewPerson(appointmentAlert);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
       Toast.makeText(this, "Appointment Alert Added",Toast.LENGTH_LONG).show();
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(AddAppointmentAlert.this, AppointmentAlertActivity.class));
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
//               .addAction(R.drawable.arroba, "DISMISS ALARM",pendingIntent)
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

       // Toast.makeText(this,"Alaram set at:" + targetCal.getTime() ,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), AppointmentAlarmReceiver.class);
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
        }
    }

    @Override
    public void onClick(View view) {

        if (appointmenttime.getText().length()==0) {
            appointmenttime.requestFocus();
            appointmenttime.setError(Html.fromHtml("<font color='white'>Please select time</font>"));
        }
        else if (appointmentdate.getText().length()==0) {
            appointmentdate.requestFocus();
            appointmentdate.setError(Html.fromHtml("<font color='white'>Please select date</font>"));
        }

        else if (view == mAddBtn) {
            setAlarmTimeAndDate();
        }
    }
}
