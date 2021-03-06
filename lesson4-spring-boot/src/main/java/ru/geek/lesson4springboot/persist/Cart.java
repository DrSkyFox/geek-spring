package ru.geek.lesson4springboot.persist;

import java.util.List;

public class Cart {

    private Long id;
    private Long userId;
    private List<LineItem> itemList;


    public Cart(Long userId) {
        this.userId = userId;
    }

    public Cart() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<LineItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<LineItem> itemList) {
        this.itemList = itemList;
    }
}
