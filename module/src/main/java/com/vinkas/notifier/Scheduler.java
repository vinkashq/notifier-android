package com.vinkas.notifier;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Vinoth on 10-5-16.
 */
public class Scheduler {

    private static Scheduler scheduler = new Scheduler();
    private static Context androidContext;

    public static Scheduler getInstance() {
        return scheduler;
    }

    public static void setAndroidContext(Context context) {
        androidContext = context;
    }

    public Scheduler() {
        alarmManager = (AlarmManager) getAndroidContext().getSystemService(Context.ALARM_SERVICE);
    }

    private AlarmManager alarmManager;
    private Notification.Builder notificationBuilder;

    public Notification.Builder getNotificationBuilder() {
        if (notificationBuilder == null)
            notificationBuilder = new Notification.Builder(getAndroidContext());
        return  notificationBuilder;
    }

    protected AlarmManager getAlarmManager() {
        return alarmManager;
    }

    public Context getAndroidContext() {
        return androidContext;
    }

    public void schedule(int notificationId, Notification notification, long timestamp, int rtcType) {
        Intent publisherIntent = unschedule(notificationId, notification);
        PendingIntent updateCurrent = getPendingIntent(notificationId, publisherIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        getAlarmManager().set(rtcType, timestamp, updateCurrent);
    }

    public Intent unschedule(int notificationId, Notification notification) {
        Intent publisherIntent = getPublisherIntent(notificationId, notification);
        PendingIntent noCreate = getPendingIntent(notificationId, publisherIntent, PendingIntent.FLAG_NO_CREATE);
        if (noCreate != null) {
            noCreate.cancel();
            getAlarmManager().cancel(noCreate);
        }
        return publisherIntent;
    }

    protected Intent getPublisherIntent(int notificationId, Notification notification) {
        return Publisher.createInstance(getAndroidContext(), notificationId, notification);
    }

    protected PendingIntent getPendingIntent(int requestCode, Intent publisherIntent, int FLAG) {
        return PendingIntent.getBroadcast(getAndroidContext(), requestCode, publisherIntent, FLAG);
    }

}
