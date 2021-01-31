package ru.chatserver;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ru.chatserver")
public class AppConfig {

//    Более гибкий вариант конфигурации
//    Можно создать метод Connection
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUsername("postgres");
        ds.setPassword("root");
        ds.setUrl("jdbc:postgresql://127.0.0.1:5432/postgres?currentSchema=chat_database");
        return ds;
    }
}
