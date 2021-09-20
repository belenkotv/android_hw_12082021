package com.example.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment implements View.OnClickListener {

    public static final String RESULT = "result";
    public static final String SAVED = "saved";

    private int position;
    private boolean forEdit = false;
    private ViewGroup form;
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm:ss");

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Note position in global list.
     * @return A new instance of fragment NoteFragment.
     */
    public static NoteFragment newInstance(int position, boolean forEdit) {
        NoteFragment fragment = new NoteFragment();
        fragment.position = position;
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
        List<NotesList.Item> notes = NotesList.getInstance();
        if ((position >= 0) && (position < notes.size())) {
            Note note = notes.get(position).data;
            if (forEdit) {
                ((EditText)form.findViewById(R.id.name_edit)).setText(note.getName());
            } else {
                ((TextView)form.findViewById(R.id.name_text)).setText(note.getName());
            }
            if (forEdit) {
                ((EditText)form.findViewById(R.id.description_edit)).setText(note.getDescription());
            } else {
                ((TextView)form.findViewById(R.id.description_text)).setText(note.getDescription());
            }
            ((TextView)form.findViewById(R.id.date_text)).setText(format.format(note.getDate()));
        } else {
            ((TextView)form.findViewById(R.id.date_text)).setText(format.format(new Date()));
            position = -1;
        }
        return form;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                String name = String.valueOf(
                    ((EditText)form.findViewById(R.id.name_edit)).getText()
                );
                String description = String.valueOf(
                    ((EditText)form.findViewById(R.id.description_edit)).getText()
                );
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                List<NotesList.Item> notes = NotesList.getInstance();
                if (position < 0) {
                    Date date = new Date();
                    try {
                        date = format.parse(
                            ((EditText)form.findViewById(R.id.description_edit)).getText().toString()
                        );
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Note note = new Note(name, description, date);
                    db.collection(NotesList.DB)
                        .add(note)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                notes.add(new NotesList.Item(documentReference.getId(), note));
                                quit();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Log.w(MainActivity.TAG, "Error adding document", e);
                            }
                        });
                } else {
                    NotesList.Item item = notes.get(position);
                    final DocumentReference ref = db.collection(NotesList.DB).document(item.id);
                    db.runTransaction(new Transaction.Function<Void>() {
                        @Override
                        public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                            transaction.update(ref, NotesList.FIELD_NAME, name);
                            transaction.update(ref, NotesList.FIELD_DESCRIPTION, description);
                            return null;
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            item.data.setName(name);
                            item.data.setDescription(description);
                            quit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.w(MainActivity.TAG, "Transaction failure.", e);
                        }
                    });
                }
                break;
        }
    }

    void quit() {
        Bundle result = new Bundle();
        result.putBoolean(SAVED, true);
        getParentFragmentManager().setFragmentResult(RESULT, result);
    }

}