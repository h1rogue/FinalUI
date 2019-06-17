package com.example.finalui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.Adapters.MyAdapter4;
import com.example.finalui.ApplicationVariable;
import com.example.finalui.Models.CommentModel;
import com.example.finalui.R;
import com.example.finalui.Adapters.MyAdapter4;
import com.example.finalui.Models.CommentModel;
import com.example.finalui.R;
import com.example.finalui.TasksModel;
import com.example.finalui.VvVolleyClass;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class itemcommfragment extends Fragment {

    View view;
    RecyclerView recyclerView2;
    MyAdapter4 myAdapter4;
    EditText comment;
    Button button;
    List<CommentModel> commentModelList;
    String slip_no;

    public itemcommfragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_comment,container,false);
        Intent intent = getActivity().getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE2");
        commentModelList=new ArrayList<>();


        Intent intent2 = getActivity().getIntent();
        TasksModel tasksModel = (TasksModel) intent2.getSerializableExtra("INFO");
        slip_no=  tasksModel.getSlip_no();

        ArrayList<CommentModel> object = (ArrayList<CommentModel>) args.getSerializable("ARRAYLIST2");
        commentModelList=object;
        recyclerView2=view.findViewById(R.id.recyclerview2);
        comment=view.findViewById(R.id.editText3);
        button=view.findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

        myAdapter4=new MyAdapter4(getContext(), commentModelList);
        recyclerView2.setAdapter(myAdapter4);
        return view;
    }

    private void check() {
        if(TextUtils.isEmpty(comment.getText().toString().trim()))
        {
           comment.setError("Please give some comment");
        }
        else
        {
            String commentxt = comment.getText().toString().trim();
            sendtheComment();
            comment.setText("");
        }
    }

    private void sendtheComment() {
        getCommentsfromJson();
    }

    private void getCommentsfromJson() {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(getActivity(),getActivity().getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("slip_no",slip_no);
        params.put("filter", jsonObject);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/comment/get", params);
    }
}
