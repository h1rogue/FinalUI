package com.example.finalui;

public class SalaryModel {
    String month,remarks;
    int amount;

    public SalaryModel(String month, String remarks, int amount) {
        this.month = month;
        this.remarks = remarks;
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
