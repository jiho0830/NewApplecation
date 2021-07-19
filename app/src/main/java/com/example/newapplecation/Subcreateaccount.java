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

        System.out.println("버튼누르기전에 preferences 저장되는값 : " + exam1);
        if (preferences.getString("account4", null) != null) {
            System.out.println("버튼누르기전에 account4는 비어있지 않다");
            try {
                jsonArray = new JSONArray(exam1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("버튼누르기전에 저장되는값 : " + jsonArray);
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
        System.out.println("버튼누르기 직전에 저장되는값 : " + jsonArray);
        //여기서 계정생성인데 이때 데이터를 저장하기
        //여기서 배열로 저장을 해서 여러개 계정 생성할려고한다
        RelativeLayout createcount_account = findViewById(R.id.createcount_account);
        createcount_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                내가 입력하는 계정의 정보들
                String newid = inputid.getText().toString();
                String newemail = inputemail.getText().toString();
                String newpassword = inputpassword.getText().toString();

                if(jsonArray.length()==0){
                    {
                        try {
                            jsonObject.put("id", newid);
                            jsonObject.put("email", newemail);
                            jsonObject.put("password", newpassword);

                            //그리고 JSONArray배열에 넣기
                            jsonArray.put(jsonObject);

                            //여기는 arraylist에 넣기
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
                            //여기서 오브젝트에 넣기
                            JSONObject jsonObject3 = new JSONObject();
                            try {
                                jsonObject3.put("aee", jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            System.out.println("account에 들어다는값 : " + jsonObject);


                            editor.putString("id", newid);
                            editor.putString("email12", newemail);
                            editor.putString("password12", newpassword);

                            //1번은 오브젝트를
                            editor.putString("account1", String.valueOf(jsonObject));

                            //2번은 1번의 오브젝트를 jsonarray배열에 넣어서
                            editor.putString("account2", String.valueOf(jsonArray));

                            //3번은 jsonobject타입의 arraylist를 안에다가는 jsonarray배열의 오브젝트를 넣어놈
                            editor.putString("account3", String.valueOf(arrayList));

                            //4번은 2번을 다시 오브젝트에 넣어서
                            editor.putString("account4", String.valueOf(jsonObject3));

                            editor.commit();
                            System.out.println("버튼누른후에 jsonArray 저장되는값 : " + jsonArray);
                            System.out.println("버튼누른후에 arrayList 저장되는값 : " + arrayList);
                            System.out.println("버튼누른후에 jsonObject2 저장되는값 : " + jsonObject3);
                            Toast.makeText(Subcreateaccount.this, "계정이 생성되었습니다.", Toast.LENGTH_LONG).show();
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

                                        //그리고 JSONArray배열에 넣기
                                        jsonArray.put(jsonObject);

                                        //여기는 arraylist에 넣기
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
                                        //여기서 오브젝트에 넣기
                                        JSONObject jsonObject3 = new JSONObject();
                                        try {
                                            jsonObject3.put("aee", jsonArray);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        System.out.println("account에 들어다는값 : " + jsonObject);


                                        editor.putString("id", newid);
                                        editor.putString("email12", newemail);
                                        editor.putString("password12", newpassword);

                                        //1번은 오브젝트를
                                        editor.putString("account1", String.valueOf(jsonObject));

                                        //2번은 1번의 오브젝트를 jsonarray배열에 넣어서
                                        editor.putString("account2", String.valueOf(jsonArray));

                                        //3번은 jsonobject타입의 arraylist를 안에다가는 jsonarray배열의 오브젝트를 넣어놈
                                        editor.putString("account3", String.valueOf(arrayList));

                                        //4번은 2번을 다시 오브젝트에 넣어서
                                        editor.putString("account4", String.valueOf(jsonObject3));

                                        editor.commit();
                                        System.out.println("버튼누른후에 jsonArray 저장되는값 : " + jsonArray);
                                        System.out.println("버튼누른후에 arrayList 저장되는값 : " + arrayList);
                                        System.out.println("버튼누른후에 jsonObject2 저장되는값 : " + jsonObject3);
                                        Toast.makeText(Subcreateaccount.this, "계정이 생성되었습니다.", Toast.LENGTH_LONG).show();
                                        finish();
                                        break;
                                    }

                                } else {
                                    Toast.makeText(Subcreateaccount.this, "이메일이 중복됩니다..", Toast.LENGTH_LONG).show();
                                    break;
                                }
                            } else {
                                Toast.makeText(Subcreateaccount.this, "아이디가 중복됩니다..", Toast.LENGTH_LONG).show();
                                break;
                            }
                        } else{
                            {
                                jsonObject.put("id", newid);
                                jsonObject.put("email", newemail);
                                jsonObject.put("password", newpassword);

                                //그리고 JSONArray배열에 넣기
                                jsonArray.put(jsonObject);

                                //여기는 arraylist에 넣기
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
                                //여기서 오브젝트에 넣기
                                JSONObject jsonObject3 = new JSONObject();
                                try {
                                    jsonObject3.put("aee", jsonArray);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                System.out.println("account에 들어다는값 : " + jsonObject);


                                editor.putString("id", newid);
                                editor.putString("email12", newemail);
                                editor.putString("password12", newpassword);

                                //1번은 오브젝트를
                                editor.putString("account1", String.valueOf(jsonObject));

                                //2번은 1번의 오브젝트를 jsonarray배열에 넣어서
                                editor.putString("account2", String.valueOf(jsonArray));

                                //3번은 jsonobject타입의 arraylist를 안에다가는 jsonarray배열의 오브젝트를 넣어놈
                                editor.putString("account3", String.valueOf(arrayList));

                                //4번은 2번을 다시 오브젝트에 넣어서
                                editor.putString("account4", String.valueOf(jsonObject3));

                                editor.commit();
                                System.out.println("버튼누른후에 jsonArray 저장되는값 : " + jsonArray);
                                System.out.println("버튼누른후에 arrayList 저장되는값 : " + arrayList);
                                System.out.println("버튼누른후에 jsonObject2 저장되는값 : " + jsonObject3);
                                Toast.makeText(Subcreateaccount.this, "계정이 생성되었습니다.", Toast.LENGTH_LONG).show();
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
//                            Toast.makeText(Subcreateaccount.this, "계정이 생성되었다", Toast.LENGTH_LONG).show();
//
//                            //이제 여기서 accoint를 json으로 저장한다음에 구현하는것을 해보자
//                            //아래는 jsonobject에 내가 입력한 값얼 넣고
//                            jsonObject.put("id", newid);
//                            jsonObject.put("email", newemail);
//                            jsonObject.put("password", newpassword);
//
//                            //그리고 JSONArray배열에 넣기
//                            jsonArray.put(jsonObject);
//                            //여기는 arraylist에 넣기
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
//                            //여기서 오브젝트에 넣기
//                            JSONObject jsonObject3 = new JSONObject();
//                            try {
//                                jsonObject3.put("aee", jsonArray);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                            System.out.println("account에 들어다는값 : " + jsonObject);
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
//                            System.out.println("버튼누른후에 jsonArray 저장되는값 : " + jsonArray);
//                            System.out.println("버튼누른후에 arrayList 저장되는값 : " + arrayList);
//                            System.out.println("버튼누른후에 jsonObject2 저장되는값 : " + jsonObject3);
//                            Toast.makeText(Subcreateaccount.this, "계정이 생성되었습니다.", Toast.LENGTH_LONG).show();
//                            finish();
//                        }
//                        else {
//                            Toast.makeText(Subcreateaccount.this, "생성하려는 계정은 이미있다.", Toast.LENGTH_LONG).show();
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

    //선택한 사진을 이미지뷰에 넣는 결과
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
                Toast.makeText(this, "사진선택취소", Toast.LENGTH_LONG).show();
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
//혹시 모르는 ㅈ버그때문에 안될까봐백업
// {
//            @Override
//            public void onClick(View v) {
//
//                //내가 입력하는 계정의 정보들
//                String newid = inputid.getText().toString();
//                String newemail = inputemail.getText().toString();
//                String newpassword = inputpassword.getText().toString();
//
//
//                Toast.makeText(Subcreateaccount.this, "계정이 생성되었다", Toast.LENGTH_LONG).show();
//
//                //이제 여기서 accoint를 json으로 저장한다음에 구현하는것을 해보자
//                //아래는 jsonobject에 내가 입력한 값얼 넣고
//
//                try {
//                    jsonObject.put("id", newid);
//                    jsonObject.put("email", newemail);
//                    jsonObject.put("password", newpassword);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                //그리고 JSONArray배열에 넣기
//                jsonArray.put(jsonObject);
//                //여기는 arraylist에 넣기
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
//                //여기서 오브젝트에 넣기
//                JSONObject jsonObject3 = new JSONObject();
//                try {
//                    jsonObject3.put("aee", jsonArray);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("account에 들어다는값 : " + jsonObject);
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
//                System.out.println("버튼누른후에 jsonArray 저장되는값 : " + jsonArray);
//                System.out.println("버튼누른후에 arrayList 저장되는값 : " + arrayList);
//                System.out.println("버튼누른후에 jsonObject2 저장되는값 : " + jsonObject3);
//                Toast.makeText(Subcreateaccount.this, "계정이 생성되었습니다.", Toast.LENGTH_LONG).show();
//                finish();
//            }
////                for (int a = 0; a < jsonArray.length(); a++) {
////                    try {
////                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(a);
////                        if (!(jsonObject1.get("id").equals(newid) && jsonObject1.get("email").equals(newemail) && jsonObject1.get("password").equals(newpassword)))
////                        {
////                            Toast.makeText(Subcreateaccount.this, "계정이 생성되었다", Toast.LENGTH_LONG).show();
////
////                            //이제 여기서 accoint를 json으로 저장한다음에 구현하는것을 해보자
////                            //아래는 jsonobject에 내가 입력한 값얼 넣고
////                            jsonObject.put("id", newid);
////                            jsonObject.put("email", newemail);
////                            jsonObject.put("password", newpassword);
////
////                            //그리고 JSONArray배열에 넣기
////                            jsonArray.put(jsonObject);
////                            //여기는 arraylist에 넣기
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
////                            //여기서 오브젝트에 넣기
////                            JSONObject jsonObject3 = new JSONObject();
////                            try {
////                                jsonObject3.put("aee", jsonArray);
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////
////                            System.out.println("account에 들어다는값 : " + jsonObject);
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
////                            System.out.println("버튼누른후에 jsonArray 저장되는값 : " + jsonArray);
////                            System.out.println("버튼누른후에 arrayList 저장되는값 : " + arrayList);
////                            System.out.println("버튼누른후에 jsonObject2 저장되는값 : " + jsonObject3);
////                            Toast.makeText(Subcreateaccount.this, "계정이 생성되었습니다.", Toast.LENGTH_LONG).show();
////                            finish();
////                        }
////                        else {
////                            Toast.makeText(Subcreateaccount.this, "생성하려는 계정은 이미있다.", Toast.LENGTH_LONG).show();
////                        }
////
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
//        });