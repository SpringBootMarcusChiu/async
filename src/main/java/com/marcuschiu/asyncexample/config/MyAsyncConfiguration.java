package com.marcuschiu.asyncexample.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @EnableAsync - enables use of @Async
 */
@EnableAsync
@Configuration
public class MyAsyncConfiguration implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }

    @Bean(name = "myThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor() {
            protected BlockingQueue<Runnable> createQueue(int queueCapacity) {
                return new PriorityBlockingQueue<>(queueCapacity);
            }
        };

        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(1000);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(5);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setTaskDecorator(new LoggingTaskDecorator());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("MyAsyncConfigurer-");
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
