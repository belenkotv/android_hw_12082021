package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class NoteActivity extends AppCompatActivity {

    public final static String INDEX = "index";
    public final static String EDIT = "edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Bundle bundle = getIntent().getExtras();
        Note note = null;
        Boolean forEdit = false;
        if (bundle != null) {
            Integer position = bundle.getInt(INDEX, -1);
            if ((position >= 0) && (position < MainActivity.ITEMS.size())) {
                note = MainActivity.ITEMS.get(position);
                forEdit = bundle.getBoolean(EDIT, false);
            } else {
                forEdit = true;
            }
        }
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.note, NoteFragment.newInstance(note, forEdit))
            .commit();
        getSupportFragmentManager().setFragmentResultListener(NoteFragment.RESULT, this,
            new FragmentResultListener() {
                @Override
                public void onFragmentResult(String requestKey, Bundle bundle) {
                    if (bundle.getBoolean(NoteFragment.SAVED, false)) {
                        setResult(RESULT_OK);
                    }
                    finish();
                }
            }
        );
    }

}