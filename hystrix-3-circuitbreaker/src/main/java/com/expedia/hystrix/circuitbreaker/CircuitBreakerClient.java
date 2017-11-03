package com.expedia.hystrix.circuitbreaker;

import java.time.Duration;
import java.time.Instant;

import com.expedia.hystrix.helpers.RestClient;
import com.expedia.hystrix.helpers.WebContainer;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CircuitBreakerClient {

    private static final String SERVICE_PATH = "brittle";

    private static final Logger log = LoggerFactory.getLogger(CircuitBreakerClient.class);

    private static String callFailService() throws Exception {
        // TODO use Hystrix command here
        return new RemoteServiceCircuitBreakingCommand(SERVICE_PATH).execute();
        //return RestClient.operation(SERVICE_PATH);
        //throw new RuntimeException("Implement RemoteServiceTimeoutCommand as done in previous modules");
    }

    public static void main(String[] args) throws Exception {

        WebContainer server = deployWebContainer();
        for (int i = 0; i < 1000; i++) {
            try {
                Instant before = Instant.now();

                String result = callFailService();

                Instant after = Instant.now();
                long duration = Duration.between(before, after).toMillis();

                log.info(duration + "ms: " + result);
            } catch (Exception ex) {
                log.error(ex.toString());
            }
            Thread.sleep(300);
        }

        server.stop();
    }

    private static WebContainer deployWebContainer() {
        WebContainer server = new WebContainer();
        server.start();
        server.deployServlet("hystrix.stream", "HystrixMetricsStreamServlet", HystrixMetricsStreamServlet.class);
        return server;
    }

}
