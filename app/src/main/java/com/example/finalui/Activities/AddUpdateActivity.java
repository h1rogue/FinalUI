package com.example.finalui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalui.Models.UpdateModel;
import com.example.finalui.R;
import com.example.finalui.fragments.itemupdatefragment;

public class AddUpdateActivity extends AppCompatActivity {

    private EditText id,slipno,updatedate,status,nextupdatedate,department,employee,updateby;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);
        id= findViewById(R.id.editText2);
        slipno=findViewById(R.id.editText4);
        updatedate=findViewById(R.id.editText5);
        status=findViewById(R.id.editText6);
        nextupdatedate=findViewById(R.id.editText7);
        department=findViewById(R.id.editText8);
        employee=findViewById(R.id.editText9);
        updateby=findViewById(R.id.editText10);
        button=findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmpty();

            }
        });
    }

    private void checkEmpty() {
        if(TextUtils.isEmpty(id.getText().toString().trim())){
            id.setError("field cannot be empty");
        }
       else if(TextUtils.isEmpty(slipno.getText().toString().trim())){
            slipno.setError("field cannot be empty");
        }
       else if(TextUtils.isEmpty(updatedate.getText().toString().trim())){
            updatedate.setError("field cannot be empty");
        }
       else if(TextUtils.isEmpty(status.getText().toString().trim())){
            status.setError("field cannot be empty");
        }
        else if(TextUtils.isEmpty(nextupdatedate.getText().toString().trim())){
            nextupdatedate.setError("field cannot be empty");
        }
       else if(TextUtils.isEmpty(department.getText().toString().trim())){
            department.setError("field cannot be empty");
        }
       else if(TextUtils.isEmpty(employee.getText().toString().trim())){
            employee.setError("field cannot be empty");
        }
       else if(TextUtils.isEmpty(updateby.getText().toString().trim())){
            updateby.setError("field cannot be empty");
        }
       else{
            Intent intent = new Intent(AddUpdateActivity.this, itemupdatefragment.class);
            UpdateModel updateModel = new UpdateModel(id.getText().toString().trim(),
                    slipno.getText().toString().trim(),updatedate.getText().toString().trim(),
                    status.getText().toString().trim(),nextupdatedate.getText().toString().trim(),
                    department.getText().toString().trim(),employee.getText().toString().trim(),
                    updateby.getText().toString().trim());
            intent.putExtra("Object",updateModel);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
