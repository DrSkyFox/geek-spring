package ru.geek.lesson4springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geek.lesson4springboot.persist.Product;
import ru.geek.lesson4springboot.persist.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream().map(ProductRepr::new).collect(Collectors.toList());
    }

    @Override
    public ProductRepr findById(long id) {
        Product product = productRepository.findById(id);
        if(product != null) {
            return new ProductRepr(product);
        }
        return null;
    }
    @Transactional
    @Override
    public void insert(ProductRepr product) {
        productRepository.insert(new Product(product));
    }
    @Transactional
    @Override
    public void update(ProductRepr product) {
        productRepository.update(new Product(product));
    }
    @Transactional
    @Override
    public void delete(long id) {
        productRepository.delete(id);
    }
}
