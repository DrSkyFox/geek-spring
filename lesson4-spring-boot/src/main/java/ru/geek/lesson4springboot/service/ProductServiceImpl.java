package ru.geek.lesson4springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geek.lesson4springboot.persist.Product;
import ru.geek.lesson4springboot.persist.ProductRepository;

import java.util.List;
import java.util.Optional;
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
    public Optional<ProductRepr> findById(long id) {
        return productRepository.findById(id).map(ProductRepr::new);
    }

    @Override
    public List<ProductRepr> findWithFilter(String productFilter, Double minCost, Double maxCost) {
        return productRepository.findWithFilter(productFilter, minCost, maxCost).stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void save(ProductRepr product) {
        productRepository.save(new Product(product));
    }


    @Transactional
    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }

}
