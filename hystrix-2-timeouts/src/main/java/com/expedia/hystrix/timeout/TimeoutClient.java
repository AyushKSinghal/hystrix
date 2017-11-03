/*
 * Copyright 2017 livthomas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expedia.hystrix.timeout;

import java.time.Duration;
import java.time.Instant;

import com.expedia.hystrix.helpers.RestClient;
import com.expedia.hystrix.helpers.WebContainer;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeoutClient {

    private static final String SERVICE_PATH = "delay";

    private static final Logger log = LoggerFactory.getLogger(TimeoutClient.class);

    private static String callTimeoutService() throws Exception {
        // TODO use Hystrix command here
        return new RemoteServiceTimeoutCommand(SERVICE_PATH).execute();
        //return RestClient.operation(SERVICE_PATH);
        //throw new RuntimeException("Implement RemoteServiceTimeoutCommand as done in module 1");
    }

    public static void main(String[] args) {

        //Test UnderTow: http://localhost:9090/hystrix.stream
        WebContainer server = deployWebContainer();

        for (int i = 0; i < 300; i++) {
            try {
                Instant before = Instant.now();

                String result = callTimeoutService();

                Instant after = Instant.now();
                long duration = Duration.between(before, after).toMillis();

                log.info(duration + "ms: " + result);
            } catch (Exception ex) {
                log.error(ex.toString());
            }
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
