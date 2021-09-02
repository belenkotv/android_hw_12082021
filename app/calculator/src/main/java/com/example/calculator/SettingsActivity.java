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
            if (theme == MainActivity.LIGHT) {
                setTheme(R.style.Theme_Calculator);
            } else if (theme == MainActivity.NIGHT) {
                setTheme(R.style.Theme_CalculatorNight);
            }
        }
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.light_theme):
                Intent intent1 = new Intent();
                intent1.putExtra(MainActivity.THEME, MainActivity.LIGHT);
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case (R.id.night_theme):
                Intent intent2 = new Intent();
                intent2.putExtra(MainActivity.THEME, MainActivity.NIGHT);
                setResult(RESULT_OK, intent2);
                finish();
                break;
        }
    }

}