package ru.geek.lesson4springboot.service;

import java.util.List;

public interface ProductService {

    List<ProductRepr> findAll();

    ProductRepr findById(long id);

    void insert(ProductRepr product);

    void update(ProductRepr product);

    void delete(long id);
}
