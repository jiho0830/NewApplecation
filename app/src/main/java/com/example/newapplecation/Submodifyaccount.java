package com.example.newapplecation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.zip.Deflater;

public class Submodifyaccount extends AppCompatActivity {

    TextView inputemail, inputpassword, inputid, exam;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    JSONArray jsonArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyaccount);

        inputemail = findViewById(R.id.modifycount_email);
        inputid = findViewById(R.id.modifycount_id);
        inputpassword = findViewById(R.id.modifycount_password);
        exam = findViewById(R.id.TextView_loginbutton);
        System.out.println(exam);

        preferences = getSharedPreferences("account", MODE_PRIVATE);

        String exam1 = preferences.getString("account2", null);

        try {
            jsonArray = new JSONArray(exam1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int a = 0; a < jsonArray.length(); a++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(a);
                if (jsonObject.get("id").equals(preferences.getString("id", ""))) {
                    inputid.setText(jsonObject.getString("id"));
                    inputemail.setText(jsonObject.getString("email"));
                    inputpassword.setText(jsonObject.getString("password"));
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        RelativeLayout modifyacount_account = findViewById(R.id.modifycount_account);
        modifyacount_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newpassword = inputpassword.getText().toString();

                editor = preferences.edit();

                editor.putString("password12", newpassword);

                editor.commit();

                Toast.makeText(Submodifyaccount.this, "계정이 수정되었습니다.", Toast.LENGTH_LONG).show();

                finish();
            }
        });
        Button logout = findViewById(R.id.modify_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) MainActivity.MainActivity1;
                mainActivity.finish();
                finish();
            }
        });

    }
}