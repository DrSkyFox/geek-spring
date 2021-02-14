package ru.geek.persists;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;



public class UserRepository {

    private EntityManager manager;

    public UserRepository(EntityManagerFactory factory) {
        manager = factory.createEntityManager();
    }

    public List<User> findAll() {
        return manager.createQuery("from User u", User.class).getResultList();
    }

    public User findById(long id) {
        return manager.find(User.class, id);
    }


    public void userSaveOrUpdate(User user) {
        if(user.getId() != null) {
            update(user);
        } else {
            insert(user);
        }
    }

    @Transactional
    private void insert(User user) {
        if(user != null) {
            manager.persist(user);
        }
    }
    @Transactional
    private void update(User user) {
        if(user != null) {
            manager.merge(user);
        }
    }
    @Transactional
    public void delete(long id) {
        User user;
        manager.getTransaction().begin();
        if((user = manager.find(User.class, id)) != null)
        manager.remove(user);
        manager.getTransaction().commit();
    }

}
