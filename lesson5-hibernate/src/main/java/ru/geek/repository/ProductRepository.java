package ru.geek.repository;


import ru.geek.persists.Product;
import ru.geek.repository.interfaces.DAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public class ProductRepository extends MyRepository implements DAOInterface<Product> {

    public ProductRepository(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public List<Product> findAll() {
        return executeForEntityManager(entityManager ->
                entityManager.createQuery("from Product p", Product.class).getResultList());
    }
    @Override
    public Product findById(long id) {
        return executeForEntityManager(entityManager -> entityManager.find(Product.class, id));
    }


    @Override
    public void saveOrUpdate(Product product) {
        if(product == null) {
            return;
        }
        if(product.getId() != null) {
            update(product);
        } else {
            insert(product);
        }
    }

    private void insert(Product product) {
        executeInTransaction(entityManager -> {
            entityManager.persist(product);
        });
    }

    private void update(Product product) {
        executeInTransaction(entityManager -> {
            entityManager.refresh(product);
        });
    }
    @Override
    public void delete(long id) {
        executeInTransaction(entityManager -> {
            Product product = entityManager.find(Product.class, id);
            if(product != null) {
                entityManager.remove(product);
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
