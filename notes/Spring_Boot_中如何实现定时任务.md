# Spring Boot 中如何实现定时任务

我们使用 @Scheduled 注解就能很方便地创建一个定时任务。    

单纯依靠 @Scheduled 注解 还不行，我们还需要在 SpringBoot 中我们只需要在启动类上加上@EnableScheduling 注解，这样才可以启动定时任务。@EnableScheduling 注解的作用是发现注解 @Scheduled 的任务并在后台执行该任务。

```
@Component 
public class ScheduledTasks { 
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

     /** * fixedRate：固定速率执行。每5秒执行一次。 */ 
    @Scheduled(fixedRate = 5000) public void reportCurrentTimeWithFixedRate() {
         log.info("Current Thread : {}", Thread.currentThread().getName()); 
         log.info("Fixed Rate Task : The time is now {}", dateFormat.format(new Date()));
 } 
}
```
