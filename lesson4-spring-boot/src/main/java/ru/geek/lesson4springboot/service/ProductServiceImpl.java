package ru.geek.lesson4springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.geek.lesson4springboot.persist.Product;
import ru.geek.lesson4springboot.persist.ProductRepository;
import ru.geek.lesson4springboot.specifications.ProductSpecification;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

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
    public Page<ProductRepr> findWithFilter(String productFilter, Double minCost, Double maxCost, Integer page, Integer size, String sortField) {

        logger.info("paramets in: productfilter {}; minCost {}; maxCost {}; page {}; size {}; sortField {}", productFilter, minCost, maxCost, page,size,sortField);

        Specification<Product> specification = Specification.where(null);
        if(productFilter != null && !productFilter.isBlank()) {
            specification =specification.and(ProductSpecification.productLike(productFilter));
        }
        if(minCost != null) {
            specification = specification.and(ProductSpecification.minCost(minCost));
        }
        if(maxCost != null) {
            specification = specification.and(ProductSpecification.maxCost(maxCost));
        }

        return productRepository.findAll(specification, PageRequest.of(page,size, Sort.by(Sort.Direction.ASC, sortField))).map(ProductRepr::new);
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
