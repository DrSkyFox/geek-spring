package ru.geek.lesson4springboot.persist;

public class CartItem {


    private Cart cart;
    private Product product;
    private Integer quantity;

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSumCost() {
        return quantity*product.getCost();
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cart=" + cart +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
