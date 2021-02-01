package ru.server;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan("ru.server")
public class AppConfig {

    @Bean
    public AuthenticationService authenticationService() {
        return new AuthenticationService();
    }

    @Bean
    public Server server() {
        return new Server(executorService(),authenticationService());
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }



}
