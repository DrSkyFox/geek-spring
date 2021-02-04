package ru.server;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    public static Connection getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/chater?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root1");
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong during establishing DB connection",e.getCause());
        }
    }
}