package com.example.finalui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandabelListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<HeaderModel> _listDataHeader; // header titles
    private HashMap<HeaderModel, List<SalaryModel>> _listDataChild;


    public ExpandabelListAdapter(Context _context, List<HeaderModel> _listDataHeader, HashMap<HeaderModel, List<SalaryModel>> _listDataChild) {
        this._context = _context;
        this._listDataHeader = _listDataHeader;
        this._listDataChild = _listDataChild;
    }


    @Override
    public int getGroupCount() {
        return _listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _listDataChild.get(_listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _listDataChild.get(_listDataHeader.get(groupPosition))
                .get(childPosition);
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
        HeaderModel headerTitle = (HeaderModel) getGroup(groupPosition);
        String month  = headerTitle.getMonthName();
        int totalamt = headerTitle.getTotalamt();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = convertView
                .findViewById(R.id.lblListHeader);
        TextView totalmonthsum = convertView
                .findViewById(R.id.totalamtmonth);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(month);
        totalmonthsum.setText(""+totalamt);

        return convertView;
    }
//Required for child
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

      SalaryModel  salaryModel = (SalaryModel) getChild(groupPosition, childPosition);
          String childmonth = salaryModel.getMonth();
          int amount = salaryModel.getAmount();
          String remarks = salaryModel.getRemarks();

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

        month.setText(childmonth);
        salamount.setText(""+amount);
        remark.setText(remarks);

        return convertView;
    }
    //Not required
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
