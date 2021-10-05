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
    private String tasks ;
    private int id ;
    private int status;

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param status should be 1 or 0 , 1 mean that the task is done.
     */
    public void setStatus(int status) {
        this.status = status;
    }


    public int getStatus() {
        return  status;
    }
}
