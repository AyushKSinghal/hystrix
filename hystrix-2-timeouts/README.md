Hystrix timeout
===============

1. Open module hystrix-servers and familiarize with [CrankyService](https://ewegithub.sb.karmalab.net/mmaheshwari/hystrix-workshop/blob/master/hystrix-servers/src/main/java/com/expedia/servers/CrankyService.java)

2. Run ServersApplication springboot application

3. Create RemoteServiceTimeoutCommand as we did in last module

4. Use RemoteServiceTimeoutCommand at TimeoutClient->callRemoteService()
```
new RemoteServiceTimeoutCommand(SERVICE_PATH).execute();
```
5. Go to resources -> [config.properties](src/main/resources/config.properties), and uncomment key hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds

6. Run TimeoutClient main function, this time you should not see fallbacks

7. Go to resources -> [config.properties](src/main/resources/config.properties), reduce value of key hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds from 2000 to 500

8. Run TimeoutClient main function, in console observe error

9. Open Hystrix Dashboard:
[http://localhost:8080/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A9090%2Fhystrix.stream&title=Test](http://localhost:8080/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A9090%2Fhystrix.stream&title=Test)

Note: Reload the above page just after running TimeoutClient main function in case you don't see dashboard updating

![Hystrix](https://github.com/Netflix/Hystrix/wiki/images/dashboard-annoted-circuit-640.png "Hystrix")

Reading Material:

* [Hystrix Dashboard](https://github.com/Netflix/Hystrix/wiki/Dashboard)
* [Explore Command Name, Command Group, and Command Thread-Pool](https://github.com/Netflix/Hystrix/wiki/How-To-Use#CommandName)

Note: We will cover dashboard in next part, if you don't understand much don't worry
