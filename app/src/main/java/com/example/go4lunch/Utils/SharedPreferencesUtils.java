package com.example.go4lunch.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    Context mContext;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SharedPreferencesUtils(Context context) {
        mContext = context;
    }

    public SharedPreferences getSharedPreferences() {
        if (preferences == null) {
            preferences = mContext.getSharedPreferences("settings", Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public SharedPreferences.Editor getEditor() {
        if (editor == null) {
            editor = mContext.getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
        }
        return editor;
    }

}
