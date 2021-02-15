package ru.geek.repository;


import ru.geek.persists.User;
import ru.geek.repository.interfaces.DAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public class UserRepository extends MyRepository implements DAOInterface<User> {

    public UserRepository(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public List<User> findAll() {
        return executeForEntityManager(entityManager ->
                entityManager.createQuery("from User p", User.class).getResultList());
    }

    @Override
    public User findById(long id) {
        return executeForEntityManager(entityManager -> entityManager.find(User.class, id));
    }


    @Override
    public void saveOrUpdate(User user) {
        if(user == null) {
            return;
        }
        if(user.getId() != null) {
            update(user);
        } else {
            insert(user);
        }
    }


    private void insert(User user) {
        executeInTransaction(entityManager -> {
            entityManager.persist(user);
        });
    }

    private void update(User user) {
        executeInTransaction(entityManager -> {
            entityManager.refresh(user);
        });
    }

    @Override
    public void delete(long id) {
        executeInTransaction(entityManager -> {
            User user = entityManager.find(User.class, id);
            if(user != null) {
                entityManager.remove(user);
            }
        });
    }

    @Override
    protected <R> R executeForEntityManager(Function<EntityManager, R> function) {
        return super.executeForEntityManager(function);
    }

    @Override
    protected void executeInTransaction(Consumer<EntityManager> consumer) {
        super.executeInTransaction(consumer);
    }
}
