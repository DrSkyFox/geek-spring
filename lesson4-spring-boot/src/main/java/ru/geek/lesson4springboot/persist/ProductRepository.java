package ru.geek.lesson4springboot.persist;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {

    private EntityManager em;

    public List<Product> findAll() {
        return em.createQuery("from Product", Product.class).getResultList();
    }

    public Product findById(long id) {
        return em.find(Product.class, id);
    }

    @Transactional
    public void insert(Product product) {
        em.persist(product);
    }

    @Transactional
    public void update(Product product) {
        em.refresh(product);
    }

    @Transactional
    public void delete(long id) {
        em.createQuery("delete from Product where id = :id").setParameter("id", id).executeUpdate();
    }

}
