package com.example.finalui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SalaryDetailsActivity extends AppCompatActivity {
    ExpandabelListAdapter listAdapter;
    ExpandableListView expListView;
    List<HeaderModel> listDataHeader;
    HashMap<HeaderModel, List<SalaryModel>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_details);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandabels);
        // preparing list data
        prepareListData();
        listAdapter = new ExpandabelListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<HeaderModel>();
        listDataChild = new HashMap<HeaderModel, List<SalaryModel>>();

        // Adding child data
        HeaderModel january = new HeaderModel(500000,"January");
        HeaderModel february = new HeaderModel(630000,"February");
        HeaderModel march = new HeaderModel(350000,"March");

        listDataHeader.add(january);
        listDataHeader.add(february);
        listDataHeader.add(march);


        // Adding child data
        List<SalaryModel> jan = new ArrayList<>();
        List<SalaryModel> feb = new ArrayList<>();
        List<SalaryModel> mar = new ArrayList<>();

        SalaryModel sal1 = new SalaryModel("2/01/2019","PAID",120000);
        SalaryModel sal2 = new SalaryModel("3/01/2019","DUE",110000);
        SalaryModel sal3 = new SalaryModel("12/01/2019","PAID",130000);
        SalaryModel sal4 = new SalaryModel("23/01/2019","PAID",140000);

        SalaryModel sal5 = new SalaryModel("2/02/2019","PAID",110000);
        SalaryModel sal6 = new SalaryModel("5/02/2019","PAID",150000);
        SalaryModel sal7 = new SalaryModel("7/02/2019","PAID",120000);
        SalaryModel sal8 = new SalaryModel("12/02/2019","PAID",110000);
        SalaryModel sal9 = new SalaryModel("24/02/2019","PAID",140000);

        SalaryModel sal10 = new SalaryModel("2/03/2019","PAID",120000);
        SalaryModel sal11 = new SalaryModel("12/03/2019","PAID",110000);
        SalaryModel sal12 = new SalaryModel("24/03/2019","PAID",120000);

        //Add to Lists

        jan.add(sal1);
        jan.add(sal2);
        jan.add(sal3);
        jan.add(sal4);

        feb.add(sal5);
        feb.add(sal6);
        feb.add(sal7);
        feb.add(sal8);
        feb.add(sal9);
        mar.add(sal10);
        mar.add(sal11);
        mar.add(sal12);

        listDataChild.put(listDataHeader.get(0), jan);
        listDataChild.put(listDataHeader.get(1), feb);
        listDataChild.put(listDataHeader.get(2), mar);// Header, Child data
    }

}
