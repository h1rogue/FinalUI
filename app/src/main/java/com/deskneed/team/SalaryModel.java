package com.deskneed.team;

public class SalaryModel {
    String id,dateCredited;
    String amount;
    String month;
    String type;

    public SalaryModel(String remarks, String dateCredited, String amount, String month, String type) {
        this.dateCredited = dateCredited;
        this.id = remarks;
        this.amount = amount;
        this.month=month;
        this.type=type;
    }

    public String getDateCredited() {
        return dateCredited;
    }

    public String getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDateCredited(String month) {
        this.dateCredited = month;
    }

    public String getId() {
        return id;
    }

    public void setRemarks(String remarks) {
        this.id = remarks;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
