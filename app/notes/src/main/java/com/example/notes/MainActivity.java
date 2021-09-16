package com.example.notes;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

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
        initViews();
        int orientation = this.getApplicationContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showNoteFragment(0);
        } else {
            findViewById(R.id.line).setVisibility(View.GONE);
            findViewById(R.id.note).setVisibility(View.GONE);
        }
    }@Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showNoteFragment(int position) {
        Note note = ITEMS.get(position);
        NoteFragment fragment = NoteFragment.newInstance(note);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.note, fragment);
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
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
        return true;
    }

}