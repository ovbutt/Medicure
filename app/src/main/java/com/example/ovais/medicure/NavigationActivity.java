package com.example.ovais.medicure;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ovais.medicure.Adapter.ChatMessageAdapter;
import com.example.ovais.medicure.Pojo.ChatMessage;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.Graphmaster;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;
import org.alicebot.ab.Timer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView mListView;
    private Button mButtonSend;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    public Bot bot;
    public static Chat chat;
    private ChatMessageAdapter mAdapter;
    private TextView tv_user_name, tv_user_email;
    private DrawerLayout drawer;
    String message;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView)findViewById(R.id.nav_view);

       tv_user_email = (TextView)navigationView.getHeaderView(0).findViewById(R.id.textView_user_email);
        tv_user_name = (TextView)navigationView.getHeaderView(0).findViewById(R.id.textView_user_name);



        String useremail = (SharedPrefManager.getInstance(this).getUserEmail().trim());
        tv_user_email.setText(useremail);
        tv_user_name.setText("Ovais Butt");


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //bot code starts here

        mListView = (ListView) findViewById(R.id.listView);
        mButtonSend = (Button) findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);
        // mImageView = (ImageView) findViewById(R.id.iv_image);
        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);

        mimicOtherMessage("Hello! This is MEDI - Your personal health assistant please type 'start test' to start symptom assessment.");

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = mEditTextMessage.getText().toString();
                //bot
                String response = chat.multisentenceRespond(mEditTextMessage.getText().toString());
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                sendMessage(message);

                if (message.contains("start test"))
                {
                    mimicOtherMessage("Ok! please let me know what symptoms you have today? (for e.g. 'headache')");
                }

                if (message.contains("headache"))
                {
                    mimicOtherMessage("Ok! I understand you have the following symptom: " + message);
                    mimicOtherMessage("How long have you had it for? Reply with");
                    mimicOtherMessage("Few hours");
                    mimicOtherMessage("Few days");
                    mimicOtherMessage("Few weeks");
                    mimicOtherMessage("Few month");
                    mimicOtherMessage("more than that");
                }

                if (message.contains("few"))
                {
                    mimicOtherMessage("Do you see illusions? Reply with");
                    mimicOtherMessage("Yes");
                    mimicOtherMessage("No");
                    mimicOtherMessage("Maybe");
                }

                if (message.contains("yes"))
                {
                    mimicOtherMessage("You have migraine");
                }

                if (message.contains("no"))
                {
                    mimicOtherMessage("You have cluster headache");
                }

                if (message.contains ("Maybe"))
                {
                    mimicOtherMessage("You have tension headache");
                }

                //mimicOtherMessage(response);
                mEditTextMessage.setText("");
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });


        //checking SD card availablility
        boolean a = isSDCARDAvailable();
        //receiving the assets from the app directory
        AssetManager assets = getResources().getAssets();
        File jayDir = new File(Environment.getExternalStorageDirectory().toString() + "/hari/bots/Hari");
        boolean b = jayDir.mkdirs();
        if (jayDir.exists()) {
            //Reading the file
            try {
                for (String dir : assets.list("Hari")) {
                    File subdir = new File(jayDir.getPath() + "/" + dir);
                    boolean subdir_check = subdir.mkdirs();
                    for (String file : assets.list("Hari/" + dir)) {
                        File f = new File(jayDir.getPath() + "/" + dir + "/" + file);
                        if (f.exists()) {
                            continue;
                        }
                        InputStream in = null;
                        OutputStream out = null;
                        in = assets.open("Hari/" + dir + "/" + file);
                        out = new FileOutputStream(jayDir.getPath() + "/" + dir + "/" + file);
                        //copy file from assets to the mobile's SD card or any secondary memory
                        copyFile(in, out);
                        in.close();
                        in = null;
                        out.flush();
                        out.close();
                        out = null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //get the working directory
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/hari";
        System.out.println("Working Directory = " + MagicStrings.root_path);
        AIMLProcessor.extension = new PCAIMLProcessorExtension();
        //Assign the AIML files to bot for processing
        bot = new Bot("Hari", MagicStrings.root_path, "chat");
        chat = new Chat(bot);
        String[] args = null;
        mainFunction(args);
        //bot code ends here
    }
    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);
        //mimicOtherMessage();
    }

    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);
    }

    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        mAdapter.add(chatMessage);

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        mAdapter.add(chatMessage);
    }

    //check SD card availability
    public static boolean isSDCARDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? true : false;
    }

    //copying the file
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static void mainFunction (String[] args) {
        MagicBooleans.trace_mode = false;
        System.out.println("trace mode = " + MagicBooleans.trace_mode);
        Graphmaster.enableShortCuts = true;
        Timer timer = new Timer();
        String request = "Hello.";
        String response = chat.multisentenceRespond(request);

        System.out.println("Human: "+request);
        System.out.println("Robot: " + response);
    }

    //bot code ends here

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_assessment) {
            drawer.closeDrawers();

        }
        else if (id == R.id.nav_profile) {
            Intent profileIntent = new Intent(this,ProfileActivity.class);
            startActivity(profileIntent);

        }
        else if (id == R.id.nav_treatment) {
            Intent treatmentIntent = new Intent(this,TreatmentActivity.class);
            startActivity(treatmentIntent);

        }
        else if (id == R.id.nav_alerts) {
            Intent alertsIntent = new Intent(this,AlertActivity.class);
            startActivity(alertsIntent);

        }
        else if (id == R.id.nav_records) {
            Intent recordsIntent = new Intent(this,RecordsActivity.class);
            startActivity(recordsIntent);

        }
        else if (id == R.id.nav_doctor) {
            Intent doctorIntent = new Intent(this,DoctorActivity.class);
            startActivity(doctorIntent);

        }
        else if (id == R.id.nav_aboutus) {
            Intent aboutusIntent = new Intent(this,AboutusActivity.class);
            startActivity(aboutusIntent);

        }
        else if (id == R.id.nav_signout) {
            final AlertDialog.Builder alert_builder = new AlertDialog.Builder(this);
            //alert_builder.setTitle("Sign Out");
            alert_builder.setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPrefManager.getInstance(NavigationActivity.this).logout();
                            finish();
                            startActivity(new Intent(NavigationActivity.this, LoginActivity.class));

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alert = alert_builder.create();
            alert.setTitle("Log Out");
            alert.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}