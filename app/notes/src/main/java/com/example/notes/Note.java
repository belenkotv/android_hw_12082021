package com.example.notes;

import java.util.Date;

public class Note {

    String name;
    String description;
    Date date;

    public Note(String name, String description) {
        this.name = name;
        this.description = description;
        this.date = new Date();
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
