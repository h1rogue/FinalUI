package com.deskneed.team.Models;

import java.io.Serializable;

public class UpdateModel implements Serializable {
    public String id;
    public String slipno;
    public String updateDate;
    public String status;
    public String nextUpdateDate;
    public String dept;
    public String employee;
    public String updatedBy;

    public UpdateModel(String id, String slipno, String updateDate, String status, String nextUpdateDate, String dept, String employee, String updatedBy) {
        this.id = id;
        this.slipno = slipno;
        this.updateDate = updateDate;
        this.status = status;
        this.nextUpdateDate = nextUpdateDate;
        this.dept = dept;
        this.employee = employee;
        this.updatedBy = updatedBy;
    }

    public String getId() {
        return id;
    }

    public String getSlipno() {
        return slipno;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getStatus() {
        return status;
    }

    public String getNextUpdateDate() {
        return nextUpdateDate;
    }

    public String getDept() {
        return dept;
    }

    public String getEmployee() {
        return employee;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSlipno(String slipno) {
        this.slipno = slipno;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNextUpdateDate(String nextUpdateDate) {
        this.nextUpdateDate = nextUpdateDate;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
