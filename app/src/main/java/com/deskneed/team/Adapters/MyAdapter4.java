package com.deskneed.team.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deskneed.team.ApplicationVariable;
import com.deskneed.team.Models.CommentModel;
import com.deskneed.team.R;

import java.util.List;

public class MyAdapter4 extends RecyclerView.Adapter{

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context context;
    private List<CommentModel> commentModelList;

    public MyAdapter4(Context context, List<CommentModel> commentModelList) {
        this.context = context;
        this.commentModelList = commentModelList;
    }


    @Override
    public int getItemCount() {
        return commentModelList.size();
    }
    @Override
    public int getItemViewType(int position) {
        CommentModel commentModel = commentModelList.get(position);

        if(commentModel.getstaffID()== ApplicationVariable.ACCOUNT_DATA.id){
            return VIEW_TYPE_MESSAGE_SENT;
        }else
        {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.send_comment_list, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recieved_comment_list, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

       CommentModel taskobj = commentModelList.get(position);
//
//        holder.time.setText(taskobj.getcommented_on());
//        holder.name.setText(taskobj.getstaff());
//        holder.msg.setText(taskobj.getcomment());

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(taskobj);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(taskobj);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView time, name, msg;

        SentMessageHolder(View view) {
            super(view);

            time = view.findViewById(R.id.textView);
            name = view.findViewById(R.id.textView3);
            msg = view.findViewById(R.id.textView4);

        }

        void bind(CommentModel message) {
            msg.setText(message.getcomment());
            name.setText(message.getstaff());
            time.setText(message.getcommented_on());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView time, name, msg;


        ReceivedMessageHolder(View view) {
            super(view);


            time = view.findViewById(R.id.textView);
            name = view.findViewById(R.id.textView3);
            msg = view.findViewById(R.id.textView4);

        }

        void bind(CommentModel message) {
            msg.setText(message.getcomment());
            name.setText(message.getstaff());
            time.setText(message.getcommented_on());
        }
    }



}
