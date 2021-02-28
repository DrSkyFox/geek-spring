package ru.geek.lesson4springboot.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geek.lesson4springboot.controller.BadRequestException;
import ru.geek.lesson4springboot.controller.NotFoundException;
import ru.geek.lesson4springboot.persist.Cart;
import ru.geek.lesson4springboot.service.CartService;
import ru.geek.lesson4springboot.service.ProductRepr;

@RestController
@RequestMapping("/api/v1/cart")
public class CartResource {

    private final CartService cartService;

    @Autowired
    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Get cart")
    @GetMapping("/{id}")
    public Cart getCard(@PathVariable("id") Long id) {
        return cartService.getCartOrCreate(id);
    }

    @Operation(summary = "Add item to cart")
    @PostMapping("/additem")
    Cart addToCart(@RequestParam("id") Long userId, @RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity)  {
        return cartService.addToCart(userId, productId, quantity);
    }


    @Operation(summary = "Clear cart")
    @DeleteMapping("/clear")
    Cart clearCart(@RequestParam("id") Long userId) {
        return cartService.clearCart(userId);
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
