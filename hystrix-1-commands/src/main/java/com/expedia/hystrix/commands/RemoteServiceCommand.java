package com.expedia.hystrix.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by asinghal on 10/31/17.
 */
class RemoteServiceCommand extends HystrixCommand<String>
{

    private final String name;

    public RemoteServiceCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("RemoteServiceCommand"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return RemoteService.operation();
    }

    @Override
    protected String getFallback() {
        return "Safe catch - This is the fallback path!";
    }
}
