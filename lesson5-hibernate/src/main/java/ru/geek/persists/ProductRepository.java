package ru.geek.persists;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class ProductRepository {

    private EntityManager manager;

    public ProductRepository(EntityManagerFactory factory) {
        manager = factory.createEntityManager();
    }


    public List<Product> findAll() {
        List<Product>  products = manager.createQuery("from products p", Product.class).getResultList();
        return products;
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


    private void insert(Product product) {
        if(product !=null) {
            manager.persist(product);
        }
    }

    private void update(Product product) {
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
