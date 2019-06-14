package com.example.finalui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.Models.Tasks;
import com.example.finalui.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<Tasks> tripData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onUpdateClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MyAdapter(Context context, List<Tasks> tripData) {
        this.context = context;
        this.tripData = tripData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tasklist_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tasks taskobj = tripData.get(position);

        holder.slipno.setText(taskobj.getSlip_no());
        holder.date.setText(taskobj.getTaskdate());
        holder.customername.setText(taskobj.getCustomer_name());
        holder.requirements.setText(taskobj.getRequirement());
        holder.peoples.setText(taskobj.getPeople());
        holder.tasks.setText(taskobj.getTask());
        holder.time_assigned.setText(taskobj.getTime_assigned());
        holder.timeofwork.setText(taskobj.getTime_work());
        holder.duration.setText(taskobj.getTask_duration());
        holder.status.setText(taskobj.getStatus());
    }

    @Override
    public int getItemCount() {
        return tripData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView slipno, status, date, customername, requirements, peoples, tasks, time_assigned, timeofwork, duration;
        Button buttstatus,buttupdate;


        public MyViewHolder(@NonNull View view) {
            super(view);
            status = view.findViewById(R.id.textView8);
            slipno = view.findViewById(R.id.textView);
            date = view.findViewById(R.id.textView2);
            customername = view.findViewById(R.id.textView3);
            requirements = view.findViewById(R.id.textView6);
            peoples = view.findViewById(R.id.textView7);
            tasks = view.findViewById(R.id.textView9);
            time_assigned = view.findViewById(R.id.textView11);
            timeofwork = view.findViewById(R.id.textView13);
            duration = view.findViewById(R.id.textView15);
            buttupdate=view.findViewById(R.id.button2);


            //Button date Implementations
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int pos  = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            mListener.onUpdateClick(pos);
                        }
                    }
                }
            });

            buttupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
