package com.mycalc;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //
    Button btnInit,
            btn1, btn2, btn3, btnPlus,
            btn4, btn5, btn6, btnMinus,
            btn7, btn8, btn9, btnDiv,
            btnBack, btn0, btnEqual, btnMult;
    //
    Button[] btns, btnNums, btnOpers;
    TextView tvNum1, tvNum2, tvSymbol;
    //
    boolean isEqual = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if (true) return;
        setContentView(R.layout.activity_main);
        tvNum1 = (TextView) findViewById(R.id.tvNum1);
        tvNum2 = (TextView) findViewById(R.id.tvNum2);
        tvSymbol = (TextView) findViewById(R.id.tvSymbol);
        tvSymbol.setTextColor(Color.WHITE);
        //
        btn0 = (Button) findViewById(R.id.btnNum0);
        btn1 = (Button) findViewById(R.id.btnNum1);
        btn2 = (Button) findViewById(R.id.btnNum2);
        btn3 = (Button) findViewById(R.id.btnNum3);
        btn4 = (Button) findViewById(R.id.btnNum4);
        btn5 = (Button) findViewById(R.id.btnNum5);
        btn6 = (Button) findViewById(R.id.btnNum6);
        btn7 = (Button) findViewById(R.id.btnNum7);
        btn8 = (Button) findViewById(R.id.btnNum8);
        btn9 = (Button) findViewById(R.id.btnNum9);
        //
        btnPlus = (Button) findViewById(R.id.btnNumPlus);
        btnMinus = (Button) findViewById(R.id.btnNumMinus);
        btnDiv = (Button) findViewById(R.id.btnNumDiv);
        btnMult = (Button) findViewById(R.id.btnNumMult);
        //
        btnBack = (Button) findViewById(R.id.btnBack);
        btnEqual = (Button) findViewById(R.id.btnEqual);
        btnInit = (Button) findViewById(R.id.btnInit);
        //
        btns = new Button[]{
                btnInit,
                btn1, btn2, btn3, btnPlus,
                btn4, btn5, btn6, btnMinus,
                btn7, btn8, btn9, btnDiv,
                btnBack, btn0, btnEqual, btnMult};
        //
        btnNums = new Button[]{
                btn1, btn2, btn3,
                btn4, btn5, btn6,
                btn7, btn8, btn9,
                btn0};
        //
        btnOpers = new Button[]{
                btnPlus, btnMinus,
                btnDiv, btnMult};
        //
        for (int i = 0; i < btns.length; i++) {
            btns[i].setOnClickListener(this);
        }
    }

    public boolean clickBtnNums(View v) {
        for (Button btnNum : btnNums) {
            if (v == btnNum) {
                String accumulateStr = tvNum2.getText().toString();
                if (accumulateStr.length() >= 6) {
                    Toast.makeText(this, "6자가 넘네", Toast.LENGTH_SHORT).show();
                    return false;
                }
                String nowInputStr = ((Button) v).getText().toString();
                tvNum2.setText(accumulateStr + nowInputStr);
                if (isEqual) {
                    isEqual = false;
                    tvNum1.setText("");
                    tvSymbol.setText("");
                }
                return false;
            }
        }
        return true;
    }

    public boolean clickBtnOper(View v) {
        for (Button btnOper : btnOpers)
            if (v == btnOper) {
                tvNum1.setText(tvNum2.getText().toString());
                tvNum2.setText("");
                tvSymbol.setText(btnOper.getText().toString());
                return false;
            }
        return true;
    }

    @Override
    public void onClick(View v) {
        boolean isBtnNums = clickBtnNums(v);
        if (!isBtnNums) return;
        //
        boolean isBtnOper = clickBtnOper(v);
        if (!isBtnOper) return;
        //
        if (v == btnInit) {
            tvNum1.setText("");
            tvNum2.setText("");
            tvSymbol.setText("");
            isEqual = false;
        } else if (v == btnBack) {
            String resultStr = tvNum2.getText().toString();
            if (resultStr.length() > 0) {
                tvNum2.setText(resultStr.substring(0, resultStr.length() - 1));
            }
        } else if (v == btnEqual) {
            String symbol = tvSymbol.getText().toString();
            double resultValue = 0;
            String inputNum1 = tvNum1.getText().toString();
            String inputNum2 = tvNum2.getText().toString();
            //
            if (inputNum1.length() <= 0 || inputNum2.length() <= 0) {
                Toast.makeText(this, "잘못 입력", Toast.LENGTH_SHORT).show();
                return;
            }
            //
            double num1 = Double.parseDouble(inputNum1);
            double num2 = Double.parseDouble(inputNum2);
            if (symbol.equals(btnPlus.getText().toString()))
                resultValue = num1 + num2;
            else if (symbol.equals(btnMinus.getText().toString()))
                resultValue = num1 - num2;
            else if (symbol.equals(btnDiv.getText().toString()))
                resultValue = num1 / num2;
            else if (symbol.equals(btnMult.getText().toString()))
                resultValue = num1 * num2;
            //
            String resultPrint = removeDecimalZeros(resultValue);
            //
            int dotPos = resultPrint.indexOf(".");
            if (dotPos > 0
                    && (resultPrint.length() - dotPos) > 3) {
                resultPrint = resultPrint.substring(0, resultPrint.indexOf(".") + 3) + "e";
            }
            //
            tvNum1.setText(resultPrint);
            tvNum2.setText("");
            isEqual = true;
        }
    }

    public String removeDecimalZeros(double dblValue) {
        Log.d("d", new Exception().getStackTrace()[0].getMethodName());
        if (dblValue == (long) dblValue)
            return String.format("%d", (long) dblValue);
        else
            return String.format("%s", dblValue);
    }
}
