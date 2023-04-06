package cn.zhanx.project05.utils;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendMessage {
    /**
     * 异步发送消息方法
     * 在Spring中，基于@Async注解的方法，称之为异步方法；这些方法将在执行的时候，
     * 将会在独立的线程中被执行，调用者无需等待它的完成，即可继续其他的操作
     */
    @Async
    public void send(int message){
        String currentThreadName = Thread.currentThread().getName();
        System.out.println(currentThreadName + "======" + message);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}