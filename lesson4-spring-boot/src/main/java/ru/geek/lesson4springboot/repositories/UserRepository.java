package ru.geek.lesson4springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geek.lesson4springboot.persist.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findUserByUsernameLike(String username);

    Optional<User> findAllByUsernameContains(String username);

    @Query("select u from User u where u.username like :username")
    List<User> someQuery(@Param("username") String username);
}
