package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String theme = bundle.getString(MainActivity.THEME);
            if (theme != null) {
                if (theme.equals(MainActivity.LIGHT)) {
                    setTheme(R.style.Theme_Calculator);
                } else if (theme.equals(MainActivity.NIGHT)) {
                    setTheme(R.style.Theme_CalculatorNight);
                }
            }
        }
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onClick(View view) {
        String theme;
        switch (view.getId()) {
            case (R.id.light_theme):
                theme = MainActivity.LIGHT;
                break;
            case (R.id.night_theme):
                theme = MainActivity.NIGHT;
                break;
            default:
                return;
        }
        Intent intent = new Intent();
        intent.putExtra(MainActivity.THEME, theme);
        setResult(RESULT_OK, intent);
        finish();
    }

}