package com.expedia.hystrix.timeout;

import com.expedia.hystrix.helpers.RestClient;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by asinghal on 10/31/17.
 */
public class RemoteServiceTimeoutCommand extends HystrixCommand<String>
{

    private final String name;

    public RemoteServiceTimeoutCommand(String name)
    {
        super(HystrixCommandGroupKey.Factory.asKey("RemoteServiceTimeoutCommand"));
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
