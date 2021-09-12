package com.example.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    private static final String ARG_NAME = "name";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_DATE = "date";

    private String name;
    private String description;
    private Date date;
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm:ss");

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Name.
     * @param description Description.
     * @param date Date.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String name, String description, Date date) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_DATE, format.format(date));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
            description = getArguments().getString(ARG_DESCRIPTION);
            try {
                date = format.parse(getArguments().getString(ARG_DATE));
            } catch (ParseException e) {
                e.printStackTrace();
                date = new Date();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup ret = (ViewGroup)
            inflater.inflate(R.layout.fragment_note, container, false);
        if (name != null) {
            ((TextView) ret.findViewById(R.id.name_text)).setText(name);
        }
        if (description != null) {
            ((TextView) ret.findViewById(R.id.description_text)).setText(description);
        }
        if (date != null) {
            ((TextView) ret.findViewById(R.id.date_text)).setText(format.format(date));
        }
        return ret;
    }
}