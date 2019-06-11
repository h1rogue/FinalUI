package com.example.finalui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity implements VvVolleyInterface{
    private TextInputEditText editphone,editpassword;
    private Button button;
    private String GEN_ID;
    private String phonestr,passwordstr;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editphone=findViewById(R.id.phone);
        editpassword=findViewById(R.id.password);
        button=findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        generateRandomString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(preferences.getString("token",null) != null){
            ApplicationVariable.ACCOUNT_DATA.phone=preferences.getString("username",null);
            ApplicationVariable.ACCOUNT_DATA.token=preferences.getString("token",null);
            progressDialog.show();
            Log.d("DSK_OPER", preferences.getString("token",null));
                requestUserDetails();
        }
    }
    public void Login(View view) {
        if(TextUtils.isEmpty(editphone.getText().toString().trim())){
            editphone.setError("This field can't be empty");
        }
        else if(TextUtils.isEmpty(editpassword.getText().toString().trim())){
            editpassword.setError("This field can't be empty");
        }
        else
        {
            Log.d("DSK_OPER","LoginButtonClicked");
            progressDialog.show();
           phonestr = editphone.getText().toString().trim();
           passwordstr = editpassword.getText().toString().trim();

            VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
            HashMap params = new HashMap<>();
            params.put("phone", phonestr);
            params.put("key", passwordstr);
            params.put("regId", GEN_ID);
            vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/account/login", params);
        }
    }

    private void generateRandomString() {
        Log.d("DSK_OPER","generateRandomString");
        //Random String generation
        char[] chars1 = "!@#$N62GHRVWXY78DEF01STUIJKL34Z9AB5MOPQC".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++) {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        GEN_ID = sb1.toString();
        ApplicationVariable.ACCOUNT_DATA.reg_id=GEN_ID;
    }


    @Override
    public void onTaskComplete(String result) {
        Log.d("DSK_OPER", "SERVER_RESPONSE: " + result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getString("responseFor").equals("login")){
                Log.d("DSK_OPER","responseForLogin");
                String token = jsonObject.getString("token");
                CreateSharedPref("token", token);
                CreateSharedPref("username", phonestr);
                ApplicationVariable.ACCOUNT_DATA.phone=phonestr;
                ApplicationVariable.ACCOUNT_DATA.token=token;
                requestUserDetails();
            }
            else if(jsonObject.getString("responseFor").equals("userDetails")){
                Log.d("DSK_OPER","responseforuserDetails");
                ApplicationVariable.ACCOUNT_DATA.emp_id=jsonObject.getString("emp_id");
                ApplicationVariable.ACCOUNT_DATA.name=jsonObject.getString("name");
                ApplicationVariable.ACCOUNT_DATA.dob=jsonObject.getString("dob");
                ApplicationVariable.ACCOUNT_DATA.address=jsonObject.getString("address");
                ApplicationVariable.ACCOUNT_DATA.contact=jsonObject.getString("phone");
                ApplicationVariable.ACCOUNT_DATA.role=jsonObject.getString("role");
                ApplicationVariable.ACCOUNT_DATA.salary=jsonObject.getString("salary");
                ApplicationVariable.ACCOUNT_DATA.blood_group=jsonObject.getString("blood_group");
                ApplicationVariable.ACCOUNT_DATA.email=jsonObject.getString("email");
                ApplicationVariable.ACCOUNT_DATA.city=jsonObject.getString("city");

                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
                finish();
            }
            else
            {
                progressDialog.dismiss();
                Log.d("Token","Not found");
                Toast.makeText(LoginActivity.this, "Invalid Phone or password", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestUserDetails() {
        Log.d("DSK_OPER","requestUserDetails");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", sharedPreferences.getString("username",null));
        params.put("token", sharedPreferences.getString("token",null));
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/account/details", params);
    }

    private void CreateSharedPref(String name,String val) {
        Log.d("DSK_OPER","createSharedPrefOfToken");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name,val);
        editor.apply();
    }
}
