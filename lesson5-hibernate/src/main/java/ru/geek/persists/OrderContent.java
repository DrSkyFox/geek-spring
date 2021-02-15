package ru.geek.persists;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_contents")
public class OrderContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable = false)
    private Integer orderID;

    @Column(nullable = true)
    private Integer productID;

    @Column(nullable = true)
    private Integer currentCost;


    @ManyToOne
    @JoinColumn(name = "orderid")
    private Orders orders;

    @ManyToMany
    @JoinTable(name = "order_contents",
    joinColumns = @JoinColumn(name = "orderid"),
    inverseJoinColumns = @JoinColumn(name = "productid"))
    private List<Product> products;


    public OrderContent(Integer orderID, Integer productID, Integer currentCost) {
        this.productID = productID;
        this.currentCost = currentCost;
    }

    public OrderContent(Orders orders, List<Product> products) {
        this.orders = orders;
        this.products = products;
    }

    public OrderContent() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(Integer currentCost) {
        this.currentCost = currentCost;
    }

    public Orders getOrder() {
        return orders;
    }

    public void setOrder(Orders orders) {
        this.orders = orders;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderContent{" +
                "ID=" + ID +
                ", orderID=" + orderID +
                ", productID=" + productID +
                ", currentCost=" + currentCost +
                '}';
    }
}
