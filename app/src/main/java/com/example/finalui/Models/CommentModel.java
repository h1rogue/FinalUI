package com.example.finalui.Models;

import java.io.Serializable;

public class CommentModel implements Serializable {
    String staff;
    String comment;
    String commented_on;
    String slip_no;
    String id;


    public CommentModel(String staff, String comment, String commented_on,String slip_no,String id) {
        this.id=id;
        this.slip_no=slip_no;
        this.staff = staff;
        this.comment = comment;
        this.commented_on = commented_on;
    }

    public String getstaff() {
        return staff;
    }
    public String getSlip_no() {
        return slip_no;
    }
    public String getcommented_on() {
        return commented_on;
    }
    public String getId() {
        return id;
    }
    public String getcomment() {
        return comment;
    }

    public void setcomment(String comment) {
        this.comment = comment;
    }
    public void setstaff(String staff) {
        this.staff = staff;
    }
    public void setcommented_on(String commented_on) {
        this.commented_on = commented_on;
    }
}
