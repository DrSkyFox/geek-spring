package ru.geek.lesson4springboot.service;

import ru.geek.lesson4springboot.persist.Cart;
import ru.geek.lesson4springboot.persist.LineItem;

import java.util.List;

public interface CartService {

    void addProductForUserQty(long productId, long userId, int qty);

    void removeProductForUser(long productId, long userId, int qty);

    void removeAllForUser(long userId);

    List<LineItem> findAllItemsForUser(long userId);
}
