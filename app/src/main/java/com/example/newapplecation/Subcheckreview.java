package com.example.newapplecation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Subcheckreview extends AppCompatActivity {

    private static String TAG = "subcheckreview";

    SharedPreferences preferences;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    JSONArray array = new JSONArray();

    ArrayList<myreview> mArrayList = new ArrayList<>();
    public static Subcheckreview subcheckreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkreview);

        preferences = getSharedPreferences("account", MODE_PRIVATE);
        pref = getSharedPreferences("allreview", MODE_PRIVATE);
        editor = pref.edit();

        subcheckreview = Subcheckreview.this;
        Subcheckreview subcheckreview = (Subcheckreview) Subcheckreview.subcheckreview;

        //이게 키값(리뷰작성시)으로도 쓰일 예정
        String userid;
        userid = preferences.getString("id", "");


        Bitmap image;
        byte[] bytearray;
        adapter mAdapter;


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.checkrivew_recyclrview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mAdapter = new adapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Intent intent1 = getIntent();
        ArrayList<myreview> mArrayList1 = new ArrayList<>();
        System.out.println("아직 수정값받기전");
        System.out.println(intent1.getSerializableExtra("modify12"));
        if (intent1.getSerializableExtra("modify12") != null) {

            JSONArray jsonArray12 = new JSONArray();
            mArrayList1 = (ArrayList<myreview>) intent1.getSerializableExtra("modify12");
            System.out.println("수정값받을시에" + mArrayList1);
            for (int c = 0; c < mArrayList1.size(); c++) {
                myreview backreview = mArrayList1.get(c);
                String reviewtitle = backreview.getTitle();
                String reviewreview = backreview.getReview();
                float reviewrating = backreview.getRatingBar();
                String userid1231 = backreview.getUserid();
                try {
                    JSONObject jsonObject12 = new JSONObject();
                    jsonObject12.put("title", reviewtitle);
                    jsonObject12.put("review", reviewreview);
                    jsonObject12.put("ratingBar", reviewrating);
                    jsonObject12.put("userid", userid1231);
                    System.out.println("try안에서" + jsonObject12);
                    System.out.println("반복분 동안 jsonObject12  " + jsonObject12);
                    jsonArray12.put(jsonObject12);
                    System.out.println("반복분 동안  jsonArray12  " + jsonArray12);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("데이터를 받아와서" + "제목" + reviewtitle + "리뷰" + reviewreview + "별점" + reviewrating + "작성한 유저" + userid1231);
            }

            System.out.println("마지막넣기전" + jsonArray12);
            editor.remove(userid);
            editor.commit();
            editor.putString(userid, jsonArray12.toString());
            editor.commit();


            mAdapter.notifyDataSetChanged();
//            recreate();
        }

        String exam1 = pref.getString(userid, "");
        System.out.println("버튼누르기전에 pref 저장되는값 : " + exam1);
        if (pref.getString(userid, "") != null) {
            System.out.println("버튼누르기전에 pref 비어있지 않다");
            try {
                array = new JSONArray(exam1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (!array.isNull(0)) {
            System.out.println("최초의생성");
            for (int a = 0; a < array.length(); a++) {
                try {
                    JSONObject jsonObject12 = (JSONObject) array.get(a);
                    String reviewtitle1 = (String) jsonObject12.get("title");
                    String reviewreview1 = (String) jsonObject12.get("review");
                    int reviewrating1 = (int) jsonObject12.get("ratingBar");
                    String useid1 = (String) jsonObject12.get("userid");
                    if ((useid1.equals(userid))) {
                        myreview dictionary = new myreview(reviewtitle1, reviewreview1, reviewrating1, useid1);
                        mArrayList.add(dictionary);

                        //어댑터에 반영
                        mAdapter.notifyDataSetChanged();
                    }
                    System.out.println("처음의 데이터를  받아와서 " + "마이리뷰" + reviewtitle1 + "제목" + reviewreview1 + "리뷰" + reviewrating1 + "별점" + useid1 + "작성한 유저" + useid1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Button buttonInsert = (Button) findViewById(R.id.check_add);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //버튼 입력시 다이얼로그로 화면
                AlertDialog.Builder builder = new AlertDialog.Builder(Subcheckreview.this);
                View view = LayoutInflater.from(Subcheckreview.this).inflate(R.layout.activity_uploadfood2, null, false);
                builder.setView(view);


                final Button Buttonupload = (Button) view.findViewById(R.id.upload_uploadbutton);
                final Button Buttonnoupload = (Button) view.findViewById(R.id.upload_noloadbutton);
                final EditText editTexttitle = (EditText) view.findViewById(R.id.upload_title);
                final EditText editTextreview = (EditText) view.findViewById(R.id.upload_review);
                final RatingBar editratingBar = (RatingBar) view.findViewById(R.id.upload_rating);
                final TextView edittext = (TextView) view.findViewById(R.id.scorew);

                final AlertDialog dialog = builder.create();



                editratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        edittext.setText(rating + "점");
                    }
                });

                Buttonnoupload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                //다이얼메뉴에서 작성하기 버튼 눌르면
                Buttonupload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //입력값을 가지고 와서
                        String reviewtitle = editTexttitle.getText().toString();
                        String reviewreview = editTextreview.getText().toString();
                        float reviewrating = editratingBar.getRating();
                        //arraylist(myreview)에 넣고
                        myreview dictionary = new myreview(reviewtitle, reviewreview, reviewrating, userid);
                        //첫줄에
//                        mArrayList.add(0, dictionary);

                        JSONObject uploadjsonObject = new JSONObject();
                        try {
                            uploadjsonObject.put("title", reviewtitle);
                            uploadjsonObject.put("review", reviewreview);
                            uploadjsonObject.put("ratingBar", reviewrating);
                            uploadjsonObject.put("userid", userid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        array.put(uploadjsonObject);
                        editor.putString(userid, array.toString());
                        editor.commit();

                        mArrayList.add(0,dictionary);
                        //어댑터에 반영
                        mAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        //데이터 넘겨받는곳
        Intent intent = getIntent();
        String title = intent.getStringExtra("contact_name1");
        String review = intent.getStringExtra("contact_name2");
        float ratingBar = intent.getFloatExtra("contact_name3", 0.0f);
        bytearray = getIntent().getByteArrayExtra("image");


        //값 넘겨받을때의 세팅하는 부분
//        TextView textViewtitle = (TextView) findViewById(R.id.check_textviewname123);
//        if (title != null) {
//            textViewtitle.setText(title);
//        }
//        TextView textViewrivew = (TextView) findViewById(R.id.check_textviewreview123);
//        if (review != null) {
//            textViewrivew.setText(review);
//        }
//        RatingBar ratingBarreview = (RatingBar) findViewById(R.id.check_ratingstar123);
//        if (intent != null) {
//            ratingBarreview.setRating(ratingBar);
//        }
//        ImageView imageView = (ImageView) findViewById(R.id.check_imageview123);
//        if (bytearray != null) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
//            imageView.setImageBitmap(bitmap);
//            Log.d(TAG, "사진 보낸후에 이미지뷰에 셋");
//        }


        //arraylist(myreview)에 넣고
        if ((review != null) && (title != null)) {
            myreview dictionary = new myreview(title, review, ratingBar, userid);
//            ArrayList<String> uploarray = new ArrayList<>();
//            uploarray.add(title);
//            uploarray.add(review);
//            uploarray.add(userid);
//            setarraypref(userid, uploarray);

            JSONObject uploadjsonObject = new JSONObject();
            try {
                uploadjsonObject.put("title", title);
                uploadjsonObject.put("review", review);
                uploadjsonObject.put("ratingBar", ratingBar);
                uploadjsonObject.put("userid", userid);
//                uploadjsonObject.put("image",bytearray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            array.put(uploadjsonObject);
            editor.putString(userid, array.toString());
            editor.commit();

            //첫줄에
            mArrayList.add(0, dictionary);
            //어댑터에 반영
            mAdapter.notifyDataSetChanged();
            Intent intent2 = getIntent();
            String check = "no";
            check = intent2.getStringExtra("bytitle");
            if(check!="no"){
                Subcheckreviewbytitle mainActivity = (Subcheckreviewbytitle) Subcheckreviewbytitle.subcheckreview;
                mainActivity.recreate();
                finish();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "subckeckreview onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "subckeckreview onResume");

//        preferences = getSharedPreferences("account", MODE_PRIVATE);
//        pref = getSharedPreferences("allreview", MODE_PRIVATE);
//        editor = pref.edit();
//
//
//        //이게 키값(리뷰작성시)으로도 쓰일 예정
//        String userid;
//        userid = preferences.getString("id", "");
//
//        Intent intent1 = getIntent();
//        ArrayList<myreview> mArrayList1 = new ArrayList<>();
//        System.out.println("아직 수정값받기전");
//        System.out.println(intent1.getSerializableExtra("modify12"));
//        if (intent1.getSerializableExtra("modify12") != null) {
//
//            JSONArray jsonArray12 = new JSONArray();
//            mArrayList1 = (ArrayList<myreview>) intent1.getSerializableExtra("modify12");
//            System.out.println("수정값받을시에" + mArrayList1);
//            for (int c = 0; c < mArrayList1.size(); c++) {
//                myreview backreview = mArrayList1.get(c);
//                String reviewtitle = backreview.getTitle();
//                String reviewreview = backreview.getReview();
//                float reviewrating = backreview.getRatingBar();
//                String userid1231 = backreview.getUserid();
//                try {
//                    JSONObject jsonObject12 = new JSONObject();
//                    jsonObject12.put("title", reviewtitle);
//                    jsonObject12.put("review", reviewreview);
//                    jsonObject12.put("ratingBar", reviewrating);
//                    jsonObject12.put("userid", userid1231);
//                    System.out.println("try안에서" + jsonObject12);
//                    System.out.println("반복분 동안 jsonObject12  " + jsonObject12);
//                    jsonArray12.put(jsonObject12);
//                    System.out.println("반복분 동안  jsonArray12  " + jsonArray12);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("데이터를 받아와서" + "제목" + reviewtitle + "리뷰" + reviewreview + "별점" + reviewrating + "작성한 유저" + userid1231);
//            }
//
//            System.out.println("마지막넣기전" + jsonArray12);
//            editor.remove(userid);
//            editor.commit();
//            editor.putString(userid, jsonArray12.toString());
//            editor.commit();


//        for(int ad=0; ad<mArrayList1.size();ad++){
//            myreview dictionary = mArrayList1.get(ad);
//            String reviewtitle = dictionary.getTitle();
//            String reviewreview = dictionary.getReview();
//            float reviewrating = dictionary.getRatingBar();
//            String userid1231 = dictionary.getUserid();
//
//            JSONObject uploadjsonObject = new JSONObject();
//            try {
//                uploadjsonObject.put("title", reviewtitle);
//                uploadjsonObject.put("review", reviewreview);
//                uploadjsonObject.put("ratingBar", reviewrating);
//                uploadjsonObject.put("userid", userid1231);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            array.put(uploadjsonObject);
//            editor.clear();
//            editor.putString(userid, array.toString());
//            editor.commit();
//        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "subckeckreview onStop");


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "subckeckreview onPause");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "subckeckreview onDestroy");
    }
}
/*
만들 리스트뷰

listview 예시
  static final String[] LIST_MENU = {"list1","list2","list3"};

   ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU);
        //arrayadapter의 생성자는 3개의 파라미터를 가집니다. ...(Context context, int resource, T[] objects)
//        context : 안드로이드 시스템에서 제공되는 어플리케이션 전역환경 정보에 대한 인터페이스
        //resource : View로 매핑될 Resource id."android.R.layout.simple_list_item_1"은
        // TextView 위젝으로 구성된 ListView 위젯으로 구성된 Listview 아이템 리소스 id
        //object: 배열로 선언된 사용자 데이터


        ListView listView = (ListView) findViewById(R.id.checkrivew_listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position);
            }
        });

 */
/*
package com.example.newapplecation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Dictionary;

public class Subcheckreview extends AppCompatActivity {

    private static String TAG = "subcheckreview";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkreview);

        preferences = getSharedPreferences("account", MODE_PRIVATE);

        String userid;
        userid = preferences.getString("id", "");

        Bitmap image;
        byte[] bytearray;

        ArrayList<myreview> mArrayList;
        adapter mAdapter;


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.checkrivew_recyclrview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mArrayList = new ArrayList<>();


        mAdapter = new adapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        Button buttonInsert = (Button) findViewById(R.id.check_add);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //버튼 입력시 다이얼로그로 화면
                AlertDialog.Builder builder = new AlertDialog.Builder(Subcheckreview.this);
                View view = LayoutInflater.from(Subcheckreview.this).inflate(R.layout.activity_uploadfood2, null, false);
                builder.setView(view);


                final Button Buttonupload = (Button) view.findViewById(R.id.upload_uploadbutton);
                final Button Buttonnoupload = (Button) view.findViewById(R.id.upload_noloadbutton);
                final EditText editTexttitle = (EditText) view.findViewById(R.id.upload_title);
                final EditText editTextreview = (EditText) view.findViewById(R.id.upload_review);
                final RatingBar editratingBar = (RatingBar) view.findViewById(R.id.upload_rating);
                final TextView edittext = (TextView) view.findViewById(R.id.score);

                final AlertDialog dialog = builder.create();


                Buttonnoupload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                //다이얼메뉴에서 작성하기 버튼 눌르면
                Buttonupload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //입력값을 가지고 와서
                        String reviewtitle = editTexttitle.getText().toString();
                        String reviewreview = editTextreview.getText().toString();
                        float reviewrating = editratingBar.getRating();
                        //arraylist(myreview)에 넣고
                        myreview dictionary = new myreview(reviewtitle, reviewreview, reviewrating,userid);
                        //첫줄에
//                        mArrayList.add(0, dictionary);

                        //아래에 추가
                        mArrayList.add(dictionary);

                        //어댑터에 반영
                        mAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        //데이터 넘겨받는곳
        Intent intent = getIntent();
        TextView textViewtitle = (TextView) findViewById(R.id.check_textviewname123);
        String title = intent.getStringExtra("contact_name1");
        if (title != null) {
            textViewtitle.setText(title);
        }

        TextView textViewrivew = (TextView) findViewById(R.id.check_textviewreview123);
        String review = intent.getStringExtra("contact_name2");
        if (review != null) {
            textViewrivew.setText(review);
        }

        RatingBar ratingBarreview = (RatingBar) findViewById(R.id.check_ratingstar123);
        float ratingBar = intent.getFloatExtra("contact_name3", 0.0f);
        if (intent != null) {
            ratingBarreview.setRating(ratingBar);
        }

        ImageView imageView = (ImageView) findViewById(R.id.check_imageview123);
        bytearray = getIntent().getByteArrayExtra("image");
        if (bytearray != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
            imageView.setImageBitmap(bitmap);
            Log.d(TAG, "사진 보낸후에 이미지뷰에 셋");
        }

        //arraylist(myreview)에 넣고
        if ((intent != null) && (review != null) && (title != null)) {
            myreview dictionary = new myreview(title, review, ratingBar,userid);
            //첫줄에
            mArrayList.add(0, dictionary);

            //어댑터에 반영
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "subckeckreview onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "subckeckreview onResume");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "subckeckreview onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "subckeckreview onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "subckeckreview onDestroy");
    }
}
 *///백업
/*  레이아웃에서 값 넘겨받을때의 부분
<RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <ImageView
            android:id="@+id/check_imageview123"
            android:layout_width="120dp"
            android:layout_height="120dp" />

        <TextView
            android:id="@+id/check_textviewname123"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="20dp"
            android:layout_toRightOf="@+id/check_imageview123"
            android:text="식당이름"
            android:textSize="30dp" />

        <RatingBar
            android:id="@+id/check_ratingstar123"
            style="@style/Widget.AppCompat.RatingBar.Indicator"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_below="@+id/check_textviewname123"
            android:layout_alignParentRight="true"
            android:layout_marginRight="30dp" />

        <TextView
            android:id="@+id/check_textviewreview123"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_below="@+id/check_ratingstar123"
            android:layout_alignParentRight="true"
            android:ellipsize="end"
            android:ems="7"
            android:maxLines="1"
            android:text="한줄평의 리뷰입니다."
            android:textSize="20dp" />
    </RelativeLayout>
 */