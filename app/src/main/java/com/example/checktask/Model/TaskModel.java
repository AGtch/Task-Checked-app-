package com.example.checktask.Model;

public class TaskModel {
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

    public byte getStatus() {
        return (byte) status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
