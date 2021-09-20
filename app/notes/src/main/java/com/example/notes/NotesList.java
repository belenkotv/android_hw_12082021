package com.example.notes;

import java.util.ArrayList;
import java.util.List;

public class NotesList {

    public static final String DB = "notes";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DATE = "date";

    public static class Item {
        String id;
        Note data;
        public Item(String id, Note data) {
            this.id = id;
            this.data = data;
        }
    }

    private static NotesList instance;
    private List<Item> items;
    private NotesList(){
        items = new ArrayList<>();
    }

    public static List<Item> getInstance() {
        if (instance == null) {
            instance = new NotesList();
        }
        return instance.items;
    }

}
