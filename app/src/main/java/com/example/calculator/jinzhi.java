package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class jinzhi extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jinzhi);

        textView = findViewById(R.id.textView1);
        editText = findViewById(R.id.editText1);
        spinner = findViewById(R.id.spinner3);

        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String s = spinner.getSelectedItem().toString();
        String str = editText.getText().toString();

        if (str != null && str.length() > 0) {
            int input = Integer.parseInt(str);

            if (s.equals("二进制")) {
                textView.setText(Integer.toBinaryString(input));
            } else if (s.equals("八进制")) {
                textView.setText(Integer.toOctalString(input));
            } else if (s.equals("请选择进制")) {
                textView.setText("未选择进制转换");
            }
            else
                textView.setText(Integer.toHexString(input));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}