package com.example.diabetesdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name, gender, height, weight;
    private TextView textView, bmi;
    private Button button, BMI_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        button = (Button) findViewById(R.id.sure);
        button.setOnClickListener(this);

        BMI_btn = (Button) findViewById(R.id.BMI_btn);
        BMI_btn.setOnClickListener(this);

        name = (EditText) findViewById(R.id.name);
        gender = (EditText) findViewById(R.id.gender);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        bmi = (TextView) findViewById(R.id.BMI);
        textView = (TextView) findViewById(R.id.text);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sure:
                if (name.getText().toString().length() == 0) {
                    Toast.makeText(this, "用户名不能为空！！！", Toast.LENGTH_SHORT).show();
                } else if (gender.getText().toString().length() == 0) {
                    Toast.makeText(this, "性别不能为空！！！", Toast.LENGTH_SHORT).show();
                }else if (weight.getText().toString().length() == 0 || height.getText().toString().length() == 0) {
                    Toast.makeText(this, "身高体重不能为空！！！", Toast.LENGTH_SHORT).show();
                } else {
                    UserData.username = name.getText().toString();
                    UserData.gender = gender.getText().toString();
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.BMI_btn:
                try {
                    float w = Float.parseFloat(weight.getText().toString()),
                            h = Float.parseFloat(height.getText().toString());
                    bmi.setText(w / (h * h) + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
