package com.vinkas.notifier;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vinoth on 26-5-16.
 */
public class Notification {

    private final static AtomicInteger c = new AtomicInteger(0);

    public static int getId() {
        return c.incrementAndGet();
    }

}
