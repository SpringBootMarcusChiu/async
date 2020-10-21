package com.marcuschiu.asyncexample.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class MyAsyncService {

    /**
     * @Async - spawns a new thread to execute the method body.
     * the threads are created and managed by a bean named "myThreadPoolTaskExecutor"
     */
    @Async("myThreadPoolTaskExecutor")
    public void asyncMethodWithVoidReturnType() {
        System.out.println("Execute method with Executor - " + Thread.currentThread().getName());
    }

    @Async
    public Future<String> asyncMethodWithReturnType() {
        System.out.println("Execute method asynchronously - "
                + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
            return new AsyncResult<>("hello world !!!!");
        } catch (InterruptedException e) {
            //
        }

        return null;
    }
}
