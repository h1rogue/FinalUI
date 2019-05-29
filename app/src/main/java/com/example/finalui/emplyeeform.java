package com.example.finalui;

public class emplyeeform {
int empID,salary;
long contact;
String name;
String address;
String bloodgrp;
String dob;

    public emplyeeform(int empID, String name, String address, String bloodgrp, String dob, int salary, long contact) {
        this.empID = empID;
        this.name = name;
        this.address=address;
        this.bloodgrp=bloodgrp;
        this.dob=dob;
        this.salary=salary;
        this.contact=contact;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public long getcontact() {
        return contact;
    }

    public void setcontact(int contact) {
        this.contact = contact;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodgrp() {
        return bloodgrp;
    }

    public void setBloodgrp(String bloodgrp) {
        this.bloodgrp = bloodgrp;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
