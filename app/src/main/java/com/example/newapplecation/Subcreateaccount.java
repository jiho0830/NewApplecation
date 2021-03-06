package com.example.newapplecation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class Subcreateaccount extends AppCompatActivity {

    private Context mcontext;
    TextView TextView_explain;
    TextView Textview_aaddimage;
    ImageView createcount_uplodimage;
    TextView inputid, inputemail, inputpassword;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    private static String TAG = "subcreateaccount";

    static final int REQUSET_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        mcontext = this;

        preferences = getSharedPreferences("account", MODE_PRIVATE);
        editor = preferences.edit();

        inputid = findViewById(R.id.createaccount_id);
        inputemail = findViewById(R.id.createaccount_email);
        inputpassword = findViewById(R.id.createaccount_password);


        TextView_explain = findViewById(R.id.TextView_explain);
        TextView_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subexplain.class);
                startActivity(intent);
            }
        });

        TextView TextView_explain1 = findViewById(R.id.TextView_explain1);
        TextView_explain1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subexplain1.class);
                startActivity(intent);
            }
        });

        createcount_uplodimage = findViewById(R.id.createcount_uplodimage);

        Textview_aaddimage = findViewById(R.id.createcount_addimage);
        Textview_aaddimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUSET_CODE);
            }
        });

        String exam1 = preferences.getString("account2", null);

        System.out.println("????????????????????? preferences ??????????????? : " + exam1);
        if (preferences.getString("account4", null) != null) {
            System.out.println("????????????????????? account4??? ???????????? ??????");
            try {
                jsonArray = new JSONArray(exam1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("????????????????????? ??????????????? : " + jsonArray);
//        if (!jsonArray.isNull(0)) {
//            for (int m = 0; m > jsonArray.length(); m++) {
//                try {
//                    JSONObject object1 = jsonArray.getJSONObject(m);
//                    jsonArray.put(object1);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
        System.out.println("??????????????? ????????? ??????????????? : " + jsonArray);
        //????????? ?????????????????? ?????? ???????????? ????????????
        //????????? ????????? ????????? ?????? ????????? ?????? ?????????????????????
        RelativeLayout createcount_account = findViewById(R.id.createcount_account);
        createcount_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                ?????? ???????????? ????????? ?????????
                String newid = inputid.getText().toString();
                String newemail = inputemail.getText().toString();
                String newpassword = inputpassword.getText().toString();

                if(jsonArray.length()==0){
                    {
                        try {
                            jsonObject.put("id", newid);
                            jsonObject.put("email", newemail);
                            jsonObject.put("password", newpassword);

                            //????????? JSONArray????????? ??????
                            jsonArray.put(jsonObject);

                            //????????? arraylist??? ??????
                            ArrayList<JSONObject> arrayList = new ArrayList<>();
                            for (int k = 0; k < jsonArray.length(); k++) {
                                JSONObject jsonObject2 = new JSONObject();
                                try {
                                    jsonObject2 = jsonArray.getJSONObject(k);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                arrayList.add(jsonObject2);
                            }
                            //????????? ??????????????? ??????
                            JSONObject jsonObject3 = new JSONObject();
                            try {
                                jsonObject3.put("aee", jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            System.out.println("account??? ??????????????? : " + jsonObject);


                            editor.putString("id", newid);
                            editor.putString("email12", newemail);
                            editor.putString("password12", newpassword);

                            //1?????? ???????????????
                            editor.putString("account1", String.valueOf(jsonObject));

                            //2?????? 1?????? ??????????????? jsonarray????????? ?????????
                            editor.putString("account2", String.valueOf(jsonArray));

                            //3?????? jsonobject????????? arraylist??? ??????????????? jsonarray????????? ??????????????? ?????????
                            editor.putString("account3", String.valueOf(arrayList));

                            //4?????? 2?????? ?????? ??????????????? ?????????
                            editor.putString("account4", String.valueOf(jsonObject3));

                            editor.commit();
                            System.out.println("?????????????????? jsonArray ??????????????? : " + jsonArray);
                            System.out.println("?????????????????? arrayList ??????????????? : " + arrayList);
                            System.out.println("?????????????????? jsonObject2 ??????????????? : " + jsonObject3);
                            Toast.makeText(Subcreateaccount.this, "????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                for (int m = 0; m < jsonArray.length(); m++) {
                    try {
                        JSONObject testjson = (JSONObject) jsonArray.get(m);
                        if(!jsonArray.isNull(0)){
                            if (!testjson.get("id").equals(newid)) {
                                if (!testjson.get("email").equals(newemail)) {
                                    {
                                        jsonObject.put("id", newid);
                                        jsonObject.put("email", newemail);
                                        jsonObject.put("password", newpassword);

                                        //????????? JSONArray????????? ??????
                                        jsonArray.put(jsonObject);

                                        //????????? arraylist??? ??????
                                        ArrayList<JSONObject> arrayList = new ArrayList<>();
                                        for (int k = 0; k < jsonArray.length(); k++) {
                                            JSONObject jsonObject2 = new JSONObject();
                                            try {
                                                jsonObject2 = jsonArray.getJSONObject(k);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            arrayList.add(jsonObject2);
                                        }
                                        //????????? ??????????????? ??????
                                        JSONObject jsonObject3 = new JSONObject();
                                        try {
                                            jsonObject3.put("aee", jsonArray);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        System.out.println("account??? ??????????????? : " + jsonObject);


                                        editor.putString("id", newid);
                                        editor.putString("email12", newemail);
                                        editor.putString("password12", newpassword);

                                        //1?????? ???????????????
                                        editor.putString("account1", String.valueOf(jsonObject));

                                        //2?????? 1?????? ??????????????? jsonarray????????? ?????????
                                        editor.putString("account2", String.valueOf(jsonArray));

                                        //3?????? jsonobject????????? arraylist??? ??????????????? jsonarray????????? ??????????????? ?????????
                                        editor.putString("account3", String.valueOf(arrayList));

                                        //4?????? 2?????? ?????? ??????????????? ?????????
                                        editor.putString("account4", String.valueOf(jsonObject3));

                                        editor.commit();
                                        System.out.println("?????????????????? jsonArray ??????????????? : " + jsonArray);
                                        System.out.println("?????????????????? arrayList ??????????????? : " + arrayList);
                                        System.out.println("?????????????????? jsonObject2 ??????????????? : " + jsonObject3);
                                        Toast.makeText(Subcreateaccount.this, "????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                                        finish();
                                        break;
                                    }

                                } else {
                                    Toast.makeText(Subcreateaccount.this, "???????????? ???????????????..", Toast.LENGTH_LONG).show();
                                    break;
                                }
                            } else {
                                Toast.makeText(Subcreateaccount.this, "???????????? ???????????????..", Toast.LENGTH_LONG).show();
                                break;
                            }
                        } else{
                            {
                                jsonObject.put("id", newid);
                                jsonObject.put("email", newemail);
                                jsonObject.put("password", newpassword);

                                //????????? JSONArray????????? ??????
                                jsonArray.put(jsonObject);

                                //????????? arraylist??? ??????
                                ArrayList<JSONObject> arrayList = new ArrayList<>();
                                for (int k = 0; k < jsonArray.length(); k++) {
                                    JSONObject jsonObject2 = new JSONObject();
                                    try {
                                        jsonObject2 = jsonArray.getJSONObject(k);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    arrayList.add(jsonObject2);
                                }
                                //????????? ??????????????? ??????
                                JSONObject jsonObject3 = new JSONObject();
                                try {
                                    jsonObject3.put("aee", jsonArray);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                System.out.println("account??? ??????????????? : " + jsonObject);


                                editor.putString("id", newid);
                                editor.putString("email12", newemail);
                                editor.putString("password12", newpassword);

                                //1?????? ???????????????
                                editor.putString("account1", String.valueOf(jsonObject));

                                //2?????? 1?????? ??????????????? jsonarray????????? ?????????
                                editor.putString("account2", String.valueOf(jsonArray));

                                //3?????? jsonobject????????? arraylist??? ??????????????? jsonarray????????? ??????????????? ?????????
                                editor.putString("account3", String.valueOf(arrayList));

                                //4?????? 2?????? ?????? ??????????????? ?????????
                                editor.putString("account4", String.valueOf(jsonObject3));

                                editor.commit();
                                System.out.println("?????????????????? jsonArray ??????????????? : " + jsonArray);
                                System.out.println("?????????????????? arrayList ??????????????? : " + arrayList);
                                System.out.println("?????????????????? jsonObject2 ??????????????? : " + jsonObject3);
                                Toast.makeText(Subcreateaccount.this, "????????? ?????????????????????.", Toast.LENGTH_LONG).show();
                                finish();
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


//                for (int a = 0; a < jsonArray.length(); a++) {
//                    try {
//                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(a);
//                        if (!(jsonObject1.get("id").equals(newid) && jsonObject1.get("email").equals(newemail) && jsonObject1.get("password").equals(newpassword)))
//                        {
//                            Toast.makeText(Subcreateaccount.this, "????????? ???????????????", Toast.LENGTH_LONG).show();
//
//                            //?????? ????????? accoint??? json?????? ?????????????????? ?????????????????? ?????????
//                            //????????? jsonobject??? ?????? ????????? ?????? ??????
//                            jsonObject.put("id", newid);
//                            jsonObject.put("email", newemail);
//                            jsonObject.put("password", newpassword);
//
//                            //????????? JSONArray????????? ??????
//                            jsonArray.put(jsonObject);
//                            //????????? arraylist??? ??????
//                            ArrayList<JSONObject> arrayList = new ArrayList<>();
//                            for (int k = 0; k < jsonArray.length(); k++) {
//                                JSONObject jsonObject2 = new JSONObject();
//                                try {
//                                    jsonObject2 = jsonArray.getJSONObject(k);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                arrayList.add(jsonObject2);
//                            }
//                            //????????? ??????????????? ??????
//                            JSONObject jsonObject3 = new JSONObject();
//                            try {
//                                jsonObject3.put("aee", jsonArray);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                            System.out.println("account??? ??????????????? : " + jsonObject);
//
//
//                            editor.putString("id", newid);
//                            editor.putString("email12", newemail);
//                            editor.putString("password12", newpassword);
//                            editor.putString("account1", String.valueOf(jsonObject));
//                            editor.putString("account2", String.valueOf(jsonArray));
//                            editor.putString("account3", String.valueOf(arrayList));
//                            editor.putString("account4", String.valueOf(jsonObject3));
//                            editor.commit();
//                            System.out.println("?????????????????? jsonArray ??????????????? : " + jsonArray);
//                            System.out.println("?????????????????? arrayList ??????????????? : " + arrayList);
//                            System.out.println("?????????????????? jsonObject2 ??????????????? : " + jsonObject3);
//                            Toast.makeText(Subcreateaccount.this, "????????? ?????????????????????.", Toast.LENGTH_LONG).show();
//                            finish();
//                        }
//                        else {
//                            Toast.makeText(Subcreateaccount.this, "??????????????? ????????? ????????????.", Toast.LENGTH_LONG).show();
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
        });
    }

    public void setStringArray(String key, ArrayList<String> values) {
        preferences = getSharedPreferences("account123", MODE_PRIVATE);
        editor = preferences.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    private ArrayList<String> getStringArray(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    //????????? ????????? ??????????????? ?????? ??????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUSET_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    createcount_uplodimage.setImageBitmap(img);
                } catch (Exception e) {

                }
            }
            //
            else if (requestCode == REQUSET_CODE) {
                Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "subcreateaccount onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "subcreateaccount onResume");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "subcreateaccount onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "subcreateaccount onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "subcreateaccount onDestroy");
    }

}
//?????? ????????? ?????????????????? ??????????????????
// {
//            @Override
//            public void onClick(View v) {
//
//                //?????? ???????????? ????????? ?????????
//                String newid = inputid.getText().toString();
//                String newemail = inputemail.getText().toString();
//                String newpassword = inputpassword.getText().toString();
//
//
//                Toast.makeText(Subcreateaccount.this, "????????? ???????????????", Toast.LENGTH_LONG).show();
//
//                //?????? ????????? accoint??? json?????? ?????????????????? ?????????????????? ?????????
//                //????????? jsonobject??? ?????? ????????? ?????? ??????
//
//                try {
//                    jsonObject.put("id", newid);
//                    jsonObject.put("email", newemail);
//                    jsonObject.put("password", newpassword);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                //????????? JSONArray????????? ??????
//                jsonArray.put(jsonObject);
//                //????????? arraylist??? ??????
//                ArrayList<JSONObject> arrayList = new ArrayList<>();
//                for (int k = 0; k < jsonArray.length(); k++) {
//                    JSONObject jsonObject2 = new JSONObject();
//                    try {
//                        jsonObject2 = jsonArray.getJSONObject(k);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    arrayList.add(jsonObject2);
//                }
//                //????????? ??????????????? ??????
//                JSONObject jsonObject3 = new JSONObject();
//                try {
//                    jsonObject3.put("aee", jsonArray);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("account??? ??????????????? : " + jsonObject);
//
//
//                editor.putString("id", newid);
//                editor.putString("email12", newemail);
//                editor.putString("password12", newpassword);
//                editor.putString("account1", String.valueOf(jsonObject));
//                editor.putString("account2", String.valueOf(jsonArray));
//                editor.putString("account3", String.valueOf(arrayList));
//                editor.putString("account4", String.valueOf(jsonObject3));
//                editor.commit();
//                System.out.println("?????????????????? jsonArray ??????????????? : " + jsonArray);
//                System.out.println("?????????????????? arrayList ??????????????? : " + arrayList);
//                System.out.println("?????????????????? jsonObject2 ??????????????? : " + jsonObject3);
//                Toast.makeText(Subcreateaccount.this, "????????? ?????????????????????.", Toast.LENGTH_LONG).show();
//                finish();
//            }
////                for (int a = 0; a < jsonArray.length(); a++) {
////                    try {
////                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(a);
////                        if (!(jsonObject1.get("id").equals(newid) && jsonObject1.get("email").equals(newemail) && jsonObject1.get("password").equals(newpassword)))
////                        {
////                            Toast.makeText(Subcreateaccount.this, "????????? ???????????????", Toast.LENGTH_LONG).show();
////
////                            //?????? ????????? accoint??? json?????? ?????????????????? ?????????????????? ?????????
////                            //????????? jsonobject??? ?????? ????????? ?????? ??????
////                            jsonObject.put("id", newid);
////                            jsonObject.put("email", newemail);
////                            jsonObject.put("password", newpassword);
////
////                            //????????? JSONArray????????? ??????
////                            jsonArray.put(jsonObject);
////                            //????????? arraylist??? ??????
////                            ArrayList<JSONObject> arrayList = new ArrayList<>();
////                            for (int k = 0; k < jsonArray.length(); k++) {
////                                JSONObject jsonObject2 = new JSONObject();
////                                try {
////                                    jsonObject2 = jsonArray.getJSONObject(k);
////                                } catch (JSONException e) {
////                                    e.printStackTrace();
////                                }
////                                arrayList.add(jsonObject2);
////                            }
////                            //????????? ??????????????? ??????
////                            JSONObject jsonObject3 = new JSONObject();
////                            try {
////                                jsonObject3.put("aee", jsonArray);
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////
////                            System.out.println("account??? ??????????????? : " + jsonObject);
////
////
////                            editor.putString("id", newid);
////                            editor.putString("email12", newemail);
////                            editor.putString("password12", newpassword);
////                            editor.putString("account1", String.valueOf(jsonObject));
////                            editor.putString("account2", String.valueOf(jsonArray));
////                            editor.putString("account3", String.valueOf(arrayList));
////                            editor.putString("account4", String.valueOf(jsonObject3));
////                            editor.commit();
////                            System.out.println("?????????????????? jsonArray ??????????????? : " + jsonArray);
////                            System.out.println("?????????????????? arrayList ??????????????? : " + arrayList);
////                            System.out.println("?????????????????? jsonObject2 ??????????????? : " + jsonObject3);
////                            Toast.makeText(Subcreateaccount.this, "????????? ?????????????????????.", Toast.LENGTH_LONG).show();
////                            finish();
////                        }
////                        else {
////                            Toast.makeText(Subcreateaccount.this, "??????????????? ????????? ????????????.", Toast.LENGTH_LONG).show();
////                        }
////
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
//        });