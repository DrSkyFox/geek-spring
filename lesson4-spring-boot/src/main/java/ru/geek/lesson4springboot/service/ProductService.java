package ru.geek.lesson4springboot.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductRepr> findAll();

    Optional<ProductRepr> findById(long id);

    void save(ProductRepr product);

    Page<ProductRepr> findWithFilter(String productFilter, Double minCost, Double maxCost, Integer page, Integer size, String sortField);

    void delete(long id);
}
