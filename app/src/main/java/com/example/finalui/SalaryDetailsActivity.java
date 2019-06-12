package com.example.finalui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SalaryDetailsActivity extends AppCompatActivity implements VvVolleyInterface {
    ExpandabelListAdapter listAdapter;
    ExpandableListView expListView;
    List<HeaderModel> listDataHeader;
    HashMap<HeaderModel, List<SalaryModel>> listDataChild;

    HeaderModel headerModel;
    SalaryModel salaryModel;
    HashMap<String,SalaryReport> map;
    List<SalaryModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_details);

        //object for the salary details
        map=new HashMap<>();
        list=new ArrayList<>();

        getJson();//get the json data from api


        // get the listview
//        expListView =  findViewById(R.id.expandabels);
//        // preparing list data
//        //prepareListData();
//        listAdapter = new ExpandabelListAdapter(this, listDataHeader, listDataChild);
//
//        // setting list adapter
//        expListView.setAdapter(listAdapter);

    }

    private void getJson() {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        params.put("sort","month");
       // params.put("sort_order", "desc");
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/salary/get", params);
    }

//    private void prepareListData() {
//        listDataHeader = new ArrayList<HeaderModel>();
//        listDataChild = new HashMap<HeaderModel, List<SalaryModel>>();
//        Log.d("pp", ",");
//
//
//        for(Map.Entry map1 :map.entrySet()){
//            Log.d("pp", "kk");
//            Log.d("pp", map1.getKey()+ ","+map1.getValue());
//        }
//        // Adding child data
//        HeaderModel january = new HeaderModel(500000,"January",450000);
//        HeaderModel february = new HeaderModel(630000,"February",600000);
//        HeaderModel march = new HeaderModel(350000,"March",300000);
//
//        listDataHeader.add(january);
//        listDataHeader.add(february);
//        listDataHeader.add(march);
//
//
//        // Adding child data
//        List<SalaryModel> jan = new ArrayList<>();
//        List<SalaryModel> feb = new ArrayList<>();
//        List<SalaryModel> mar = new ArrayList<>();
//
//        SalaryModel sal1 = new SalaryModel("2/01/2019","PAID",120000);
//        SalaryModel sal2 = new SalaryModel("3/01/2019","DUE",110000);
//        SalaryModel sal3 = new SalaryModel("12/01/2019","PAID",130000);
//        SalaryModel sal4 = new SalaryModel("23/01/2019","PAID",140000);
//
//        SalaryModel sal5 = new SalaryModel("2/02/2019","PAID",110000);
//        SalaryModel sal6 = new SalaryModel("5/02/2019","PAID",150000);
//        SalaryModel sal7 = new SalaryModel("7/02/2019","PAID",120000);
//        SalaryModel sal8 = new SalaryModel("12/02/2019","PAID",110000);
//        SalaryModel sal9 = new SalaryModel("24/02/2019","PAID",140000);
//
//        SalaryModel sal10 = new SalaryModel("2/03/2019","PAID",120000);
//        SalaryModel sal11 = new SalaryModel("12/03/2019","PAID",110000);
//        SalaryModel sal12 = new SalaryModel("24/03/2019","PAID",120000);
//
//        //Add to Lists
//
//        jan.add(sal1);
//        jan.add(sal2);
//        jan.add(sal3);
//        jan.add(sal4);
//
//        feb.add(sal5);
//        feb.add(sal6);
//        feb.add(sal7);
//        feb.add(sal8);
//        feb.add(sal9);
//        mar.add(sal10);
//        mar.add(sal11);
//        mar.add(sal12);
//
//        listDataChild.put(listDataHeader.get(0), jan);
//        listDataChild.put(listDataHeader.get(1), feb);
//        listDataChild.put(listDataHeader.get(2), mar);// Header, Child data
//    }

    @Override
    public void onTaskComplete(String result) {
        Log.d("Aa", result);
        try {
            JSONObject o = new JSONObject(result);
            JSONArray a = o.getJSONArray("data_rows");
            int arrSize = a.length();
            for (int i = 0; i < arrSize; ++i) {

                String k = a.getJSONObject(i).getString("month");
                k+='_';
                k+=a.getJSONObject(i).getString("type");//this k is the key
                SalaryReport salaryReport;
                float ss;

                salaryModel = new SalaryModel(a.getJSONObject(i).getString("id"),
                        a.getJSONObject(i).getString("date_credited"),a.getJSONObject(i).getString("amount"),
                        a.getJSONObject(i).getString("month"), a.getJSONObject(i).getString("type"));
                list.add(salaryModel);

                if(map.containsKey(k) && map.get(k) != null){
                    ss=Float.parseFloat(a.getJSONObject(i).getString("amount"));
                    salaryReport =new SalaryReport(a.getJSONObject(i).getString("month"),
                    a.getJSONObject(i).getString("type"), ss+=map.get(k).amount, list);
                }
                else{
                    ss=Float.parseFloat(a.getJSONObject(i).getString("amount"));
                    Log.d("pp", String.valueOf(ss));
                    salaryReport=new SalaryReport(a.getJSONObject(i).getString("month"),
                        a.getJSONObject(i).getString("type"), ss, list);
                }
                map.put(k,salaryReport);
                Log.d("pp", String.valueOf(map.size()));
            }
            listDataHeader = new ArrayList<HeaderModel>();
            listDataChild = new HashMap<HeaderModel, List<SalaryModel>>();
            Log.d("pp", ",");

            // Adding child data

            int j=0;
            for(HashMap.Entry<String,SalaryReport> map1 :map.entrySet()){//loop for the headers present
                String k = map1.getValue().month;
                k+='_';
                k+=map1.getValue().type;//k is key

                HeaderModel january = new HeaderModel(500000,k, (int) map1.getValue().amount);
                Log.d("pp", map1.getKey()+ ","+map1.getValue().amount);
                listDataHeader.add(january);

                List<SalaryModel> jan = new ArrayList<>();
                int i=0;

                for(SalaryModel salary:list){//loop for the array elements of each array "data_rows"
                    Log.i("ooo1", salary.id);
                    String k1 = list.get(i).getMonth();
                    k1+='_';
                    k1+=list.get(i).getType();//k is key
                    Log.d("ooo", k1+","+k);
                    if(k1.equals(k)){

                        jan.add(list.get(i));
                        Log.d("ooo", "in"+i+","+list.get(i).id);

                    }
                    i++;
                }
                for(SalaryModel salary:jan){
                    Log.d("ooo","list"+ salary.id);
                }
                listDataChild.put(listDataHeader.get(j), jan);
                j++;
//                jan.add(list.get(3));
//                listDataChild.put(listDataHeader.get(listDataHeader.size()-1), jan);
            }

//            HeaderModel january = new HeaderModel(500000,"January",450000);
//            HeaderModel february = new HeaderModel(630000,"February",600000);
//            HeaderModel march = new HeaderModel(350000,"March",300000);
//
//            listDataHeader.add(january);
//            listDataHeader.add(february);
//            listDataHeader.add(march);


            // Adding child data
//            List<SalaryModel> jan = new ArrayList<>();
//            List<SalaryModel> feb = new ArrayList<>();
//            List<SalaryModel> mar = new ArrayList<>();
//
//            SalaryModel sal1 = new SalaryModel(salaryModel.dateCredited,salaryModel.remarks,salaryModel.amount);
//            SalaryModel sal2 = new SalaryModel("3/01/2019","DUE",110000);
//            SalaryModel sal3 = new SalaryModel("12/01/2019","PAID",130000);
//            SalaryModel sal4 = new SalaryModel("23/01/2019","PAID",140000);
//
//            SalaryModel sal5 = new SalaryModel("2/02/2019","PAID",110000);
//            SalaryModel sal6 = new SalaryModel("5/02/2019","PAID",150000);
//            SalaryModel sal7 = new SalaryModel("7/02/2019","PAID",120000);
//            SalaryModel sal8 = new SalaryModel("12/02/2019","PAID",110000);
//            SalaryModel sal9 = new SalaryModel("24/02/2019","PAID",140000);
//
//            SalaryModel sal10 = new SalaryModel("2/03/2019","PAID",120000);
//            SalaryModel sal11 = new SalaryModel("12/03/2019","PAID",110000);
//            SalaryModel sal12 = new SalaryModel("24/03/2019","PAID",120000);
//
//            //Add to Lists
//
//            jan.add(sal1);
//            jan.add(sal2);
//            jan.add(sal3);
//            jan.add(sal4);
//
//            feb.add(sal5);
//            feb.add(sal6);
//            feb.add(sal7);
//            feb.add(sal8);
//            feb.add(sal9);
//            mar.add(sal10);
//            mar.add(sal11);
//            mar.add(sal12);
//
//            listDataChild.put(listDataHeader.get(0), jan);
//            listDataChild.put(listDataHeader.get(1), feb);
//            listDataChild.put(listDataHeader.get(2), mar);// Header, Child data

            expListView =  findViewById(R.id.expandabels);
            // preparing list data
            //prepareListData();
            listAdapter = new ExpandabelListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            expListView.setAdapter(listAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
