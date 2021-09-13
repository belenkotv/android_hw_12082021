package com.example.notes;

import java.util.Date;

public class Note {

    String name;
    String description;
    Date date;

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

}
