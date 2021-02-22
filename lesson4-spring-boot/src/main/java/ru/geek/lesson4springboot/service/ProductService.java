package ru.geek.lesson4springboot.service;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductRepr> findAll();


    Optional<ProductRepr> findById(long id);

    void save(ProductRepr product);


    void delete(long id);
}
