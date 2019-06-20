package com.example.ovais.medicure;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreen extends Activity {

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        textView = (TextView)findViewById(R.id.textView);
        imageView = (ImageView)findViewById(R.id.image);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.myanimation);
        imageView.startAnimation(myanim);
        final Intent i = new Intent(this,LoginActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();

    }
}
