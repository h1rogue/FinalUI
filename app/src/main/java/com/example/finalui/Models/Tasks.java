package com.example.finalui.Models;

import com.example.finalui.TasksModel;

import java.util.List;

public class Tasks {

        private String slip_no;
        private String customer_name;
        private String requirement;
        private String people;
        private String task;
        private String taskdate;
        private String time_assigned;
        private String time_work;
        private String task_duration;
        private String status;
        private List<UpdateModel> updateModelList;
        private List<PuchaseModel> puchaseModelList;
        private List<CommentModel> commentModelList;
        private List<TasksModel> tasksModelList;

    public Tasks(String taskdate, String slip_no, String customer_name, String requirement, String people, String task, String time_assigned, String time_work, String task_duration, String status, List<UpdateModel> updateModelList, List<PuchaseModel> puchaseModelList, List<CommentModel> commentModelList, List<TasksModel> tasksModel) {
        this.slip_no = slip_no;
        this.customer_name = customer_name;
        this.requirement = requirement;
        this.people = people;
        this.task = task;
        this.time_assigned = time_assigned;
        this.time_work = time_work;
        this.task_duration = task_duration;
        this.taskdate  = taskdate;
        this.status = status;
        this.updateModelList = updateModelList;
        this.puchaseModelList = puchaseModelList;
        this.commentModelList = commentModelList;
        this.tasksModelList=tasksModel;
    }


    public String getSlip_no() {
        return slip_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getRequirement() {
        return requirement;
    }

    public String getPeople() {
        return people;
    }

    public String getTask() {
        return task;
    }

    public String getTime_assigned() {
        return time_assigned;
    }

    public String getTime_work() {
        return time_work;
    }

    public String getTask_duration() {
        return task_duration;
    }

    public String getTaskdate() {
        return taskdate;
    }

    public String getStatus() {
        return status;
    }

    public List<UpdateModel> getUpdateModelList() {
        return updateModelList;
    }
    public List<TasksModel> getTasksModelList() {
        return tasksModelList;
    }

    public List<PuchaseModel> getPuchaseModelList() {
        return puchaseModelList;
    }


    public List<CommentModel> getCommentModelList() {
        return commentModelList;
    }

    public void setUpdateModelList(List<UpdateModel> updateModelList) { this.updateModelList = updateModelList; }

    public void setTasksModelList(List<TasksModel> tasksModelList) { this.tasksModelList = tasksModelList; }

    public void setCommentModelList(List<CommentModel> commentModelList) { this.commentModelList = commentModelList; }

    public void setStatus(String status) { this.status = status; }

    public void setSlip_no(String slip_no) {
        this.slip_no = slip_no;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setTime_assigned(String time_assigned) {
        this.time_assigned = time_assigned;
    }

    public void setTime_work(String time_work) {
        this.time_work = time_work;
    }

    public void setTask_duration(String task_duration) {
        this.task_duration = task_duration;
    }
    public void setTaskdate(String task_duration) {
        this.taskdate= taskdate;
    }
}

//order/slip/update/get;
//filter - slip_number
//slip/purchase/get

//  for (int i = 0; i < arrSize; ++i) {
//        String key = a.getJSONObject(i).getString("month");
//        key += '_';
//        key += a.getJSONObject(i).getString("type");//this k is the key
//        SalaryReport salaryReport;
//        float ss = 0;
//
//        salaryModel = new SalaryModel(a.getJSONObject(i).getString("id"), a.getJSONObject(i).getString("date_credited"),a.getJSONObject(i).getString("amount"),
//        a.getJSONObject(i).getString("month"), a.getJSONObject(i).getString("type"));
//
//        if(map.containsKey(key) && map.get(key) != null){
//        SalaryReport tempSalaryReport = map.get(key);
//        ss = tempSalaryReport.amount;
//        ss = ss + Float.parseFloat(a.getJSONObject(i).getString("amount"));
//        tempSalaryReport.records.add(salaryModel);
//        }
//        else{
//        SalaryReport tempSalaryReport =
//        new SalaryReport(salaryModel.month, salaryModel.type, salaryModel.amount, new ArrayList<>());
//        map.put(key, tempSalaryReport);
//        }
//        //map.put(key,salaryReport);
//        Log.d("pp", String.valueOf(map.size()));
//        }




