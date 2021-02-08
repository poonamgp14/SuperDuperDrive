package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteId;
    private String title;
    private String description;
    private Integer userId;

    public Note(Integer noteId, String title, String description, Integer userId){
        this.noteId = noteId;
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
