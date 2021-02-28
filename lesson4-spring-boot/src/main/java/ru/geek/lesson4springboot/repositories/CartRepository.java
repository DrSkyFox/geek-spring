package ru.geek.lesson4springboot.repositories;

import org.springframework.stereotype.Repository;
import ru.geek.lesson4springboot.persist.Cart;
import ru.geek.lesson4springboot.persist.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CartRepository {

    private Map<Long, Cart> cartMap = new ConcurrentHashMap<>();

    private AtomicLong identity = new AtomicLong(0);

    private Cart findByUser(Long userId) {
        return cartMap.get(userId);
    }

    private Cart insert(Long userId) {
        long id = identity.incrementAndGet();
        Cart cart = new Cart(userId);
        cart.setId(id);
        cart.setItemList(new ArrayList<>());
        cartMap.put(userId, cart);
        return cart;
    }

    public Cart getCartOrCreate(Long userId) {
        Cart cart  = findByUser(userId);
        if (cart != null) {
            return cart;
        } else {
            return insert(userId);
        }

    }

    public void update(Cart cart) {
        cartMap.put(cart.getUserId(), cart);
    }

    public void delete(long id) {
        cartMap.remove(id);
    }



}
