package com.example.newapplecation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//emulator로 작동시의 코드, 맵에서 터치할때의 코드
public class Subplusfood2 extends AppCompatActivity {
    ImageView plusfood_image;
    static final int REQUSET_CODE = 0;
    MainActivity mainActivity = (MainActivity) MainActivity.MainActivity1;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plusfood);


//        plusfood_image = findViewById(R.id.plusfood_image);
//        plusfood_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, REQUSET_CODE);
//            }
//        });

        preferences = getSharedPreferences("mapmarker", MODE_PRIVATE);
        editor = preferences.edit();

        Intent intent = getIntent();
        Double latitude = intent.getDoubleExtra("latitude", 0.0);
        Double longitude = intent.getDoubleExtra("longitude", 0.0);

        String latitude2 = String.format("%.6f", latitude);
        String longitude2 = String.format("%.6f", longitude);


        EditText editText = findViewById(R.id.upload_title);
        EditText editphone = findViewById(R.id.upload_phone);


        String exam2 = preferences.getString("marker", null);
        System.out.println("처음생성시에 exam2 : " + exam2);
        if (exam2 != null) {
            System.out.println("버튼누르기전에 pref 비어있지 않다");
            try {
                jsonArray = new JSONArray(exam2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("조건문 안에서의  jsonArray : " + jsonArray);
        }


        Button plusfood_addputton = findViewById(R.id.plusfood_addputton);
        plusfood_addputton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText.getText().toString();
                String phone = editphone.getText().toString();
                System.out.println("작성버튼 클릭시에 데이터의 확인 : " + "  latitude : "
                        + latitude2 + "   longitude : " + longitude2 + "  title : " + title + "  phone : " + phone);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("latitude", latitude2);
                    jsonObject.put("longitude", longitude2);
                    jsonObject.put("title", title);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(jsonObject);
                System.out.println("데이터를 넣기전에 저장된거 jsonArray 확인하는 것 : " + jsonArray);

                editor.putString("marker", String.valueOf(jsonArray));
                editor.commit();

                mainActivity.recreate();
                finish();

            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUSET_CODE) {
//            if (resultCode == RESULT_OK) {
//                try {
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();
//                    plusfood_image.setImageBitmap(img);
//                } catch (Exception e) {
//
//                }
//            } else if (requestCode == REQUSET_CODE) {
//                Toast.makeText(this, "사진선택취소", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}