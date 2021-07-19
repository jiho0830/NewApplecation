package com.example.newapplecation;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.widget.ArrayAdapter;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Subuploadfood extends AppCompatActivity {
    RatingBar ratingbar;
    TextView score;
    ImageView upload_image;

    MainActivity mainActivity = new MainActivity();

    static final int REQUSET_CODE = 0;
    //끝에 들어가는 인자값으로 여러 액티비티를 쓰는경우 어떤 액티비티인지식별하는값
    private static String TAG = "subupload";
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfood);


        score = findViewById(R.id.score);
        score.setText("0점");

        ratingbar = findViewById(R.id.upload_rating);
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score.setText(rating + "점");
            }
        });

        Intent intent = getIntent();
        String storetitle = intent.getStringExtra("bytitle");
        EditText uploadtitle = (EditText) findViewById(R.id.upload_title);

//        if (storetitle == "") {
//
//        } else {
//            uploadtitle.setText(storetitle);
//        }

        String strti = intent.getStringExtra("recommend");
        if (strti == "") {

        } else {
            uploadtitle.setText(strti);
        }

        upload_image = findViewById(R.id.upload_image);
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUSET_CODE);//새 액티비티를 열고 결과값을 전달받음(쌍방향통신)
            }
            //이코드는 구글갤러리에 대한 접근
            //만일 일반 갤러리에 접근하고 싶다면
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//            startActivityForResult(intent,RequestCode.PICK_IMAGE);
        });

        Button upload_uploadbutton = findViewById(R.id.upload_uploadbutton);
        upload_uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Subuploadfood.this, Subcheckreview.class);

                EditText uploadtitle = (EditText) findViewById(R.id.upload_title);
                intent.putExtra("contact_name1", uploadtitle.getText().toString());

                EditText uploadreview = (EditText) findViewById(R.id.upload_review);
                intent.putExtra("contact_name2", uploadreview.getText().toString());

                intent.putExtra("contact_name3", ratingbar.getRating());


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable) upload_image.getDrawable()).getBitmap();
                //사진크키조정
                float scale = (float) (1024 / (float) bitmap.getWidth());
                int imagew = (int) (bitmap.getWidth() * scale);
                int imageh = (int) (bitmap.getHeight() * scale);
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, imagew, imageh, true);
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();

                intent.putExtra("image", bytes);

                Log.d(TAG, "사진 보내기전에 로그");

                startActivity(intent);
                finish();
            }
        });

        Button upload_noloadbutton = findViewById(R.id.upload_noloadbutton);
        upload_noloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        list = new ArrayList<String>();

        settingList();
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.upload_titleauto);

        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list));

    }

    private void settingList(){
        SharedPreferences sharedPreferences = getSharedPreferences("mapmarker", MODE_PRIVATE);
        JSONArray jsonArray = new JSONArray();
        String exam2 = sharedPreferences.getString("marker", null);
        System.out.println("처음생성시에 exam2 : " + exam2);
        //preferences.getString("mapmarker", "") != null
        if (exam2 != null) {
            System.out.println("버튼누르기전에 pref 비어있지 않다");
            try {
                jsonArray = new JSONArray(exam2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (!jsonArray.isNull(0)) {
            System.out.println("마커들 생성 시작");
            for (int i = 1; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject12 = (JSONObject) jsonArray.get(i);
                    String title = (String) jsonObject12.get("title");
                    System.out.println("조건문 안에서의 생성 마커의 타이틀  : " + title);
                    list.add(title);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }




//        list.add("name23");
//        list.add("name12");
//        list.add("name45");
//        list.add("name67");
    }

    @Override//갤러리에서 불러온후에 행동
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode는 activity식별값이고   resultCode는 setResult()에서 보낸값이다 data는 putExtra()를 이용하려 intent값도 보낼수 있다
        if (requestCode == REQUSET_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    //선택한 이미지에서 비트맵생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    //원하는 이미지뷰에 넣기
                    upload_image.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (requestCode == REQUSET_CODE) {
                Toast.makeText(this, "사진선택취소", Toast.LENGTH_LONG).show();
            }
        }
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