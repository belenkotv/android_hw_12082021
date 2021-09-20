package com.example.notes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class NoteShortFragment extends Fragment implements NoteRecyclerViewAdapter.NoteClickListener {

    public static final String RESULT = "result";
    public static final String POSITION = "position";

    private RecyclerView list;
    private ActivityResultLauncher<Intent> launcher;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteShortFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        list.getAdapter().notifyDataSetChanged();
                    }
                }
            });
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_short_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            list = (RecyclerView) view;
            list.setLayoutManager(new LinearLayoutManager(context));
            list.setAdapter(new NoteRecyclerViewAdapter(this));
        }
        return view;
    }

    @Override
    public void onClick(int position) {
        showNote(position, true);
    }

    @Override
    public void onLongClick(int position, View view) {
        if (position < 0) {
            return;
        }
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.menu_context, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        launchNoteActivity(position, true);
                        break;
                    case R.id.delete:
                        new AlertDialog.Builder(view.getContext())
                            .setTitle(R.string.delete)
                            .setMessage(R.string.delete_note)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    List<NotesList.Item> notes = NotesList.getInstance();
                                    FirebaseFirestore.getInstance()
                                        .collection(NotesList.DB)
                                        .document(notes.get(position).id)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                notes.remove(position);
                                                list.getAdapter().notifyDataSetChanged();
                                                showNote(position, false);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(Exception e) {
                                                Log.w(MainActivity.TAG, "Error deleting document", e);
                                            }
                                        });
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    private void launchNoteActivity(int position, boolean forEdit) {
        Intent intent = new Intent(getContext(), NoteActivity.class);
        intent.putExtra(NoteActivity.INDEX, position);
        intent.putExtra(NoteActivity.EDIT, forEdit);
        launcher.launch(intent);
    }

    private void showNote(int position, boolean onClick) {
        if (position < 0) {
            launchNoteActivity(position, true);
            return;
        }
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Bundle bundle = new Bundle();
            bundle.putInt(POSITION, position);
            getParentFragmentManager().setFragmentResult(RESULT, bundle);
        } else if (onClick && (position < NotesList.getInstance().size())) {
            launchNoteActivity(position, false);
        }
    }

}