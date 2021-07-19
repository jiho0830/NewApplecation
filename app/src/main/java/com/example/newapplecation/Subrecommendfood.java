package com.example.newapplecation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newapplecation.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Subrecommendfood extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendfood);


        Button button = (Button) findViewById(R.id.recommendfood_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subrecommendfood.this, Subuploadfood.class);
                finish();
                startActivity(intent);
            }
        });


        sharedPreferences = getSharedPreferences("mapmarker", MODE_PRIVATE);
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

        //핸드폰을 작동시에는 (i -1) 이부분 수정필요
        String[] List2 = new String[jsonArray.length() - 1];
        System.out.println("조건문 생성전의 배열인텍스 확인 jsonArray.length()  : " + (jsonArray.length()));
        if (!jsonArray.isNull(0)) {
            System.out.println("마커들 생성 시작");
            for (int i = 1; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject12 = (JSONObject) jsonArray.get(i);
                    String title = (String) jsonObject12.get("title");
                    System.out.println("조건문 안에서의 생성 마커의 타이틀  : " + title);
                    List2[i - 1] = title;
                    System.out.println("조건문 안에서의 생성  List2  : " + List2[i - 1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, List2);
        ListView listView = (ListView) findViewById(R.id.recommendfood_list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strtext = (String) parent.getItemAtPosition(position);
                System.out.println("리스트뷰에서의 클릭시   " + strtext);
                Intent intent = new Intent(Subrecommendfood.this, Subuploadfood.class);
                intent.putExtra("recommend", strtext);
                finish();
                startActivity(intent);
            }
        });
    }
}