package ru.geek.lesson4springboot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geek.lesson4springboot.controller.NotFoundException;
import ru.geek.lesson4springboot.persist.Cart;

import ru.geek.lesson4springboot.persist.CartItem;
import ru.geek.lesson4springboot.repositories.CartRepository;
import ru.geek.lesson4springboot.repositories.ProductRepository;
import ru.geek.lesson4springboot.repositories.UserRepository;


@Service
public class CartServiceImp implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Autowired
    public CartServiceImp(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Cart getCartOrCreate(Long userId) {
        return cartRepository.getCartOrCreate(userId);
    }


    @Override
    public Cart addToCart(Long userId, Long productId, Integer quantity) {
        if(checkUserExists(userId)) {
            Cart cart = cartRepository.getCartOrCreate(userId);
            cart.getItemList().add(new CartItem(productRepository.findById(productId).orElseThrow(NotFoundException::new), quantity));
            return cart;
        }
        return null;
    }

    private boolean checkUserExists(Long userId) {
        if(userRepository.findById(userId) == null) {
            throw new NotFoundException();
        }
        return true;
    }


    @Override
    public Cart clearCart(Long userId) {
        if(checkUserExists(userId)) {
            Cart cart =  cartRepository.getCartOrCreate(userId);
            cart.setItemList(null);
            return cart;
        }
        return null;
    }


}
