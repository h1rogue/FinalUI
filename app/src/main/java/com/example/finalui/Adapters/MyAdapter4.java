package com.example.finalui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.Models.CommentModel;
import com.example.finalui.Models.CommentModel;
import com.example.finalui.R;

import java.util.List;

public class MyAdapter4 extends RecyclerView.Adapter<MyAdapter4.MyViewHolder> {

    private Context context;
    private List<CommentModel> commentModelList;

    public MyAdapter4(Context context, List<CommentModel> commentModelList) {
        this.context = context;
        this.commentModelList = commentModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comment_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CommentModel taskobj = commentModelList.get(position);

        holder.time.setText(taskobj.getTime());
        holder.name.setText(taskobj.getName());
        holder.msg.setText(taskobj.getMessage());
    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time, name, msg;


        public MyViewHolder(@NonNull View view) {
            super(view);

            time = view.findViewById(R.id.textView);
            name = view.findViewById(R.id.textView3);
            msg = view.findViewById(R.id.textView4);

        }
    }
}
