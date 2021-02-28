package ru.geek.lesson4springboot.service;

import ru.geek.lesson4springboot.persist.Cart;
import ru.geek.lesson4springboot.persist.CartItem;
import ru.geek.lesson4springboot.persist.User;

import java.util.List;

public interface CartService {

    Cart getCartOrCreate(Long userId);

    Cart addToCart(Long userId, Long productId, Integer quantity);


    Cart clearCart(Long userId);
}
