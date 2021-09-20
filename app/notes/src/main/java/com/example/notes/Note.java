package com.example.notes;

import java.util.Date;

public class Note {

    private String name;
    private String description;
    private Date date;

    public Note() {
    }

    public Note(String name, String description) {
        this(name, description, new Date());
    }

    public Note(String name, String description, Date date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
