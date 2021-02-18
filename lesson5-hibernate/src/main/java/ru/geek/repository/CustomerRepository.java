package ru.geek.repository;

import ru.geek.persists.Customer;
import ru.geek.repository.interfaces.DAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomerRepository extends MyRepository implements DAOInterface<Customer> {

    public CustomerRepository(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public List<Customer> findAll() {
        return executeForEntityManager(entityManager ->
                entityManager.createQuery("from Customer c", Customer.class).getResultList());
    }

    @Override
    public Customer findById(long id) {
        return executeForEntityManager(entityManager -> entityManager.find(Customer.class, id));
    }

    @Override
    public void saveOrUpdate(Customer customer) {
        if(customer == null) {
            return;
        }
        if(customer.getId() != null) {
            update(customer);
        } else {
            insert(customer);
        }
    }

    private void insert(Customer customer) {
        executeInTransaction(entityManager -> {
            entityManager.persist(customer);
        });
    }

    private void update(Customer  customer) {
        executeInTransaction(entityManager -> {
            entityManager.refresh(customer);
        });
    }

    @Override
    public void delete(long id) {
        executeInTransaction(entityManager -> {
            Customer customer = entityManager.find(Customer.class, id);
            if(customer != null) {
                entityManager.remove(customer);
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
