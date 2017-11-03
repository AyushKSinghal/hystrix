## FAQ

### I am not able to see hystrix dashboard?
Reload dashboard page, if you don't see graph after that as well. Check following:
* Whether test is running? If not, start it
* Http server is running? If not, start it
* If there are error in Http server console, restart hystrix-servers->ServersApplication and run test 

### How does Hystrix dashboard work?
Timeout and Circuit breaker modules are streaming hystrix logs to http://localhost:9090. Check it by opening http://localhost:9090/hystrix.stream in chrome or safari. It is implemented by using [Timeout->WebContainer](hystrix-2-timeouts/src/main/java/com/expedia/hystrix/helpers/WebContainer.java)/ [CircuitBreaker->WebContainer](hystrix-3-circuitbreaker/src/main/java/com/expedia/hystrix/helpers/WebContainer.java). 
hystrix-servers enabled hystrix dashboard by using annotation @EnableHystrixDashboard. You can access dashboard UI at http://localhost:8080/hystrix and provide http://localhost:9090/hystrix.stream as input

![https://github.com/Netflix/Hystrix/wiki/images/dashboard-direct-vs-turbine-640.png](https://github.com/Netflix/Hystrix/wiki/images/dashboard-direct-vs-turbine-640.png)

### Hystrix flow chart
![Hystrix flow chart](https://github.com/Netflix/Hystrix/wiki/images/hystrix-command-flow-chart.png)

### Can dashboard has more than one graph?
Yes it can have more than one graph. It really depends on number of hystrix commands. Try adding two commands and check dashboard.
