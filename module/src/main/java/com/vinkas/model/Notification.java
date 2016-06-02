package com.vinkas.model;

import android.app.NotificationManager;
import android.content.Context;

import com.vinkas.notifier.Scheduler;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Vinoth on 2-6-16.
 */
public class Notification extends RealmObject {

    private final static AtomicInteger c = new AtomicInteger(0);

    public static int generateId() {
        return c.incrementAndGet();
    }

    public Notification() {
    }

    @PrimaryKey
    private int id;
    private long timestamp;
    private int alarm_RTC_TYPE;
    private boolean scheduled;
    @Ignore
    private android.app.Notification androidNotification;

    public int getAlarm_RTC_TYPE() {
        return alarm_RTC_TYPE;
    }

    public void setAlarm_RTC_TYPE(int alarm_RTC_TYPE) {
        this.alarm_RTC_TYPE = alarm_RTC_TYPE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public android.app.Notification getAndroidNotification() {
        return androidNotification;
    }

    public void setAndroidNotification(android.app.Notification androidNotification) {
        this.androidNotification = androidNotification;
    }

    public void cancel() {
        NotificationManager manager = (NotificationManager) getScheduler().getAndroidContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(getId());
    }

    protected Scheduler getScheduler() {
        return Scheduler.getInstance();
    }

    public void schedule() {
        getScheduler().schedule(getId(), getAndroidNotification(), getTimestamp(), getAlarm_RTC_TYPE());
    }

    public void unschedule() {
        cancel();
        getScheduler().unschedule(getId(), getAndroidNotification());
    }

    public Boolean scheduleIfNotExist() {
        if (isScheduled())
            return false;
        schedule();
        return true;
    }

}