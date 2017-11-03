package com.expedia.hystrix.commands;

import java.util.concurrent.TimeUnit;

public class RemoteService {

    public static String operation() throws Exception {
        randomSleep();
        if (isFail()) {
            throw new RuntimeException("service failure");
        }
        return "success";
    }

    private static final int MAX_WAITING_MILLISECONDS = 1000;

    private static void randomSleep() throws InterruptedException {
        long waitingTime = Math.round(Math.random() * MAX_WAITING_MILLISECONDS);
        TimeUnit.MILLISECONDS.sleep(waitingTime);
    }

    private static final double FAILURE_RATE = 0.5;

    private static boolean isFail() {
        return Math.round(Math.random()) < FAILURE_RATE;
    }


}
