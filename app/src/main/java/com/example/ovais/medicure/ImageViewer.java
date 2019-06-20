package com.example.ovais.medicure;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.sql.Blob;

public class ImageViewer extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);


        Integer Image = getIntent().getIntExtra("image",1);
        imageView = (ImageView)findViewById(R.id.imageView4);

        imageView.setImageResource(Image);

    }
}
