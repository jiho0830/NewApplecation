package com.example.newapplecation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Subfindaccount extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    JSONArray jsonArray = new JSONArray();
    TextView findid1, findid2, findmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findaccount);
        preferences = getSharedPreferences("account", MODE_PRIVATE);

        findid1 = findViewById(R.id.findid1);
        findid2 = findViewById(R.id.findid2);
        findmail = findViewById(R.id.findemail);

        RelativeLayout findaccount = findViewById(R.id.TextView_findaccount);
        findaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("account", MODE_PRIVATE);

                String newid = findid1.getText().toString();

                String exam1 = preferences.getString("account2", null);


                System.out.println(newid);
                try {
                    jsonArray = new JSONArray(exam1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int a = 0; a < jsonArray.length(); a++) {
                    try {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(a);
                        if (jsonObject.get("id").equals(newid)) {
                            Toast.makeText(Subfindaccount.this, "찾으시는 이메일은 " + jsonObject.get("email") + " 입니다", Toast.LENGTH_LONG).show();
                            break;
                        } else if (!jsonObject.get("id").equals(newid)) {
                            Toast.makeText(Subfindaccount.this, "찾으시는 이메일은 없습니다.", Toast.LENGTH_LONG).show();
                        }
//                        else {
//                            Toast.makeText(Subfindaccount.this, "찾으시는 이메일은 없습니다.", Toast.LENGTH_LONG).show();
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        RelativeLayout findpassword = findViewById(R.id.TextView_findpassword);
        findpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String exam1 = preferences.getString("account2", null);

                String newid = findid2.getText().toString();
                String newemail = findmail.getText().toString();

                try {
                    jsonArray = new JSONArray(exam1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int a = 0; a < jsonArray.length(); a++) {
                    try {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(a);
                        if (jsonObject.get("id").equals(newid)&&jsonObject.get("email").equals(newemail)) {
                            Toast.makeText(Subfindaccount.this, "찾으시는 비밀번호는 " + jsonObject.get("password") + " 입니다", Toast.LENGTH_LONG).show();
                            break;
                        } else {
                            Toast.makeText(Subfindaccount.this, "찾으시는 계정은 없습니다.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}