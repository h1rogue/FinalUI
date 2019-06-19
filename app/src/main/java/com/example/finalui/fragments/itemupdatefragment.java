package com.example.finalui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.Activities.AddUpdateActivity;
import com.example.finalui.Adapters.MyAdapter2;
import com.example.finalui.Models.UpdateModel;
import com.example.finalui.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
//vdhp/order/slip/get

public class itemupdatefragment extends Fragment {
    View view;
    public itemupdatefragment(){
    }
    FloatingActionButton button;
    RecyclerView recyclerView;
    MyAdapter2 myAdapter2;
    List<UpdateModel> objectlist;
    String slipno,date,status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.item_updates,container,false);
        button = view.findViewById(R.id.floatbutton);
        Intent intent = getActivity().getIntent();
        slipno = intent.getStringExtra("slipno");
        date = intent.getStringExtra("date");
        status = intent.getStringExtra("status");
        Log.d("slippp", slipno+date+"");

        Bundle args = intent.getBundleExtra("BUNDLE");
        objectlist  = new ArrayList<>();
        ArrayList<UpdateModel> objectlistfromintent = (ArrayList<UpdateModel>) args.getSerializable("ARRAYLIST");
        objectlist=objectlistfromintent;
        recyclerView=view.findViewById(R.id.recyclerview);
        myAdapter2=new MyAdapter2(getContext(),  objectlist);
        recyclerView.setAdapter(myAdapter2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), AddUpdateActivity.class);
                intent1.putExtra("slip_no", slipno);
                intent1.putExtra("date", date);
                intent1.putExtra("status", status);
                startActivityForResult(intent1,123);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==123 && resultCode==RESULT_OK && data!=null){
          UpdateModel object = (UpdateModel) data.getSerializableExtra("Object");
          objectlist.add(object);
          myAdapter2.notifyDataSetChanged();
        }
    }
}
