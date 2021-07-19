package com.example.newapplecation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Subuploadfood3 extends AppCompatActivity {

    TextView textViewtitle;

    //끝에 들어가는 인자값으로 여러 액티비티를 쓰는경우 어떤 액티비티인지식별하는값
    private static String TAG = "subupload3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfood3);

        Intent intent = getIntent();
        String string = intent.getStringExtra("title123");
        String string1 = intent.getStringExtra("review123");
        int rating = intent.getIntExtra("rating123", 0);
        String string3 = intent.getStringExtra("userid123");


        textViewtitle = findViewById(R.id.upload_title3);
        textViewtitle.setText(string);

        TextView review123 = (TextView) findViewById(R.id.upload_review3);
        review123.setText(string1);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.upload_rating3);
        ratingBar.setRating(rating);

        TextView score = (TextView) findViewById(R.id.score);
        score.setText(rating+" 점");
        
        TextView writerid = (TextView) findViewById(R.id.upload_title123);
        writerid.setText("작성자" + string3);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "upload onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "upload onResume");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "upload onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "upload onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "upload onDestroy");
    }


}