# HandlerInterceptorAdapter拦截器的使用

在SpringBoot中我们可以使用HandlerInterceptorAdapter这个适配器来实现自己的拦截器。这样就可以拦截所有的请求并做相应的处理。

应用场景：

日志记录，可以记录请求信息的日志，以便进行信息监控、信息统计等。

权限检查：如登陆检测，进入处理器检测是否登陆，如果没有直接返回到登陆页面。

性能监控：典型的是慢日志。

在HandlerInterceptorAdapter中主要提供了以下的方法：

preHandle：在方法被调用前执行。在该方法中可以做类似校验的功能。如果返回true，则继续调用下一个拦截器。如果返回false，则中断执行，也就是说我们想调用的方法 不会被执行，但是你可以修改response为你想要的响应。

postHandle：在方法执行后调用。

afterCompletion：在整个请求处理完毕后进行回调，也就是说视图渲染完毕或者调用方已经拿到响应。

![79352e6efcd066c7884238fe11a6d255.png](image/79352e6efcd066c7884238fe11a6d255.png)
