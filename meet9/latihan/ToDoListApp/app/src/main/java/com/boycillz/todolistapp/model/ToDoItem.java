package com.boycillz.todolistapp.model;

public class ToDoItem {
    private String title;
    private boolean isDone;

    public ToDoItem(String title) {
        this.title = title;
        this.isDone = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return title + (isDone ? " (Selesai)" : " (Belum Selesai)");
    }
}
