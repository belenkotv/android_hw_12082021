package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import android.os.Bundle;

public class NoteActivity extends AppCompatActivity {

    public final static String INDEX = "index";

    class FragmentFactoryImpl extends FragmentFactory {
        private int position;
        FragmentFactoryImpl(int position) {
            this.position = position;
        }
        @Override
        public Fragment instantiate(ClassLoader classLoader, String className) {
            if (className.equals("com.example.notes.NoteFragment")) {
                Note note = MainActivity.ITEMS.get(position);
                return NoteFragment.newInstance(note);
            } else {
                return super.instantiate(classLoader, className);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Integer position = bundle.getInt(INDEX);
            if (position != null) {
                getSupportFragmentManager().setFragmentFactory(new FragmentFactoryImpl(position));
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }
}