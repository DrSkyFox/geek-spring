package ru.geek.lesson4springboot.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geek.lesson4springboot.controller.BadRequestException;
import ru.geek.lesson4springboot.controller.NotFoundException;
import ru.geek.lesson4springboot.service.ProductRepr;
import ru.geek.lesson4springboot.service.ProductService;
import ru.geek.lesson4springboot.service.UserRepr;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping(path = "/all", produces = "application/json")
    public List<ProductRepr> findAll() {
        return productService.findAll();
    }

    @GetMapping(path = "/{id}")
    public ProductRepr findById(@PathVariable("id") Long id) {
        ProductRepr productRepr = productService.findById(id)
                .orElseThrow(NotFoundException::new);
        return productRepr;
    }

    @PostMapping(consumes = "application/json")
    public ProductRepr create(@RequestBody ProductRepr productRepr) {
        if(productRepr.getId() != null) {
            throw new BadRequestException();
        }
        productService.save(productRepr);
        return productRepr;
    }

    @PutMapping(consumes = "application/json")
    public void update(@RequestBody ProductRepr productRepr) {
        if(productRepr.getId() == null) {
            throw new BadRequestException();
        }
        productService.save(productRepr);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(NotFoundException ex) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> badRequestException(BadRequestException ex) {
        return new ResponseEntity<>("Bad request exception", HttpStatus.NOT_FOUND);
    }

}
