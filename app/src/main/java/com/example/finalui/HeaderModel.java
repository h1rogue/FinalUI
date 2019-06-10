package com.example.finalui;

public class HeaderModel {
    int totalamt;
    int creditedamt;
    String MonthName;

    public HeaderModel(int totalamt, String monthName,int creditedamt) {
        this.totalamt = totalamt;
        MonthName = monthName;
        this.creditedamt = creditedamt;
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
