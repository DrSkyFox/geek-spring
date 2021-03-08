package ru.geek.lesson4springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geek.lesson4springboot.persist.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
