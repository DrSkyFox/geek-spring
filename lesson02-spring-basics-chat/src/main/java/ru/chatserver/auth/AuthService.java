package ru.chatserver.auth;

import ru.chatserver.persists.User;

public interface AuthService {

    boolean authUser(User user);
}
