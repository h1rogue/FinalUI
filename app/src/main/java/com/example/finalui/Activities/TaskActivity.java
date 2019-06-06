package com.example.finalui.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.Adapters.MyAdapter;
import com.example.finalui.Models.CommentModel;
import com.example.finalui.Models.PuchaseModel;
import com.example.finalui.Models.Tasks;
import com.example.finalui.Models.UpdateModel;
import com.example.finalui.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    RecyclerView recyclerView;
    MyAdapter mAdapter;
    List<Tasks> tasks;
    Tasks taskobj, taskobj2, taskobj3, taskobj4;
    UpdateModel model1,model2,model3,model4;
    List<UpdateModel> updateModelList;

    PuchaseModel pmodel1,pmodel2,pmodel3,pmodel4;
    List<PuchaseModel> puchaseModelList;

    CommentModel cmodel1,cmodel2,cmodel3,cmodel4,cmoddel5;
    List<CommentModel> commentModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getSupportActionBar().setTitle("Tasks And Assignment");
        recyclerView = findViewById(R.id.recy);
        tasks = new ArrayList<>();
        updateModelList=new ArrayList<>();
        puchaseModelList=new ArrayList<>();
        commentModelList=new ArrayList<>();


        model1=new UpdateModel("123", "#122", "12/2/12", "IN DUE", "22/22/19", "cse",
                                "Hir", "dd");

        model2=new UpdateModel("124", "#122", "15/2/12", "INVOICE", "31/22/19", "ece",
                "Hira", "ff");

        model3=new UpdateModel("125", "#122", "14/2/12", "DONE", "31/12/19", "me",
                "Gagan", "Hiraa");

        model4=new UpdateModel("126", "#122", "11/2/12", "OK", "21/22/19", "eie",
                "Hirak", "oo");

        updateModelList.add(model1);
        updateModelList.add(model2);
        updateModelList.add(model3);
        updateModelList.add(model4);

        pmodel1=new PuchaseModel("123", "11/11/11", "we", "23/11",
                "2300", "OK", "done");
        pmodel2=new PuchaseModel("111", "21/11/11", "were", "23/122",
                "3300", "NOT OK", "NOT done");
        pmodel3=new PuchaseModel("111", "21/11/11", "were", "23/122",
                "3300", "NOT OK", "NOT done");
        pmodel4=new PuchaseModel("111", "21/11/11", "were", "23/122",
                "3300", "NOT OK", "NOT done");

        puchaseModelList.add(pmodel1);
        puchaseModelList.add(pmodel2);
        puchaseModelList.add(pmodel3);
        puchaseModelList.add(pmodel4);


        cmodel1=new CommentModel("Hirak", "I am humungasour", "04:09pm");
        cmodel2=new CommentModel("Gagan", "I am heatblast.", "09:00am");
        cmodel3=new CommentModel("Vaibhav", "I am DiamondHead", "23:99PM");
        cmodel4=new CommentModel("Anushree", "I am Accelarator", "23:99PM");
        cmoddel5=new CommentModel("Vaibhav", "I am Ghostfreak", "23:99PM");

        commentModelList.add(cmodel1);
        commentModelList.add(cmodel2);
        commentModelList.add(cmodel3);
        commentModelList.add(cmodel4);
        commentModelList.add(cmoddel5);

        taskobj = new Tasks("12/03/19", "#2234", "Hirak Borah",
                "Furniture Repairement",
                "Gagan, Vaibhav, Hirak",
                "Furniture Repairement", "14/03/19", "15/03/19", "3 days"
                , "FEEDBACK", updateModelList, puchaseModelList, commentModelList);

        taskobj2 = new Tasks("23/03/19", "#2235", "Gagan Baishya",
                "Electrical Board",
                "Gagan, Vaibhav, Hirak",
                "Electrician", "24/03/19", "25/03/19", "2 days"
                , "WORK DUE",new ArrayList<UpdateModel>(), new ArrayList<PuchaseModel>(), new ArrayList<CommentModel>());

        taskobj3 = new Tasks("1/04/19", "#2236", "Padmanabha Bhattacharya",
                "Carpentry for lock repairing",
                "Gagan, Himangshu, Hirak",
                "Fire Management", "2/04/19", "3/04/19", "1 days"
                , "PAYMENT",new ArrayList<UpdateModel>(), new ArrayList<PuchaseModel>(), new ArrayList<CommentModel>());

        taskobj4 = new Tasks("12/04/19", "#2237", "Anushree Goswami",
                "Blind for 3 window",
                "Hirak",
                "Fire Management", "2/04/19", "3/04/19", "1 days"
                , "PAYMENT",new ArrayList<UpdateModel>(), new ArrayList<PuchaseModel>(), new ArrayList<CommentModel>());

        tasks.add(taskobj);
        tasks.add(taskobj2);
        tasks.add(taskobj3);
        tasks.add(taskobj4);

        mAdapter = new MyAdapter(getApplicationContext(), tasks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //for the divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(TaskActivity.this);
    }

    @Override
    public void onUpdateClick(int position) {
        Tasks tasks1 = tasks.get(position);
        Intent intent = new Intent(this, DetailSctivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("ARRAYLIST",(Serializable)tasks1.getUpdateModelList());
        intent.putExtra("BUNDLE",bundle);

        Bundle bundle1=new Bundle();
        bundle1.putSerializable("ARRAYLIST1",(Serializable)tasks1.getPuchaseModelList());
        intent.putExtra("BUNDLE1",bundle1);

        Bundle bundle2=new Bundle();
        bundle2.putSerializable("ARRAYLIST2",(Serializable)tasks1.getCommentModelList());
        intent.putExtra("BUNDLE2",bundle2);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {

    }
}