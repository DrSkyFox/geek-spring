package ru.geek.lesson4springboot.service;

import org.springframework.data.domain.Page;
import ru.geek.lesson4springboot.persist.User;
import java.util.List;
import java.util.Optional;

public interface UserService {


    List<UserRepr> findAll();

    Optional<UserRepr> findById(long id);

    void save(UserRepr user);

    List<UserRepr> findWithFilter(String usernameFilter);

    Page<UserRepr> findWithFilter(String usernameFilter, Integer minAge, Integer maxAge,
                                  Integer page, Integer size, String sortField);

    void delete(long id);

}

