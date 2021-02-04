package ru.chatserver;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.chatserver.auth.AuthService;
import ru.chatserver.auth.AuthServiceJdbcImpl;
import ru.chatserver.persists.UserRepository;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan("ru.chatserver")
public class AppConfig {

//    Более гибкий вариант конфигурации
//    Можно создать метод Connection

    @Bean
    public ChatServer chatServer() throws SQLException {
        System.out.println("Bean chatServer execute");
        return new ChatServer(authServiceJdbc());
    }

    @Bean
    public AuthServiceJdbcImpl authServiceJdbc() throws SQLException {
        System.out.println("Bean AuthServiceJDBC execute");
        return new AuthServiceJdbcImpl(userRepository());
    }

    @Bean
    public UserRepository userRepository() throws SQLException {
        System.out.println("Bean userRepository execute");
        return new UserRepository(dataSource());
    }


    @Bean
    public DataSource dataSource() {
        System.out.println("Bean dataSource execute");
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUsername("postgres");
        ds.setPassword("root");
        ds.setUrl("jdbc:postgresql://127.0.0.1:5432/postgres?currentSchema=chat_database");
        return ds;
    }

//    Bean chatServer execute
//    Bean AuthServiceJDBC execute
//    Bean userRepository execute
//    Bean dataSource execute
//    Server started!
}
