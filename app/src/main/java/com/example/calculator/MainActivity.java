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
            String currentSymbol = (String) currentButton.getText();
            int lengthStr = ArithmeticExample.length();
            if (lengthStr > 0){
                String lastSymbol = "";
                lastSymbol = ArithmeticExample.substring(lengthStr-1);
                String strOperations = "+-×÷,";
                if (strOperations.contains(lastSymbol) && strOperations.contains(currentSymbol)){
                    ArithmeticExample= ArithmeticExample.substring(0,lengthStr-1);
                }
            }
            ArithmeticExample += currentSymbol;
            textView.setText(ArithmeticExample);
        }
    }

    public void clearArithmeticExample(View v) {
        ArithmeticExample = "";
        textView.setText(ArithmeticExample);
    }

    public void calculateOnButton(View v) {
        if (ArithmeticExample.equals("")){
            return;
        }

        String resString = "ERROR";
        try {
            double res = Calculate.CalculateFromString(ArithmeticExample);
            resString = Double.toString(res);
        } catch (NegativeArraySizeException e){
//            System.out.println(e.getMessage());
        }
        ArithmeticExampleHistory += ArithmeticExample + " = " + resString +"\n";
        textViewHistory.setText(ArithmeticExampleHistory);

        ArithmeticExample = "";
        textView.setText(ArithmeticExample);
    }
}