package com.expedia.hystrix.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RequestExecutor {

    private static final long TIMEOUT = 5; // minutes

    private static final Logger log = LoggerFactory.getLogger(RequestExecutor.class);

    private final Set<Callable<String>> executables = new HashSet<>();

    private final int executionsCount;
    private final long waitingTime;

    public RequestExecutor(int executionsCount, long waitingTime) {
        this.executionsCount = executionsCount;
        this.waitingTime = waitingTime;
    }

    public void add(Callable<String> executable) {
        executables.add(executable);
    }

    public void execute() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(executables.size());
        for (Callable<String> executable : executables) {
            executorService.execute(new SafeRunnable(executable));
        }
        executorService.shutdown();
        executorService.awaitTermination(TIMEOUT, TimeUnit.MINUTES);
    }

    private class SafeRunnable implements Runnable {

        private final Callable<String> callable;

        public SafeRunnable(Callable<String> callable) {
            this.callable = callable;
        }

        @Override
        public void run() {
            for (int i = 0; i < executionsCount; i++) {
                try {
                    Instant before = Instant.now();

                    String result = callable.call();

                    Instant after = Instant.now();
                    long duration = Duration.between(before, after).toMillis();

                    log.info("{} ms: {}", duration, result);
                } catch (Exception ex) {
                    log.error(ex.toString());
                }
                try {
                    Thread.sleep(waitingTime);
                } catch (InterruptedException ex) {
                    // ignore
                }
            }
        }
    }
}
