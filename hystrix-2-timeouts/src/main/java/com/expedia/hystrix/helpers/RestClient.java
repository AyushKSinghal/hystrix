package com.expedia.hystrix.helpers;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class RestClient {

    private static final String SERVER_URL = "http://localhost:8080/";

    public static String operation(String servicePath) throws Exception {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(SERVER_URL).path(servicePath);
        return target.request().get(String.class);
    }
}
