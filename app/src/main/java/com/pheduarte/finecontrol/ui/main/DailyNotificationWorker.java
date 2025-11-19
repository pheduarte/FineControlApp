package com.pheduarte.finecontrol.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.pheduarte.finecontrol.R;

public class DailyNotificationWorker extends Worker {

    public DailyNotificationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {

        showNotification("Daily reminder", "Don’t forget to track your expenses today!");

        return Result.success();
    }

    private void showNotification(String title, String message) {
        Context context = getApplicationContext();
        String channelId = "daily_channel";

        NotificationManager manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Daily Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.icon_bell)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);

        manager.notify(1, builder.build());
    }
}
