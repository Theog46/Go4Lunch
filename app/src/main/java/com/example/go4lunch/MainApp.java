package com.example.go4lunch;

import android.app.Application;

public class MainApp extends Application {

    private static Application sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

    }

    public static Application getApp() {
        return sApp;
    }
}
