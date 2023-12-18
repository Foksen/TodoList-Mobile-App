package com.example.todolist;

public class TaskEntity {
    private final String title;
    private final String desc;

    public TaskEntity(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
