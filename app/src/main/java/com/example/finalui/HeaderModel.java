package com.example.finalui;

public class HeaderModel {
    public int id;
    public int totalamt;
    public int creditedamt;
    public String MonthName;
    public String type;


    public HeaderModel(int id, int totalamt, String monthName,int creditedamt, String type) {
        this.totalamt = totalamt;
        this.id=id;
        this.MonthName = monthName;
        this.creditedamt = creditedamt;
        this.type=type;
    }

    public HeaderModel(int i, String january, int i1) {
        this.creditedamt=i1;
        this.MonthName=january;
        this.totalamt=i;
    }

    public String getType() {
        return type;
    }
    public int getId() {
        return id;
    }
    public int getTotalamt() {
        return totalamt;
    }
    public int getCreditedamt() {
        return creditedamt;
    }

    public void setTotalamt(int totalamt) {
        this.totalamt = totalamt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreditedamt(int creditedamt) {
        this.creditedamt = creditedamt;
    }

    public String getMonthName() {
        return MonthName;
    }

    public void setMonthName(String monthName) {
        MonthName = monthName;
    }
}
