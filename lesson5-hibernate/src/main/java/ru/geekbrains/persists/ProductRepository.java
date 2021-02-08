package ru.geekbrains.persists;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class ProductRepository {

    private EntityManager manager;

    public ProductRepository(EntityManagerFactory factory) {
        manager = factory.createEntityManager();
    }


    public List<Product> findAll() {
        return manager.createQuery("from products", Product.class).getResultList();
    }

    public Product findById(long id) {
        return manager.find(Product.class, id);
    }

    public void productSaveOrUpdate(Product product) {
        manager.getTransaction().begin();
        if(product.getId() != null) {
            update(product);
        } else {
            insert(product);
        }
        manager.getTransaction().commit();
    }


    public void insert(Product product) {
        if(product !=null) {
            manager.persist(product);
        }
    }

    public void update(Product product) {
        if(product !=null) {
            manager.merge(product);
        }
    }
    public void delete(long id) {
        manager.getTransaction().begin();
        Product product = manager.find(Product.class, id);
        if(product != null) {
            manager.remove(product);
        }
        manager.getTransaction().commit();
    }

    public void close() {
        manager.close();
    }

}
