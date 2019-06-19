package com.deskneed.team.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deskneed.team.Models.UpdateModel;
import com.deskneed.team.R;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    private Context context;
    private List<UpdateModel> updateModelList;

    public MyAdapter2(Context context, List<UpdateModel> updateModelList) {
        this.context = context;
        this.updateModelList = updateModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.updatellist_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UpdateModel taskobj = updateModelList.get(position);

        holder.id.setText(taskobj.getId());
        holder.slipno.setText(taskobj.getSlipno());
        holder.updateDate.setText(taskobj.getUpdateDate());
        holder.status.setText(taskobj.getStatus());
        holder.nextUpdateDate.setText(taskobj.getNextUpdateDate());
        holder.dept.setText(taskobj.getDept());
        holder.emp.setText(taskobj.getEmployee());
        holder.updatedBy.setText(taskobj.getUpdatedBy());
    }

    @Override
    public int getItemCount() {
        return updateModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, slipno, updateDate, status, nextUpdateDate, dept, emp, updatedBy;


        public MyViewHolder(@NonNull View view) {
            super(view);
            id = view.findViewById(R.id.textView);
            slipno = view.findViewById(R.id.textView2);
            updateDate = view.findViewById(R.id.textView21);
            status = view.findViewById(R.id.textView20);
            nextUpdateDate = view.findViewById(R.id.textView18);
            dept = view.findViewById(R.id.textView19);
            emp = view.findViewById(R.id.textView22);
            updatedBy = view.findViewById(R.id.textView23);
        }
    }
}
