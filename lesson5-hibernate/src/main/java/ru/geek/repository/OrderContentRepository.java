package ru.geek.repository;

import ru.geek.persists.OrderContent;
import ru.geek.repository.interfaces.DAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class OrderContentRepository extends MyRepository implements DAOInterface<OrderContent> {
    public OrderContentRepository(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public List<OrderContent> findAll() {
        return executeForEntityManager(entityManager ->
                entityManager.createQuery("from OrderContent oc", OrderContent.class).getResultList());
    }

    @Override
    public OrderContent findById(long id) {
        return executeForEntityManager(entityManager -> entityManager.find(OrderContent.class, id));
    }

    @Override
    public void saveOrUpdate(OrderContent orderContent) {
        if(orderContent == null) {
            return;
        }
        if(orderContent.getID() != null) {
            update(orderContent);
        } else {
            insert(orderContent);
        }
    }

    private void insert(OrderContent orderContent) {
        executeInTransaction(entityManager -> {
            entityManager.persist(orderContent);
        });
    }

    private void update(OrderContent orderContent) {
        executeInTransaction(entityManager -> {
            entityManager.refresh(orderContent);
        });
    }


    @Override
    public void delete(long id) {
        executeInTransaction(entityManager -> {
            OrderContent orderContent = entityManager.find(OrderContent.class, id);
            if(orderContent != null) {
                entityManager.remove(orderContent);
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
