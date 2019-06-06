package com.example.finalui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.Models.PuchaseModel;
import com.example.finalui.R;

import java.util.List;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder> {

    private Context context;
    private List<PuchaseModel> puchaseModelList;

    public MyAdapter3(Context context, List<PuchaseModel> puchaseModelList) {
        this.context = context;
        this.puchaseModelList = puchaseModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.purchase_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PuchaseModel taskobj = puchaseModelList.get(position);

        holder.slipno.setText(taskobj.getSlipno());
        holder.purchaseDate.setText(taskobj.getPurchaseddate());
        holder.vendor.setText(taskobj.getVendor());
        holder.invoiceNo.setText(taskobj.getInvoiceno());
        holder.invoiceAmt.setText(taskobj.getInvoiceamt());
        holder.paymentStatus.setText(taskobj.getPaymentstatus());
        holder.remarks.setText(taskobj.getRemarks());
    }

    @Override
    public int getItemCount() {
        return puchaseModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView slipno, purchaseDate, vendor, invoiceNo, invoiceAmt, paymentStatus, remarks;


        public MyViewHolder(@NonNull View view) {
            super(view);

            slipno = view.findViewById(R.id.textView);
            purchaseDate = view.findViewById(R.id.textView21);
            vendor = view.findViewById(R.id.textView20);
            invoiceNo = view.findViewById(R.id.textView18);
            invoiceAmt = view.findViewById(R.id.textView19);
            paymentStatus = view.findViewById(R.id.textView22);
            remarks = view.findViewById(R.id.textView23);

        }
    }
}
