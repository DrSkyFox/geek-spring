package ru.geek.repository;

import ru.geek.persists.Orders;
import ru.geek.repository.interfaces.DAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class OrderRepository extends MyRepository implements DAOInterface<Orders> {
    public OrderRepository(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public List<Orders> findAll() {
        return executeForEntityManager(entityManager ->
                entityManager.createQuery("from Orders", Orders.class).getResultList());
    }

    @Override
    public Orders findById(long id) {
        return executeForEntityManager(entityManager -> entityManager.find(Orders.class, id));
    }

    @Override
    public void saveOrUpdate(Orders orders) {
        if(orders == null) {
            return;
        }
        if(orders.getID() != null) {
            update(orders);
        } else {
            insert(orders);
        }
    }

    private void insert(Orders orders) {
        executeInTransaction(entityManager -> {
            entityManager.persist(orders);
        });
    }

    private void update(Orders orders) {
        executeInTransaction(entityManager -> {
            entityManager.refresh(orders);
        });
    }

    @Override
    public void delete(long id) {
        executeInTransaction(entityManager -> {
            Orders orders = entityManager.find(Orders.class, id);
            if(orders != null) {
                entityManager.remove(orders);
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
