package com.example.finalui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.Adapters.MyAdapter4;
import com.example.finalui.Models.CommentModel;
import com.example.finalui.R;
import com.example.finalui.Adapters.MyAdapter4;
import com.example.finalui.Models.CommentModel;
import com.example.finalui.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class itemcommfragment extends Fragment {

    View view;
    RecyclerView recyclerView2;
    MyAdapter4 myAdapter4;
    EditText comment;
    Button button;
    List<CommentModel> commentModelList;

    public itemcommfragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_comment,container,false);
        Intent intent = getActivity().getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE2");
        commentModelList=new ArrayList<>();

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
            String str=comment.getText().toString().trim();
            String nam="Gagan";
            String time = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            comment.setText("");
            CommentModel commentModel= new CommentModel(nam, str,time);
            commentModelList.add(commentModel);
            myAdapter4.notifyDataSetChanged();

        }
    }
}
