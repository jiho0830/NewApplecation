package com.example.newapplecation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private GoogleMap mmap;
    private Geocoder geocoder;
    TextView TextView_loginbutton, Textview_string;
    Button Button_mainmenu;
    private static String TAG = "mainlogin";
    SharedPreferences preferences;
    SharedPreferences pref;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static MainActivity MainActivity1;
    Handler handler;
    String[] strings = new String[]{"맛있으면 0칼로리", "고기는 살 안찐다", "오늘먹을 치킨 내일로 미루지 말자", "부먹 찍먹 말고 한입 더",
            "2배로 맛있게 먹는 법은 2배로 먹는 것", "칼로리와 맛은 비례한다", "'B'와 'D' 사이에는 'CHICKEN' 이 있다", ""};

    JSONArray array = new JSONArray();

    int stringsnumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlogin);

        Log.d(TAG, "mainlogin onCreate");

        MainActivity1 = MainActivity.this;
        MainActivity mainActivity = (MainActivity) MainActivity.MainActivity1;

        TextView_loginbutton = findViewById(R.id.TextView_loginbutton);

        TextView_loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Submodifyaccount.class);
                startActivity(intent);
            }
        });


        preferences = getSharedPreferences("account", MODE_PRIVATE);
        pref = getSharedPreferences("allreview", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("mapmarker", MODE_PRIVATE);


        Button_mainmenu = findViewById(R.id.Button_mainmenu);
        Button_mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.mainmenu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_upload) {
                            Intent intent = new Intent(getApplicationContext(), Subrecommendfood.class);
                            startActivity(intent);
                        } else if (item.getItemId() == R.id.menu_check) {
                            Intent intent = new Intent(getApplicationContext(), Subcheckreview.class);
                            startActivity(intent);
                        } else if (item.getItemId() == R.id.menu_plus) {
                            Intent intent = new Intent(getApplicationContext(), Subplusfood.class);
                            startActivity(intent);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        ArrayList<String> arrayListid = new ArrayList<>();

        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        ArrayList<JSONObject> arrayList = new ArrayList<>();

        String exam2 = preferences.getString("account2", null);
        try {
            jsonArray = new JSONArray(exam2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int a = 0; a < jsonArray.length(); a++) {
            String allid = null;
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(a);
                allid = jsonObject.getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            arrayListid.add(allid);
            System.out.println("반복문안에서 arrayListid 저장되는값 : " + arrayListid);
        }
        System.out.println("반복문밖에서 arrayListid 저장되는값 : " + arrayListid);


        for (int i = 0; i < arrayListid.size(); i++) {
            String exam1 = pref.getString(arrayListid.get(i), "");
            try {
                array = new JSONArray(exam1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("반복문밖에서 array 저장되는값 : " + array);


            if (!array.isNull(0)) {
                System.out.println("최초의생성");
                for (int a = 0; a < array.length(); a++) {
                    try {
                        JSONObject jsonObject12 = (JSONObject) array.get(a);
                        String reviewtitle1 = (String) jsonObject12.get("title");
                        String reviewreview1 = (String) jsonObject12.get("review");
                        int reviewrating1 = (int) jsonObject12.get("ratingBar");
                        String useid1 = (String) jsonObject12.get("userid");

                        arrayList.add(jsonObject12);


                        System.out.println("처음의 데이터를  받아와서 " + "마이리뷰" + reviewtitle1 + "제목" + reviewreview1 + "리뷰" + reviewrating1 + "별점" + useid1 + "작성한 유저" + useid1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("핸들러전에 출력하는 리스트" + arrayList);

        Random random = new Random();


        if (arrayList.size() != 0) {
            handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    TextView Textview_string = findViewById(R.id.mainlogintext);

                    String reviewtitle1, reviewreview1, useid1;
                    int reviewrating1;


                    try {
                        JSONObject jsonObject = arrayList.get(stringsnumber);
                        reviewtitle1 = (String) jsonObject.get("title");
                        reviewreview1 = (String) jsonObject.get("review");
                        reviewrating1 = (int) jsonObject.get("ratingBar");
                        useid1 = (String) jsonObject.get("userid");

                        Textview_string.setText("가게이름 : " + reviewtitle1 + "     별점 : " + reviewrating1
                                + " 점" + "\n" + "리뷰 : " + reviewreview1);
                        Textview_string.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), Subuploadfood3.class);
                                intent.putExtra("title123", reviewtitle1);
                                intent.putExtra("review123", reviewreview1);
                                intent.putExtra("rating123", reviewrating1);
                                intent.putExtra("userid123", useid1);
                                startActivity(intent);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            class newrunnable implements Runnable {
                @Override
                public void run() {
                    while (true) {
                        try {

                            int checknumber = stringsnumber;
                            while (stringsnumber != checknumber) {
                                checknumber = random.nextInt(arrayList.size());
                                stringsnumber = checknumber;
                            }

                            stringsnumber = random.nextInt(arrayList.size());
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println(e);
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0);
                    }
                }
            }

//        handler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                TextView Textview_string = findViewById(R.id.mainlogintext);
//
//
//                Textview_string.setText(strings[stringsnumber]);
//
//
//                Textview_string.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getApplicationContext(), Subuploadfood3.class);
//                        String string = strings[stringsnumber];
//                        intent.putExtra("title", string);
//                        startActivity(intent);
//                    }
//                });
//            }
//        };

//        class newrunnable implements Runnable {
//            @Override
//            public void run() {
//                while (true) {
//                    stringsnumber++;
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    handler.sendEmptyMessage(0);
//                    if (stringsnumber == 7) {
//                        stringsnumber = 0;
//                    }
//                }
//            }
//        }
            newrunnable newrunnable = new newrunnable();
            Thread thread = new Thread(newrunnable);
            thread.setDaemon(true);
            thread.start();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //GOOGLEMap 객체가 준비될 때 실행될 콜백을 등록합니다.
        mapFragment.getMapAsync(this);

    }

    //맵의 사용준비가 되어있을때 호출되는 메소드
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mmap = googleMap;
        geocoder = new Geocoder(this);

        //좌표를 담는 객체, 위도와 경도를 파라미터로 넣는다.
        LatLng mark1 = new LatLng(37.52666, 127.02910);
//        LatLng mark2 = new LatLng(37.52611, 127.02788);
//        LatLng mark3 = new LatLng(37.52637, 127.02940);
//        LatLng mark4 = new LatLng(37.52598, 127.02764);


        //여기에 반복문으로 데이터를 가져와서
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
            try {
                if (jsonArray.get(0).equals("")) {
                    for (int i = 1; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject12 = (JSONObject) jsonArray.get(i);
                            String latitude = (String) jsonObject12.get("latitude");
                            String longitude = (String) jsonObject12.get("longitude");
                            String title = (String) jsonObject12.get("title");

                            double latitude1 = Double.parseDouble(latitude);
                            double longitude1 = Double.parseDouble(longitude);

                            LatLng mark = new LatLng(latitude1, longitude1);
                            mmap.addMarker(new MarkerOptions().position(mark).title(title));
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
                            double latitude = (double) jsonObject12.get("latitude");
                            double longitude = (double) jsonObject12.get("longitude");
                            String title = (String) jsonObject12.get("title");


                            LatLng mark = new LatLng(latitude, longitude);
                            mmap.addMarker(new MarkerOptions().position(mark).title(title));
                            System.out.println("조건문 안에서의 생성 0 " + latitude + " " + longitude + "   " + title);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        //이걸로 마커를 추가한다. 그럼 내가 저장해야 하는 값은 LatLng,위도 경도 ,title 이렇게 두개를 저장하면 되는건가?
        //마커를 입력하는 키 값은 위도와 경도를 키값으로 하자
        mmap.addMarker(new MarkerOptions().position(mark1).title("아지트1번").snippet("집결지 1번"));
//        mmap.addMarker(new MarkerOptions().position(mark2).title("name23").snippet("집결지 2번"));
//        mmap.addMarker(new MarkerOptions().position(mark3).title("name2").snippet("집결지 3번"));
//        mmap.addMarker(new MarkerOptions().position(mark4).title("name12").snippet("집결지 4번"));

        //카메라를 내가 지정한 위치로 지정해서 움직입니다.CameraUpdateFactory 메소드를 이용하여 지정한 단계의 카메라 줌을 이용함.
        mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(mark1, 17));


        //지도 클릭시에 넘겨줄 데이터
        mmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                //맵 클릭시 현재 위도와 경도에서 화면 포인트를 알려줌
                Point screenpt = mmap.getProjection().toScreenLocation(point);

                //현재 화면에 찍힌 포인트로부터의 위도와 경도를 알려줌
                LatLng latLng = mmap.getProjection().fromScreenLocation(screenpt);

                Log.d("맵좌표", "좌표 : 위도는 : " + point.latitude +
                        "경도는 : " + point.longitude);

                Log.d("화면 좌표는 ", "  화면좌표 :   X : " + screenpt.x +
                        "Y : " + screenpt.y);

                Intent intent = new Intent(getApplicationContext(), Subplusfood2.class);
                intent.putExtra("latitude", point.latitude);
                intent.putExtra("longitude", point.longitude);
                startActivity(intent);
            }
        });

        mmap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(this, Subcheckreviewbytitle.class);
        intent.putExtra("storename", marker.getTitle());
        startActivity(intent);
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        TextView_loginbutton.setText(preferences.getString("id", "") + " 님");
        Log.d(TAG, "mainlogin onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "mainlogin onResume");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "mainlogin onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "mainlogin onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "mainlogin onDestroy");
    }


}