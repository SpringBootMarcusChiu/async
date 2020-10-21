package com.marcuschiu.asyncexample;

import com.marcuschiu.asyncexample.service.MyAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Future;

/**
 * Created by marcus.chiu on 10/1/16.
 * @SpringBootApplication - a convenience annotation that adds all the following:
 *     1. @Configuration - tags the class as a source of bean definitions
 *     2. @EnableAutoConfiguration - tells Spring Boot to start adding beans
 *          based on classpath settings, other beans, and various property settings
 *     3. @EnableWebMvc - normally added for a Spring MVC app, but Spring boot adds
 *          it automatically when it sees 'spring-webmvc' on the classpath.
 *          This flags application as a web application and activates key behaviors
 *          like setting up DispatcherServlet
 *     4. @ComponentScan - tells Spring to look for other components, configurations,
 *          and services in the package this class belongs to, allowing it to find the controllers
 */
@SpringBootApplication
public class AsyncExampleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AsyncExampleApplication.class, args);
	}

	@Autowired
	MyAsyncService myAsyncService;

	@Override
	public void run(String... strings) throws Exception {
		myAsyncService.asyncMethodWithVoidReturnType();

		System.out.println("Invoking an asynchronous method. " + Thread.currentThread().getName());
		Future<String> future = myAsyncService.asyncMethodWithReturnType();

		while (true) {
			if (future.isDone()) {
				System.out.println("Result from asynchronous process - " + future.get());
				break;
			}
			System.out.println("Continue doing something else. ");
			Thread.sleep(1000);
		}

		initiateShutdown(0);
	}

	@Autowired
	private ApplicationContext appContext;

	private void initiateShutdown(int returnCode){
		SpringApplication.exit(appContext, () -> returnCode);
	}
}
