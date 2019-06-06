package com.example.finalui.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.finalui.Adapters.ViewPagerAdapter;
import com.example.finalui.R;
import com.example.finalui.fragments.itemcommfragment;
import com.example.finalui.fragments.itempurcfragment;
import com.example.finalui.fragments.itemupdatefragment;
import com.google.android.material.tabs.TabLayout;

public class DetailSctivity extends AppCompatActivity {
Toolbar toolbar;
ViewPager viewPager;
TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sctivity);
        getSupportActionBar().setTitle("Tasks Details");
//    toolbar = findViewById(R.id.toolbary);
//    setSupportActionBar(toolbar);

    viewPager  = findViewById(R.id.viewpager);
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(new itemcommfragment(),"COMMENT");
        adapter.addFragment(new itemupdatefragment(),"UPDATE");
        adapter.addFragment(new itempurcfragment(),"PURCHASE");
        viewPager.setAdapter(adapter);
        tabLayout = findViewById(R.id.tabviewlay);
        tabLayout.setupWithViewPager(viewPager);



    }
}
