package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final List<Note> ITEMS = new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ITEMS.add(new Note("Заметка №1", "ничего особенного"));
        ITEMS.add(new Note("Заметка №2", "тоже ничего особенного"));
        ITEMS.add(new Note("Заметка №3", "вообще ничего особенного"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}