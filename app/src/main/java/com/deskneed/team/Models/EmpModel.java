package com.deskneed.team.Models;

public class EmpModel {
        public int dept;
        public String name;

    public EmpModel(int dept, String name) {
        this.dept = dept;
        this.name = name;
    }

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
