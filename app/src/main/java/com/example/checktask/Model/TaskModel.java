package com.example.checktask.Model;

/**
 * @author Ali Ahmed Gomaa
 * @version 1.0
 */
public class TaskModel {

    /*
     * task is String which user input
     * status check if task is done or not
     */
    private String task;
    private int id;
    private int status;
    private String description;
    private String date;
    private String time;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTasks() {
        return task;
    }

    public void setTasks(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    /**
     * @param status should be 1 or 0 , 1 mean that the task is done.
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
