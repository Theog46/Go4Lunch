package com.example.go4lunch.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.go4lunch.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class NotificationWorker extends Worker {




    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        displayNotif();
        return Result.success();
    }

    public static void Request() {

        Calendar now = Calendar.getInstance();
        Calendar notifTime = Calendar.getInstance();
        notifTime.set(Calendar.HOUR_OF_DAY, 12);
        notifTime.set(Calendar.MINUTE, 0);
        notifTime.set(Calendar.SECOND, 3);



        if (notifTime.before(now)) {
            notifTime.add(Calendar.HOUR_OF_DAY, 24);
        }
        long timeDifference = notifTime.getTimeInMillis() - now.getTimeInMillis();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay( timeDifference, TimeUnit.MILLISECONDS)
                .addTag("NOTIF")
                .setConstraints(setCons())
                .build();

        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }

    public static Constraints setCons() {
        Constraints constraints = new Constraints.Builder()
                .build();
        return constraints;
    }

    public void displayNotif() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "go4lunch", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("go4lunch");
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        SharedPreferences preferences = new SharedPreferencesUtils(getApplicationContext()).getSharedPreferences();

        CharSequence contentTitle = getApplicationContext().getString(R.string.lunch_remember);

        NotificationCompat.Builder compat = new NotificationCompat.Builder(getApplicationContext(), "1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(contentTitle)
                .setContentText(preferences.getString("Lunch", ""))
                .setStyle(new NotificationCompat.BigTextStyle())
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(1, compat.build());

    }




}
