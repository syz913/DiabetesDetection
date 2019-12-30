package com.example.diabetesdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {
    private TextView risk, suggest;
    private int totalNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_report);

        calculate();
    }

    public void calculate() {
        risk = findViewById(R.id.risk);
        suggest = findViewById(R.id.suggest);
        if (UserData.age >= 45 && UserData.age < 54) totalNum += 2;
        else if (UserData.age >= 54 && UserData.age < 64) totalNum += 3;
        else if (UserData.age >= 64) totalNum += 4;
        if (UserData.BMI >= 25 && UserData.BMI < 30) totalNum += 1;
        else if (UserData.BMI >= 30) totalNum += 3;
        if (UserData.gender.equals("男")) {
            if (UserData.waistline >= 94 && UserData.waistline < 102) totalNum += 3;
            else if (UserData.waistline >= 102) totalNum += 4;
        } else if (UserData.gender.equals("女")) {
            if (UserData.waistline >= 80 && UserData.waistline < 88) totalNum += 3;
            else if (UserData.waistline >= 88) totalNum += 4;
        }
        if (UserData.fruit_frequency < 3) totalNum += 1;
        if (UserData.exercise_frequency < 3) totalNum += 2;
        if (UserData.case_history) totalNum += 5;
        if (UserData.family_history) totalNum += 5;
        if (UserData.SBP >= 120 && UserData.SBP < 130) totalNum += 3;
        else if (UserData.SBP >= 130 && UserData.SBP < 150) totalNum += 5;
        else if (UserData.SBP >= 150) totalNum += 8;

        System.out.println(totalNum);
        if(totalNum < 12){
            risk.setText("1%");
            suggest.setText(UserData.username + ", 您患糖尿病的风险极低");
        } else if(totalNum >= 12 && totalNum < 16){
            risk.setText("4%");
            suggest.setText(UserData.username + ", 您患糖尿病的风险较低");
        } else if(totalNum >= 16 && totalNum < 20){
            risk.setText("16.7%");
            suggest.setText(UserData.username + ", 您患糖尿病的风险位于平均水平");
        } else if(totalNum >= 20 && totalNum < 25){
            risk.setText("33.3%");
            suggest.setText(UserData.username + ", 您患糖尿病的风险较高");
        } else if(totalNum >= 25 && totalNum < 30){
            risk.setText("50%");
            suggest.setText(UserData.username + ", 您患糖尿病的风险高");
        } else if(totalNum >= 30){
            risk.setText("80%");
            suggest.setText(UserData.username + ", 您患糖尿病的风险极高");
        }

    }
}
