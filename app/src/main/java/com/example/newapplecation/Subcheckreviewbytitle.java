package com.example.newapplecation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class Subcheckreviewbytitle extends AppCompatActivity {

    private static String TAG = "subcheckreviewbytitle";

    SharedPreferences preferences;
    TextView textView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    JSONArray array = new JSONArray();

    int ratingall = 0;
    int ratingnumber = 0;

    ArrayList<myreview> mArrayList = new ArrayList<>();
    public static Subcheckreviewbytitle subcheckreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkreviewbytitle);


        subcheckreview = Subcheckreviewbytitle.this;
        Subcheckreviewbytitle subcheckreview1 = (Subcheckreviewbytitle) Subcheckreviewbytitle.subcheckreview;

        preferences = getSharedPreferences("account", MODE_PRIVATE);
        pref = getSharedPreferences("allreview", MODE_PRIVATE);


        //이게 키값(리뷰작성시)으로도 쓰일 예정
        String userid;
        userid = preferences.getString("id", "");


        textView = findViewById(R.id.checkrivewbititle_title);
        Intent intent = getIntent();
        String storetitle = intent.getStringExtra("storename");


        adapter2 mAdapter;


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.checkrivewbititle_recyclrview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mAdapter = new adapter2(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        ArrayList<String> arrayListid = new ArrayList<>();

        JSONArray jsonArray = new JSONArray();
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
            if (pref.getString(userid, "") != null) {
                System.out.println("버튼누르기전에 pref 비어있지 않다");
                try {
                    array = new JSONArray(exam1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("반복문안에서 array 저장되는값 : " + array);
            } else if (pref.getString(userid, "") == null) {
                continue;
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
                        if ((reviewtitle1.equals(storetitle))) {
                            ratingall += reviewrating1;
                            ratingnumber++;
                            System.out.println("반복문안에서  저장되는 별점" + ratingall + "  " + ratingnumber);
                            myreview dictionary = new myreview(reviewtitle1, reviewreview1, reviewrating1, useid1);
                            mArrayList.add(dictionary);
                            //어댑터에 반영
                            mAdapter.notifyDataSetChanged();
                        } else {
                            continue;
                        }

                        System.out.println("처음의 데이터를  받아와서 " + "마이리뷰" + reviewtitle1 + "제목"
                                + reviewreview1 + "리뷰" + reviewrating1 + "별점" + useid1 + "작성한 유저" + useid1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("반복문 밖 에서의 저장되는 별점" + ratingall + "  " + ratingnumber);
//        String exam1 = pref.getString(userid, "");
//        System.out.println("버튼누르기전에 pref 저장되는값 : " + exam1);
//        if (pref.getString(userid, "") != null) {
//            System.out.println("버튼누르기전에 pref 비어있지 않다");
//            try {
//                array = new JSONArray(exam1);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            System.out.println("반복문안에서 array 저장되는값 : " + array);
//        }
//        System.out.println("반복문밖에서 array 저장되는값 : " + array);

//        Collection<?> exam1 = pref.getAll().values();
//        System.out.println("버튼누르기전에 pref 저장되는값 : " + exam1);
//        array = new JSONArray(exam1);
//        System.out.println("버튼누르기전에 array 저장되는값 : " + array);

//        if (!array.isNull(0)) {
//            System.out.println("최초의생성");
//            for (int a = 0; a < array.length(); a++) {
//                try {
//                    JSONObject jsonObject12 = (JSONObject) array.get(a);
//                    String reviewtitle1 = (String) jsonObject12.get("title");
//                    String reviewreview1 = (String) jsonObject12.get("review");
//                    int reviewrating1 = (int) jsonObject12.get("ratingBar");
//                    String useid1 = (String) jsonObject12.get("userid");
//
//
////                    if ((reviewtitle1.equals(가게이름))) {
//
//                    myreview dictionary = new myreview(reviewtitle1, reviewreview1, reviewrating1, useid1);
//                    mArrayList.add(dictionary);
//                    //어댑터에 반영
//                    mAdapter.notifyDataSetChanged();
//
////                    }
//
//                    System.out.println("처음의 데이터를  받아와서 " + "마이리뷰" + reviewtitle1 + "제목" + reviewreview1 + "리뷰" + reviewrating1 + "별점" + useid1 + "작성한 유저" + useid1);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        Button buttonInsert = (Button) findViewById(R.id.check_add);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Subuploadfood.class);
                intent1.putExtra("recommend", storetitle);
                startActivity(intent1);
            }
        });

//        buttonInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //버튼 입력시 다이얼로그로 화면
//                AlertDialog.Builder builder = new AlertDialog.Builder(Subcheckreviewbytitle.this);
//                View view = LayoutInflater.from(Subcheckreviewbytitle.this).inflate(R.layout.activity_uploadfood2, null, false);
//                builder.setView(view);
//
//
//                final Button Buttonupload = (Button) view.findViewById(R.id.upload_uploadbutton);
//                final Button Buttonnoupload = (Button) view.findViewById(R.id.upload_noloadbutton);
//                final EditText editTexttitle = (EditText) view.findViewById(R.id.upload_title);
//                final EditText editTextreview = (EditText) view.findViewById(R.id.upload_review);
//                final RatingBar editratingBar = (RatingBar) view.findViewById(R.id.upload_rating);
//                final TextView edittext = (TextView) view.findViewById(R.id.scorew);
//
//                final AlertDialog dialog = builder.create();
//
//                editTexttitle.setText(storetitle);
//
//                editratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                    @Override
//                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                        edittext.setText(rating + "점");
//                    }
//                });
//
//                Buttonnoupload.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//                //다이얼메뉴에서 작성하기 버튼 눌르면
//                Buttonupload.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        //입력값을 가지고 와서
//                        String reviewtitle = editTexttitle.getText().toString();
//                        String reviewreview = editTextreview.getText().toString();
//                        float reviewrating = editratingBar.getRating();
//                        //arraylist(myreview)에 넣고
//                        myreview dictionary = new myreview(reviewtitle, reviewreview, reviewrating, userid);
//                        //첫줄에
////                        mArrayList.add(0, dictionary);
//
//                        JSONObject uploadjsonObject = new JSONObject();
//                        try {
//                            uploadjsonObject.put("title", reviewtitle);
//                            uploadjsonObject.put("review", reviewreview);
//                            uploadjsonObject.put("ratingBar", reviewrating);
//                            uploadjsonObject.put("userid", userid);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        array.put(uploadjsonObject);
//                        editor.putString(userid, String.valueOf(array));
//                        editor.commit();
//
//                        mArrayList.add(0, dictionary);
//                        //어댑터에 반영
//                        mAdapter.notifyDataSetChanged();
//
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });

        int titlrrating = 0;

        if (ratingnumber == 0) {
            textView.setText(storetitle);
        } else {
            titlrrating = ratingall / ratingnumber;
            textView.setText(storetitle + "  평균별점 : " + titlrrating);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "subcheckreviewbytitle onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "subcheckreviewbytitle onResume");

    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "subcheckreviewbytitle onStop");


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "subcheckreviewbytitle onPause");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "subcheckreviewbytitle onDestroy");
    }
}