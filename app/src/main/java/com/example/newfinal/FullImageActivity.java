package com.example.newfinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        Intent i = getIntent();

        int position = i.getExtras().getInt("id");
       // ImageAdapter adapter = new ImageAdapter(this);

      //  ImageView imageView = (ImageView) findViewById(R.id.imageView);
        //String dataValue=adapter.images.get(position);
        //byte[] bytes= Base64.decode(dataValue,Base64.DEFAULT);
        // byte[] bytes = dataValue.getBytes();
        //Bitmap bmp= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        //imageView.setImageBitmap(bmp);

    }
}
