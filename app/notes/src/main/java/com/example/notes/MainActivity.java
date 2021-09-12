package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final List<Note> ITEMS = new ArrayList<Note>();
    public static MainActivity mainActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        ITEMS.clear();
        ITEMS.add(new Note("Заметка №1", "ничего особенного"));
        ITEMS.add(new Note("Заметка №2", "тоже ничего особенного"));
        ITEMS.add(new Note("Заметка №3", "вообще ничего особенного"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int orientation = this.getApplicationContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showNoteFragment(0);
        } else {
            findViewById(R.id.line).setVisibility(View.GONE);
            findViewById(R.id.note).setVisibility(View.GONE);
        }
    }

    public void showNoteFragment(int position) {
        Note note = ITEMS.get(position);
        NoteFragment fragment = NoteFragment.newInstance(note.getName(), note.getDescription(), note.getDate());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.note, fragment);
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

}