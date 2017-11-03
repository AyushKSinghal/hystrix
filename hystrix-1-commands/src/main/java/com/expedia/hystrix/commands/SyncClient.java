package com.expedia.hystrix.commands;

import java.time.Duration;
import java.time.Instant;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncClient {

    private static final Logger log = LoggerFactory.getLogger(SyncClient.class);

    private static String callRemoteService() throws Exception {
        //return new RemoteServiceCommand("remoteCall").execute();
        // TODO execute Hystrix command synchronously here
        return new RemoteServiceCommand("World").execute();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try {
                Instant before = Instant.now();

                String result = callRemoteService();

                Instant after = Instant.now();
                long duration = Duration.between(before, after).toMillis();

                log.info(duration + "ms: " + result);
            } catch (Exception ex) {
                log.error(ex.toString());
            }
        }
    }
}
