package ru.geek.lesson4springboot.service;

import ru.geek.lesson4springboot.persist.User;
import java.util.List;

public interface UserService {


    List<UserRepr> findAll();

    UserRepr findById(long id);

    void insert(UserRepr user);

    void update(UserRepr user);

    void delete(long id);

}

