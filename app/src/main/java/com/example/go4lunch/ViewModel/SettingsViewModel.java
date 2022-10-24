package com.example.go4lunch.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.work.WorkManager;

import com.example.go4lunch.Repository.SettingsRepository;
import com.example.go4lunch.Utils.NotificationWorker;

public class SettingsViewModel extends ViewModel {

    private SettingsRepository settingsRepository;

    public SettingsViewModel(
            SettingsRepository settingsRepository
    ) {
        this.settingsRepository = settingsRepository;
    }

    public boolean areNotificationsAllowed(Context context) {
        return settingsRepository.areNotificationsAllowed(context);
    }

    public void changeNotificationState(Context context, boolean state) {
        settingsRepository.changeNotificationState(context, state);
        if (state) {
            NotificationWorker.Request();
        } else {
            deleteNotification(context);
        }
    }

    public void deleteNotification(Context context) {
        WorkManager.getInstance(context).cancelAllWorkByTag("NOTIF");
    }
}
