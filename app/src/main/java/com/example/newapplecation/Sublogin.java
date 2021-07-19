package com.example.newapplecation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sublogin extends AppCompatActivity {
    TextView TextView_findaccount;
    TextView TextView_createaccount;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView loginemail, loginpassword;
    JSONArray jsonArray = new JSONArray();
    private static String TAG = "sublogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView_findaccount = findViewById(R.id.TextView_findaccount);
        TextView_findaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subfindaccount.class);
                startActivity(intent);
            }
        });

        loginemail = findViewById(R.id.login_email);
        loginpassword = findViewById(R.id.login_password);

        preferences = getSharedPreferences("account", MODE_PRIVATE);

        TextView textViewlogin = findViewById(R.id.login_button);
        textViewlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("account", MODE_PRIVATE);
                String inputemail = loginemail.getText().toString();
                String inputpassword = loginpassword.getText().toString();

                String exam1 = preferences.getString("account2", null);

                try {
                    jsonArray = new JSONArray(exam1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                if (preferences.getString("email12", "").equals(inputemail) && preferences.getString("password12", "").equals(inputpassword)) {
//                    Toast.makeText(Sublogin.this, "어서오세요" + preferences.getString("id", "") + " 님", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(Sublogin.this, "아이디 및 비밀번호가 안맞는다.", Toast.LENGTH_LONG).show();
//                }
                for (int a = 0; a < jsonArray.length(); a++) {
                    try {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(a);
                        if (jsonObject.get("email").equals(inputemail) && jsonObject.get("password").equals(inputpassword)) {
                            Toast.makeText(Sublogin.this, "어서오세요" + jsonObject.get("id") + " 님", Toast.LENGTH_LONG).show();
                            editor = preferences.edit();
                            editor.putString("id", (String) jsonObject.get("id"));
                            editor.commit();
                            loginemail.setText("");
                            loginpassword.setText("");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            break;
                        } else {
                            if(a==jsonArray.length()){
                                Toast.makeText(Sublogin.this, "아이디 및 비밀번호가 안맞는다.", Toast.LENGTH_LONG).show();
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });


        TextView_createaccount = findViewById(R.id.TextView_createaccount);
        TextView_createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subcreateaccount.class);
                startActivity(intent);
            }
        });

        RelativeLayout RelativeLayout_loginkko = findViewById(R.id.RelativeLayout_loginkko);
        RelativeLayout_loginkko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "sublogin onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "sublogin onResume");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "sublogin onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "sublogin onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "sublogin onDestroy");
    }

}