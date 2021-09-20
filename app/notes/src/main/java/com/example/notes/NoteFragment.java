package com.example.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment implements View.OnClickListener {

    public static final String RESULT = "result";
    public static final String SAVED = "saved";

    private Note note;
    private boolean forEdit = false;
    private ViewGroup form;
    private boolean added = false;
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm:ss");

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param note Note class.
     * @return A new instance of fragment NoteFragment.
     */
    public static NoteFragment newInstance(Note note, boolean forEdit) {
        NoteFragment fragment = new NoteFragment();
        fragment.note = note;
        fragment.forEdit = forEdit;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        form = (ViewGroup)inflater.inflate(R.layout.fragment_note, container, false);
        if (forEdit) {
            form.findViewById(R.id.name_text).setVisibility(View.INVISIBLE);
            form.findViewById(R.id.description_text).setVisibility(View.INVISIBLE);
            form.findViewById(R.id.save).setOnClickListener(this);
        } else {
            form.findViewById(R.id.name_edit).setVisibility(View.INVISIBLE);
            form.findViewById(R.id.description_edit).setVisibility(View.INVISIBLE);
            form.findViewById(R.id.save).setVisibility(View.INVISIBLE);
        }
        if (note == null) {
            note = new Note("", "");
            added = true;
        }
        if (note.name != null) {
            if (forEdit) {
                ((EditText)form.findViewById(R.id.name_edit)).setText(note.name);
            } else {
                ((TextView)form.findViewById(R.id.name_text)).setText(note.name);
            }
        }
        if (note.description != null) {
            if (forEdit) {
                ((EditText)form.findViewById(R.id.description_edit)).setText(note.description);
            } else {
                ((TextView)form.findViewById(R.id.description_text)).setText(note.description);
            }
        }
        if (note.date != null) {
            ((TextView)form.findViewById(R.id.date_text)).setText(format.format(note.date));
        }
        return form;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.save:
                if (note != null) {
                    note.name = String.valueOf(
                        ((EditText)form.findViewById(R.id.name_edit)).getText()
                    );
                    note.description = String.valueOf(
                        ((EditText)form.findViewById(R.id.description_edit)).getText()
                    );
                }
                if (added) {
                    MainActivity.ITEMS.add(note);
                }
                Bundle result = new Bundle();
                result.putBoolean(SAVED, true);
                getParentFragmentManager().setFragmentResult(RESULT, result);
                break;
        }
    }

}