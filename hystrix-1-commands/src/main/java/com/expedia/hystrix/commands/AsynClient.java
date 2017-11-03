package com.expedia.hystrix.commands;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsynClient {

    private static final Logger log = LoggerFactory.getLogger(AsynClient.class);

    private static Future<String> callRemoteServiceAsynchronously() throws Exception {
        // TODO execute Hystrix command asynchronously here
        return null;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try {
                Instant before = Instant.now();

                Future<String> futureResult = callRemoteServiceAsynchronously();
                String result = waitForResponse(futureResult);

                Instant after = Instant.now();
                long duration = Duration.between(before, after).toMillis();

                log.info(duration + "ms: " + result);
            } catch (Exception ex) {
                log.error(ex.toString());
            }
        }
    }

    private static String waitForResponse(Future<String> futureResult) throws Exception {
        while (!futureResult.isDone()) {
            log.info("still waiting for result");
            TimeUnit.MILLISECONDS.sleep(200);
        }
        return futureResult.get();
    }
}
