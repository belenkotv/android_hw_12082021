package com.example.notes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Notes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<NotesList.Item> notes = NotesList.getInstance();
        notes.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(NotesList.DB)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            NotesList.Item item = new NotesList.Item(
                                document.getId(),
                                document.toObject(Note.class)
                            );
                            notes.add(item);
                        }
                        navigateFragment(R.id.list);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getSupportFragmentManager().setFragmentResultListener(NoteShortFragment.RESULT, this,
            new FragmentResultListener() {
                @Override
                public void onFragmentResult(String requestKey, Bundle bundle) {
                    Integer pos = bundle.getInt(NoteShortFragment.POSITION);
                    if (pos != null) {
                        showNoteFragment(pos);
                    }
                }
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create:
                ((NoteShortFragment)getSupportFragmentManager().findFragmentById(R.id.notes))
                    .onClick(-1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showNoteFragment(int position) {
        Fragment oldFragment = getSupportFragmentManager().findFragmentById(R.id.note);
        if (position >= NotesList.getInstance().size()) {
            if (oldFragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.remove(oldFragment);
                ft.commit();
            }
        } else {
            NoteFragment fragment = NoteFragment.newInstance(position, false);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (oldFragment == null) {
                ft.add(R.id.note, fragment);
            } else {
                ft.replace(R.id.note, fragment);
            }
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.open,
                R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)){
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });

    }

    private boolean navigateFragment(int id) {
        int visibility = View.GONE;
        Fragment fragment;
        switch (id) {
            case R.id.list:
                int orientation = this.getApplicationContext().getResources()
                    .getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    visibility = View.VISIBLE;
                }
                fragment = new NoteShortFragment();
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                break;
            case R.id.about:
                fragment = new AboutFragment();
                break;
            default:
                return false;
        }
        findViewById(R.id.line).setVisibility(visibility);
        findViewById(R.id.note).setVisibility(visibility);
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.notes, fragment)
            .commit();
        if ((id == R.id.list) && (visibility == View.VISIBLE)) {
            showNoteFragment(0);
        }
        return true;
    }

}