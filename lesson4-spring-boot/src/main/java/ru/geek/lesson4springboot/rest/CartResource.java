package ru.geek.lesson4springboot.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geek.lesson4springboot.controller.BadRequestException;
import ru.geek.lesson4springboot.controller.NotFoundException;
import ru.geek.lesson4springboot.persist.Cart;
import ru.geek.lesson4springboot.persist.LineItem;
import ru.geek.lesson4springboot.service.CartService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/cart")
public class CartResource {

    private final CartService cartService;

    @Autowired
    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{userId}/user/{productId}/product")
    public void addProductForUser(@PathVariable("userId") Long userId,
                                  @PathVariable("productId") Long productId,
                                  @RequestParam("qty") Integer qty) {
        cartService.addProductForUserQty(productId, userId, qty);
    }

    @GetMapping("/{userId}/user")
    public List<LineItem> findItemsForUser(@PathVariable("userId") Long userId) {
        return cartService.findAllItemsForUser(userId);
    }

    @PostMapping("/remove/{userId}/user/{productId}/product")
    public void removeProductForUser(@PathVariable("userId") Long userId,
                                     @PathVariable("productId") Long productId,
                                     @RequestParam("qty") Integer qty) {
        cartService.removeProductForUser(productId, userId, qty);
    }

    @DeleteMapping("/remove/{userId}/user")
    public void removeAllForUser(@PathVariable("userId") Long userId) {
        cartService.removeAllForUser(userId);
    }



}
