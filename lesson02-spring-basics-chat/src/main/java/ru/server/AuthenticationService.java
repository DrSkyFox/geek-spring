package ru.server;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;



@Service
public class AuthenticationService {

    Logger  logger = Logger.getLogger(this.getClass().getName());


    public Client logIN(String login, String pass) {
        Connection connection = null;
        try {
            connection = connection = ConnectionFactory.getInstance();
            PreparedStatement statement = connection.prepareStatement("select id, login, password, nickname from users as u where login = ? and password = ?");
            statement.setString(1,login);
            statement.setString(2,pass);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                logger.info(String.format("Connected client info : id - %s, login - %s, password  - %s , nickname - %s",
                        resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3),resultSet.getString(4)));
                System.out.println(resultSet.getString(1));
                return new Client(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong during DB-query");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }


    public String changeNickName(ClientHandler client ,String newName) {
        Connection connection = null;
        try {
            connection = connection = ConnectionFactory.getInstance();
            logger.info(String.format("NewName is : %s , id_client is : %s",newName ,client.getiD_Client()));
            PreparedStatement statement = connection.prepareStatement("update users set nickname = ? where id = ?");
            statement.setString(1, newName);
            statement.setInt(2, client.getiD_Client());
            logger.info("Going execute");
            int count = statement.executeUpdate();
            logger.info(String.format("Count result %s",count));
            if(count > 0) {
                return newName;
            }
           return null;
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong during DB-query");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }


    public boolean regNew(String clogin, String cpass, String cnickname) {
        Connection connection = null;
        try {
            connection = connection = ConnectionFactory.getInstance();
            PreparedStatement statement = connection.prepareStatement("insert into users (login, password, nickname) values (clogin, cpass, cnickname");
            statement.setString(1,clogin);
            statement.setString(2,cpass);
            statement.setString(3,cnickname);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong during DB-query");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public AuthenticationService() {

    }


    static public class Client {
        private int iD;
        private String login;
        private String password;
        private String name;

        public Client(int iD, String login, String password, String name) {
            this.login = login;
            this.password = password;
            this.name = name;
            this.iD = iD;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public int getiD() {
            return iD;
        }
    }
}
