package com.expedia.hystrix.circuitbreaker;

import com.expedia.hystrix.helpers.RestClient;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by asinghal on 10/31/17.
 */
public class RemoteServiceCircuitBreakingCommand extends HystrixCommand<String>
{

    private final String name;

    public RemoteServiceCircuitBreakingCommand(String name)
    {
        super(HystrixCommandGroupKey.Factory.asKey("RemoteServiceCircuitBreakingCommand"));
        this.name = name;
    }

    @Override protected String run() throws Exception
    {
        return RestClient.operation(name);
    }

    @Override protected String getFallback()
    {
        return "Safe catch - This is the fallback path!";
    }
}
