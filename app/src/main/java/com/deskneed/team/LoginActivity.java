package com.deskneed.team;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deskneed.team.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity implements VvVolleyInterface {
    private TextInputEditText editphone, editpassword;
    private Button button;
    private String GEN_ID;
    private String phonestr, passwordstr;
    private ProgressDialog progressDialog;
    LinearLayout linearroot;
    LinearLayout loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editphone = findViewById(R.id.phone);
        editpassword = findViewById(R.id.password);
        button = findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        linearroot = findViewById(R.id.rootlinear);

        loginLayout = findViewById(R.id.login_layout);
        generateRandomString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (preferences.getString("token", null) != null) {
            ApplicationVariable.ACCOUNT_DATA.phone = preferences.getString("username", null);
            ApplicationVariable.ACCOUNT_DATA.token = preferences.getString("token", null);
            progressDialog.show();
            loginLayout.setVisibility(View.GONE);
            requestUserDetails();
        }
    }

    public void Login(View view) {
        if (TextUtils.isEmpty(editphone.getText().toString().trim())) {
            editphone.setError("This field can't be empty");
        } else if (TextUtils.isEmpty(editpassword.getText().toString().trim())) {
            editpassword.setError("This field can't be empty");
        } else {
            Log.d("DSK_OPER", "LoginButtonClicked");
            progressDialog.show();
            linearroot.setAlpha(1);
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
        Log.d("DSK_OPER", "generateRandomString");
        //Random String generation
        char[] chars1 = "!@#$N62GHRVWXY78DEF01STUIJKL34Z9AB5MOPQC".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++) {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        GEN_ID = sb1.toString();
        ApplicationVariable.ACCOUNT_DATA.reg_id = GEN_ID;
    }


    @Override
    public void onTaskComplete(String result) {
        Log.d("DSK_OPER", "SERVER_RESPONSE: " + result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getString("responseFor").equals("login")) {
                String token = jsonObject.getString("token");
                CreateSharedPref("token", token);
                CreateSharedPref("username", phonestr);
                ApplicationVariable.ACCOUNT_DATA.phone = phonestr;
                ApplicationVariable.ACCOUNT_DATA.token = token;

                requestUserDetails();
            } else if (jsonObject.getString("responseFor").equals("userDetails")) {
                ApplicationVariable.ACCOUNT_DATA.emp_id = jsonObject.getString("emp_id");
                ApplicationVariable.ACCOUNT_DATA.name = jsonObject.getString("name");
                ApplicationVariable.ACCOUNT_DATA.dob = jsonObject.getString("dob");
                ApplicationVariable.ACCOUNT_DATA.address = jsonObject.getString("address");
                ApplicationVariable.ACCOUNT_DATA.contact = jsonObject.getString("phone");
                ApplicationVariable.ACCOUNT_DATA.role = jsonObject.getString("role");
                ApplicationVariable.ACCOUNT_DATA.salary = jsonObject.getString("salary");
                ApplicationVariable.ACCOUNT_DATA.blood_group = jsonObject.getString("blood_group");
                ApplicationVariable.ACCOUNT_DATA.email = jsonObject.getString("email");
                ApplicationVariable.ACCOUNT_DATA.city = jsonObject.getString("city");

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
                finish();
            } else {
                progressDialog.dismiss();
                loginLayout.setVisibility(View.VISIBLE);
                Log.d("Token", "Not found");
                Toast.makeText(LoginActivity.this, "Invalid Phone or password", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            progressDialog.dismiss();
            loginLayout.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    private void requestUserDetails() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", sharedPreferences.getString("username", null));
        params.put("token", sharedPreferences.getString("token", null));
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/account/details", params);
    }

    private void CreateSharedPref(String name, String val) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, val);
        editor.apply();
    }
}
