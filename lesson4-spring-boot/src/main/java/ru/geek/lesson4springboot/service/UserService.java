package ru.geek.lesson4springboot.service;

import ru.geek.lesson4springboot.persist.User;
import java.util.List;
import java.util.Optional;

public interface UserService {


    List<UserRepr> findAll();

    Optional<UserRepr> findById(long id);

    void save(UserRepr user);


    void delete(long id);

}

