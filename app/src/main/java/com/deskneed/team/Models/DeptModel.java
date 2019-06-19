package com.deskneed.team.Models;

import java.util.ArrayList;

public class DeptModel {
    int id;
    String dept;
    ArrayList<String> statuses;


    public DeptModel(int id, String dept, ArrayList<String> statuses) {
        this.id = id;
        this.dept = dept;
        this.statuses = statuses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public ArrayList<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<String> statuses) {
        this.statuses = statuses;
    }
}
