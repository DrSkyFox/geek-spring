package ru.chatserver.persists;

public class User {
    private Integer id;
    private String username;
    private String info;

    public User(String username, String info) {
        this.username = username;
        this.info = info;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
