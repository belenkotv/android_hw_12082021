package com.example.calculator;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String THEME = "THEME";
    public final static String LIGHT = "LIGHT";
    public final static String NIGHT = "NIGHT";

    Calculator calculator = new Calculator();
    TextView screen;
    String theme;
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Bundle bundle = data.getExtras();
                        if (bundle == null) {
                            return;
                        }
                        String theme = bundle.getString(THEME);
                        if (theme == null) {
                            return;
                        }
                        if (!theme.equals(LIGHT) && !theme.equals(NIGHT)) {
                            return;
                        }
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        intent.putExtra(THEME, theme);
                        MainActivity.this.startActivity(intent);
                        finish();
                    }
                }
            });
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            theme = bundle.getString(THEME);
            if (theme != null) {
                if (theme.equals(LIGHT)) {
                    setTheme(R.style.Theme_Calculator);
                } else if (theme.equals(NIGHT)) {
                    setTheme(R.style.Theme_CalculatorNight);
                }
            }
        }
        setContentView(R.layout.activity_main);
        screen = findViewById(R.id.screen);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.button_c):
                screen.setText(calculator.clear());
                break;
            case (R.id.button_back):
                screen.setText(calculator.inputDelete());
                break;
            case (R.id.button_point):
                screen.setText(calculator.inputPoint());
                break;
            case (R.id.button_sum):
                screen.setText(calculator.inputOperation('+'));
                break;
            case (R.id.button_sub):
                screen.setText(calculator.inputOperation('-'));
                break;
            case (R.id.button_mult):
                screen.setText(calculator.inputOperation('*'));
                break;
            case (R.id.button_div):
                screen.setText(calculator.inputOperation('/'));
                break;
            case (R.id.button_sign):
                screen.setText(calculator.inputSign());
                break;
            case (R.id.button_percent):
                screen.setText(calculator.inputPercent());
                break;
            case (R.id.button_equals):
                screen.setText(calculator.inputEqual());
                break;
            case (R.id.button_0):
                screen.setText(calculator.inputDigit('0'));
                break;
            case (R.id.button_1):
                screen.setText(calculator.inputDigit('1'));
                break;
            case (R.id.button_2):
                screen.setText(calculator.inputDigit('2'));
                break;
            case (R.id.button_3):
                screen.setText(calculator.inputDigit('3'));
                break;
            case (R.id.button_4):
                screen.setText(calculator.inputDigit('4'));
                break;
            case (R.id.button_5):
                screen.setText(calculator.inputDigit('5'));
                break;
            case (R.id.button_6):
                screen.setText(calculator.inputDigit('6'));
                break;
            case (R.id.button_7):
                screen.setText(calculator.inputDigit('7'));
                break;
            case (R.id.button_8):
                screen.setText(calculator.inputDigit('8'));
                break;
            case (R.id.button_9):
                screen.setText(calculator.inputDigit('9'));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                if (theme != null) {
                    intent.putExtra(THEME, theme);
                }
                launcher.launch(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}