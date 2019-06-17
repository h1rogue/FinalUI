package com.example.finalui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.Adapters.MyAdapter3;
import com.example.finalui.Models.PuchaseModel;
import com.example.finalui.R;

import java.util.ArrayList;

public class itempurcfragment extends Fragment {
    public itempurcfragment(){}
    View view;
    RecyclerView recyclerView1;
    MyAdapter3 myAdapter3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_purchase,container,false);
        Intent intent = getActivity().getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE1");
        ArrayList<PuchaseModel> object = (ArrayList<PuchaseModel>) args.getSerializable("ARRAYLIST1");

        recyclerView1=view.findViewById(R.id.recyclerview1);
        myAdapter3=new MyAdapter3(getContext(), object);
        recyclerView1.setAdapter(myAdapter3);

        return view;
    }
}