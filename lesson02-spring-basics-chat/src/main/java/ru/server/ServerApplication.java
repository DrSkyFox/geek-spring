package ru.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Server server = context.getBean("server", Server.class);
    }
}
