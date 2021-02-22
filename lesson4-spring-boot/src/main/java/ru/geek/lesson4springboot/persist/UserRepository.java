package ru.geek.lesson4springboot.persist;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public List<User> findAll() {
        return em.createQuery("from User", User.class).getResultList();
    }

    public User findById(long id) {
        return em.find(User.class, id);
    }


    public void insert(User user) {
        em.persist(user);
    }


    public void update(User user) {
        em.refresh(user);
    }


    public void delete(long id) {
        em.createQuery("delete fron User where id = :id").setParameter("id", id).executeUpdate();
    }

}
