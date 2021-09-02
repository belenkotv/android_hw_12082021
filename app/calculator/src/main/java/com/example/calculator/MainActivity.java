package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Calculator calculator = new Calculator();
    TextView screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}