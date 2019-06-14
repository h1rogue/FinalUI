package com.example.finalui;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class TasksModel implements Serializable {

    private String slip_no;
    private String customer_name;
    private String requirement;
    private String vendor;
    private String dept;
    private String nextUpdateDate;
    private String employee;
    private String slipDate;

    public TasksModel(String slip_no, String customer_name, String requirement, String vendor, String dept, String nextUpdateDate, String employee, String slipDate) {
        this.slip_no = slip_no;
        this.customer_name = customer_name;
        this.requirement = requirement;
        this.vendor = vendor;
        this.dept = dept;
        this.nextUpdateDate = nextUpdateDate;
        this.employee = employee;
        this.slipDate = slipDate;
    }

    public String getSlip_no() {
        return slip_no;
    }

    public void setSlip_no(String slip_no) {
        this.slip_no = slip_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getNextUpdateDate() {
        return nextUpdateDate;
    }

    public void setNextUpdateDate(String nextUpdateDate) {
        this.nextUpdateDate = nextUpdateDate;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getSlipDate() {
        return slipDate;
    }

    public void setSlipDate(String slipDate) {
        this.slipDate = slipDate;
    }
}


