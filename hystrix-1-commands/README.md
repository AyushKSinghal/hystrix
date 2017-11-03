Hystrix command
================

1. Read about how to create hello world command here: [https://github.com/Netflix/Hystrix/wiki/How-To-Use#Hello-World](https://github.com/Netflix/Hystrix/wiki/How-To-Use#Hello-World)

```
//This class wraps the request you are making to the dependency. 
//Define the constructor with arguments that will be needed when the request is made.
public class CommandHelloWorld extends HystrixCommand<String> {

    //Remote operation arguments are setup here.
    private final String name;

    public CommandHelloWorld(String name) {
    
        //Hystrix uses the command group key to group together commands such as for reporting, alerting, dashboards, or team/library ownership. 
        //By default Hystrix uses this to define the command thread-pool unless a separate one is defined. 
        //HystrixCommandGroupKey is an interface and can be implemented as an enum or regular class, 
        //but it also has the helper Factory class to construct and intern instances such as: 
        //HystrixCommandGroupKey.Factory.asKey("ExampleGroup")
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        // a real example would do work like a network call here
        return "Hello " + name + "!";
    }
}
```

2. Familiarize with
    * [RemoteService.java](src/main/java/com/expedia/hystrix/commands/RemoteService.java)
    * [SyncClient](src/main/java/com/expedia/hystrix/commands/SyncClient.java) & [AsyncClient](src/main/java/com/expedia/hystrix/commands/AsynClient.java)

3. Create Remote service hystrix command, please find sample code below

```
class RemoteServiceCommand extends HystrixCommand<String> {

    private final String name;
    
    public RemoteServiceCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("RemoteServiceCommand"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return RemoteService.operation();
    }
}
```

##   
##### Read before next step: [Synchronous Execution](https://github.com/Netflix/Hystrix/wiki/How-To-Use#Synchronous-Execution)
4. Update SyncClient -> callRemoteService() as follows:
```
new RemoteServiceCommand("World").execute();
```

5. Run SyncClient.

##   
##### Read before next step: [Asynchronous Execution](https://github.com/Netflix/Hystrix/wiki/How-To-Use#Asynchronous-Execution)
6. Use Asynchronous execution at AsynClient -> callRemoteServiceAsynchronously(), and run AsyncClient.
```
new RemoteServiceCommand("World").queue();
```

7. Check console and observe errors

8. Change fallback configuration: Go to resources -> [config.properties](src/main/resources/config.properties), and change value of hystrix.command.default.fallback.enabled to true. Read comments for better understanding.


9. Add a fallback path for Remote service command, by adding below to RemoteServiceCommand, and run SyncClient again.
```
    @Override
    protected String getFallback() {
        return "Safe catch - This is the fallback path!";
    }
```

### Reading Material:
* Basic fallback [https://github.com/Netflix/Hystrix/wiki/How-To-Use#Fallback](https://github.com/Netflix/Hystrix/wiki/How-To-Use#Fallback)
* Fallback patterns [https://github.com/Netflix/Hystrix/wiki/How-To-Use#Common-Patterns-FallbackStatic] (https://github.com/Netflix/Hystrix/wiki/How-To-Use#Common-Patterns-FallbackStatic)
* [Explore Command Name, Command Group, and Command Thread-Pool](https://github.com/Netflix/Hystrix/wiki/How-To-Use#CommandName)
