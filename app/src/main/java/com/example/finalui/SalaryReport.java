package com.example.finalui;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SalaryReport {
    public String month;
    public String type;
    public float amount;
    public List<SalaryModel> records = new ArrayList<>();

    public SalaryReport(String month, String type, float amount, List<SalaryModel> records) {
        this.month = month;
        this.type = type;
        this.amount = amount;
        this.records = records;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<SalaryModel> getRecords() {
        return records;
    }

    public void setRecords(List<SalaryModel> records) {
       this.records = records;
    }
}
