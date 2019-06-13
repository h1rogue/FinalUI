package com.example.finalui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandabelListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private HashMap<String, SalaryReport> salaryReports;
    private ArrayList<String> keys = new ArrayList<>();

    public ExpandabelListAdapter(Context _context, HashMap<String, SalaryReport> salaryReport, ArrayList<String> keys) {
        this._context = _context;
        this.salaryReports = salaryReport;
        this.keys = keys;
    }


    @Override
    public int getGroupCount() {
        return salaryReports.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return salaryReports.get(keys.get(groupPosition)).records.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return salaryReports.get(keys.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return salaryReports.get(keys.get(groupPosition)).records.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
//Required for group list(We have Only One element i.e our child View)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        SalaryReport salaryReport = salaryReports.get(keys.get(groupPosition));
        String month  = salaryReport.month;
        String totalamt = ApplicationVariable.ACCOUNT_DATA.salary;
        int creamt = (int) salaryReport.amount;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView type = convertView.findViewById(R.id.type1);
        TextView lblListHeader = convertView
                .findViewById(R.id.lblListHeader);
        TextView totalmonthsum = convertView
                .findViewById(R.id.totalamtmonth);
        TextView creditamt = convertView
                .findViewById(R.id.creditedamt);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(modifyMonth(month));
        type.setText(salaryReport.type);
        totalmonthsum.setText(""+totalamt);
        creditamt.setText(""+creamt);

        return convertView;
    }
//Required for child
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

      SalaryModel  salaryModel = (SalaryModel) getChild(groupPosition, childPosition);
          String childmonth = salaryModel.getMonth();
          String amount = salaryModel.getAmount();
          String remarks = salaryModel.getDateCredited();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_items, null);
        }

        TextView month = convertView
                .findViewById(R.id.lblListItem);

        TextView salamount = convertView
                .findViewById(R.id.salaryamt);

        TextView remark = convertView
                .findViewById(R.id.salaryremarks);

        month.setText(modifyMonth(salaryModel.month));
        salamount.setText(""+amount);
        remark.setText(remarks);

        return convertView;
    }
    //Not required
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public String modifyMonth(String k) {
        //for displaying as month
        String s = String.valueOf(k.charAt(5)) + k.charAt(6);
        String mahina = null;

        if (s.equals("01"))
            mahina = "JANUARY";
        if (s.equals("02"))
            mahina = "FEBRUARY";
        if (s.equals("03"))
            mahina = "MARCH";
        if (s.equals("04"))
            mahina = "APRIL";
        if (s.equals("05"))
            mahina = "MAY";
        if (s.equals("06"))
            mahina = "JUNE";
        if (s.equals("07"))
            mahina = "JULY";
        if (s.equals("08"))
            mahina = "AUGUST";
        if (s.equals("09"))
            mahina = "SEPTEMBER";
        if (s.equals("10"))
            mahina = "OCTOBER";
        if (s.equals("11"))
            mahina = "NOVEMBER";
        if (s.equals("12"))
            mahina = "DECEMBER";

        mahina += "'" + k.charAt(0) + k.charAt(1) + k.charAt(2) + k.charAt(3);
        return  mahina;
    }
}
