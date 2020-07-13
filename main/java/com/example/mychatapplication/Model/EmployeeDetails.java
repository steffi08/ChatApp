package com.example.mychatapplication.Model;

public class EmployeeDetails {
    private String title;
    private String fname,lname,dob,designation,Address,id;

    public EmployeeDetails() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmployeeDetails(String title, String fname, String lname, String dob, String designation, String address, String id) {
        this.title = title;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.designation = designation;
        Address = address;
        this.id = id;
    }

}
