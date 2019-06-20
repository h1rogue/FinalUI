package com.deskneed.team.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.deskneed.team.Adapters.MyAdapter4;
import com.deskneed.team.ApplicationVariable;
import com.deskneed.team.Models.CommentModel;
import com.deskneed.team.TasksModel;
import com.deskneed.team.VvVolleyClass;
import com.deskneed.team.VvVolleyInterface;
import com.deskneed.team.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class itemcommfragment extends Fragment implements VvVolleyInterface {

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
        Intent intent2 = getActivity().getIntent();
        TasksModel tasksModel = (TasksModel) intent2.getSerializableExtra("INFO");
        slip_no=  tasksModel.getSlip_no();
        getCommentRefreshed();

        recyclerView2=view.findViewById(R.id.recyclerview2);
        comment=view.findViewById(R.id.editText3);
        button=view.findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
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
            sendtheComment(commentxt);

        }
    }
    private void sendtheComment(String commentxt) {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(getContext(),this);
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("slip_no",slip_no);
        jsonObject.addProperty("comment",commentxt);
        params.put("new_data_row", jsonObject.toString());
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/comment/create", params);
    }

    @Override
    public void onTaskComplete(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            Log.d("DSK_OPER", "onTaskComplete:"+result);
            if(jsonObject.getString("responseFor").equals( "order/slip/comment/create")){
                comment.setText("");
                getCommentRefreshed();
            }

            if(jsonObject.getString("responseFor").equals( "order/slip/comment/get")){

               if(jsonObject.getInt("data_rows_size")>0){
                   JSONArray jarray = jsonObject.getJSONArray("data_rows");
                   for(int i=0;i<jarray.length();i++){
                       CommentModel commentModel = new CommentModel(jarray.getJSONObject(i).getString("staff_name"),
                               jarray.getJSONObject(i).getString("comment"),
                               jarray.getJSONObject(i).getString("commented_on"),
                               jarray.getJSONObject(i).getString("slip_no"),
                               jarray.getJSONObject(i).getString("id"),
                               jarray.getJSONObject(i).getInt("staff"));
                       commentModelList.add(commentModel);
                       Log.d("TAGDER", "onTaskComplete: "+commentModelList.get(i).getcomment());
                   }
                   myAdapter4=new MyAdapter4(getContext(), commentModelList);
                   recyclerView2.setAdapter(myAdapter4);
               }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getCommentRefreshed() {
        commentModelList=new ArrayList<>();

        VvVolleyClass vvVolleyClass = new VvVolleyClass(getContext(),this);
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("slip_no",slip_no);
        params.put("filter", jsonObject.toString());
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/comment/get", params);
    }
}
