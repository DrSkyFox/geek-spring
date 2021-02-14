package ru.geek.persist;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {

    private Map<Long, Product> productMap = new ConcurrentHashMap<>();

    private AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        this.insert(new Product("apple", "fruit", 100.0));
        this.insert(new Product("orange", "fruit", 120.0));
        this.insert(new Product("Guitar", "instrument music", 100000.0));
    }

    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    public Product findById(long id) {
        return productMap.get(id);
    }

    public void insert(Product product) {
        long id = identity.incrementAndGet();
        product.setId(id);
        productMap.put(id, product);
    }

    public void update(Product product) {
        productMap.put(product.getId(), product);
    }

    public void delete(long id) {
        productMap.remove(id);
    }

}
