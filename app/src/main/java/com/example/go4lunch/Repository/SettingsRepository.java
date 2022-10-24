package com.example.go4lunch.Repository;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsRepository {

    static SharedPreferences settings;

    public SettingsRepository() {}


    public static void changeNotificationState(Context context, Boolean state) {
        settings = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("STATE", state);
        editor.apply();
    }

    public Boolean areNotificationsAllowed(Context context) {
        settings = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        return settings.getBoolean("STATE", true);
    }
}
