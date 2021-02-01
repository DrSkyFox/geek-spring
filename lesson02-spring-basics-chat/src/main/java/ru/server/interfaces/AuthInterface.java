package ru.server.interfaces;

public interface  AuthInterface {
    boolean logIN(String login, String pass);
    boolean changeNickName(String login,String newName);
    boolean regNew(String login, String pass, String nickname);

}
