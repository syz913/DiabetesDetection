package com.example.diabetesdetection;

import android.app.Application;
import android.graphics.Bitmap;


public final class UserData extends Application {
    public static String username;
    public static String gender;
    public static int age;
    public static float height;
    public static float weight;
    public static float BMI;
    public static float waistline;
    public static int fruit_frequency;
    public static int exercise_frequency;
    public static float SBP;
    public static boolean case_history;
    public static boolean family_history;


    static {
       username = "";
       gender = "";
       height = 0;
       weight = 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

