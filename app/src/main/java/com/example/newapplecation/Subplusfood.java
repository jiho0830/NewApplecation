package com.example.newapplecation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//핸드폰에서 한글로 식당을 검색할때의 코드
//이제 한글이 되는것을 확인했으니 거기에 따른 확인
public class Subplusfood extends AppCompatActivity {
    ImageView plusfood_image;
    static final int REQUSET_CODE = 0;
    MainActivity mainActivity = (MainActivity) MainActivity.MainActivity1;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    JSONArray jsonArray;
    Boolean checking = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plusfood);

        preferences = getSharedPreferences("mapmarker", MODE_PRIVATE);
        editor = preferences.edit();


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
        } else {
            jsonArray = new JSONArray();
        }

        EditText editText = findViewById(R.id.upload_title);
        EditText editphone = findViewById(R.id.upload_phone);


        final Geocoder geocoder = new Geocoder(this);
        Button plusfood_addputton = findViewById(R.id.plusfood_addputton);
        plusfood_addputton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Address> list = null;
                String str = editText.getText().toString();
                try {
                    list = geocoder.getFromLocationName(str, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test by first geocoder", "오류발생");
                }

                if (list != null) {
                    if (list.size() == 0) {
                        Toast.makeText(Subplusfood.this, "잘못된 주소입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        //위도 획득
                        Double latitube = list.get(0).getLatitude();
                        //경도획득
                        Double longitude = list.get(0).getLongitude();

                        System.out.println("획득한 위도 : " + latitube + " 획득한 경도 : " + longitude);


                        //위도 경도 비교
                        if (!jsonArray.isNull(0)) {
                            System.out.println("비교할 마커들 생성 시작");
                            try {
                                if (jsonArray.get(0).equals("")) {
                                    for (int i = 1; i < jsonArray.length(); i++) {
                                        try {
                                            JSONObject jsonObject12 = (JSONObject) jsonArray.get(i);
                                            String latitude12 = (String) jsonObject12.get("latitude");
                                            String longitude12 = (String) jsonObject12.get("longitude");
                                            String title = (String) jsonObject12.get("title");

                                            double latitude1 = Double.parseDouble(latitude12);
                                            double longitude1 = Double.parseDouble(longitude12);


                                            System.out.println("조건문 안에서의 생성 1  " + latitude1 + " " + longitude1 + "   " + title);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                //핸드폰
                                else {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        try {
                                            JSONObject jsonObject12 = (JSONObject) jsonArray.get(i);
                                            double latitude12 = (double) jsonObject12.get("latitude");
                                            double longitude12 = (double) jsonObject12.get("longitude");
                                            String title = (String) jsonObject12.get("title");

                                            if ((latitude12 == latitube) && (longitude12 == longitude)) {
                                                Toast.makeText(Subplusfood.this, "해당 위치에 마커가 있습니다.", Toast.LENGTH_LONG).show();
                                                checking = false;
                                            }

                                            System.out.println("조건문 안에서의 생성 0 " + latitude12 + " " + longitude12 + "   " + title);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (checking == true) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("latitude", latitube);
                                jsonObject.put("longitude", longitude);
                                jsonObject.put("title", str);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            jsonArray.put(jsonObject);
                            System.out.println("데이터를 넣기전에 저장된거 jsonArray 확인하는 것 : " + jsonArray);

                            editor.putString("marker", String.valueOf(jsonArray));
                            editor.commit();
                        }

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        String title = editText.getText().toString();
                        intent.putExtra("koewatitle", title);
                        startActivity(intent);
                        mainActivity.recreate();
                        finish();
                    }
                }


//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                String title = editText.getText().toString();
//                intent.putExtra("koewatitle", title);
//                startActivity(intent);
//                mainActivity.recreate();
//                finish();
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