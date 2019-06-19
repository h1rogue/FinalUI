package com.example.finalui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.finalui.Adapters.ViewPagerAdapter;
import com.example.finalui.Models.UpdateModel;
import com.example.finalui.R;
import com.example.finalui.TasksModel;
import com.example.finalui.fragments.itemcommfragment;
import com.example.finalui.fragments.itempurcfragment;
import com.example.finalui.fragments.itemupdatefragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DetailSctivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView slip_no;
    TextView  customer_name;
    TextView  requirement;
    TextView  vendor;
    TextView  dept;
    TextView  nextUpdateDate;
    TextView  employee;
    TextView  slipDate;
    public static String sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sctivity);
        getSupportActionBar().setTitle("Tasks Details");

        slip_no = findViewById(R.id.textView17);
        customer_name = findViewById(R.id.textView25);
        requirement = findViewById(R.id.textView32);
        vendor = findViewById(R.id.textView34);
        dept = findViewById(R.id.textView35);
        nextUpdateDate = findViewById(R.id.textView33);
        employee = findViewById(R.id.textView36);
        slipDate = findViewById(R.id.textView26);

        Intent intent = getIntent();
        intent.getSerializableExtra("INFO");//to get object from intent of other activity
        TasksModel tasksModel = (TasksModel) getIntent().getSerializableExtra("INFO");

        //set the text to those values from object

        slip_no.setText(tasksModel.getSlip_no());
        customer_name.setText(tasksModel.getCustomer_name());
        requirement.setText(tasksModel.getRequirement());
        vendor.setText(tasksModel.getVendor());
        dept.setText(tasksModel.getDept());
        nextUpdateDate.setText(tasksModel.getNextUpdateDate());
        employee.setText(tasksModel.getEmployee());
        slipDate.setText(tasksModel.getSlipDate());

        viewPager  = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new itemcommfragment(),"COMMENT");
        adapter.addFragment(new itemupdatefragment(),"UPDATE");
        adapter.addFragment(new itempurcfragment(),"PURCHASE");
        viewPager.setAdapter(adapter);
        tabLayout = findViewById(R.id.tabviewlay);
        tabLayout.setupWithViewPager(viewPager);
        Intent intent1 = getIntent();
        int val = (int) intent1.getIntExtra("pos",0);//if update button of prev activity is clicked (i.e onItemClick is executed the pos is saved as 1 so direct update fragment is intented)
        Log.d("stttt", intent1.getStringExtra("status1")+"");
        viewPager.setCurrentItem(val);

    }
}
