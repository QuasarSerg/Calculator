package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String ArithmeticExample = "";
    private String ArithmeticExampleHistory = "";

    private TextView textView;
    private TextView textViewHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textViewHistory = (TextView) findViewById(R.id.textViewHistory);
    }

    public void makeArithmeticExample(View v) {
        if (v instanceof Button) {
            Button currentButton = (Button) v;
            ArithmeticExample += currentButton.getText();
            textView.setText(ArithmeticExample);
        }
    }

    public void clearArithmeticExample(View v) {
        ArithmeticExample = "";
        textView.setText(ArithmeticExample);
    }

    public void calculateOnButton(View v) {
        Double res = Calculate.CalculateFromString(ArithmeticExample);
        ArithmeticExampleHistory += ArithmeticExample + " = " + res.toString() +"\n";
        textViewHistory.setText(ArithmeticExampleHistory);

        ArithmeticExample = "";
        textView.setText(ArithmeticExample);
    }
}