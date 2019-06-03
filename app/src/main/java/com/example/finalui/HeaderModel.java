package com.example.finalui;

public class HeaderModel {
    int totalamt;
    String MonthName;

    public HeaderModel(int totalamt, String monthName) {
        this.totalamt = totalamt;
        MonthName = monthName;
    }

    public int getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(int totalamt) {
        this.totalamt = totalamt;
    }

    public String getMonthName() {
        return MonthName;
    }

    public void setMonthName(String monthName) {
        MonthName = monthName;
    }
}
