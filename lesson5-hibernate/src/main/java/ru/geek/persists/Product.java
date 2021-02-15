package ru.geek.persists;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(length = 8196)
    private String description;

    @Column(length = 12, nullable = false)
    private Integer cost;

    @ManyToMany
    @JoinTable(name = "order_contents",
    joinColumns = @JoinColumn(name = "productid"),
    inverseJoinColumns = @JoinColumn(name = "orderid"))
    private List<OrderContent> orderContent;



    public Product(String name, String description, Integer cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }



    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public List<OrderContent> getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(List<OrderContent> orderContent) {
        this.orderContent = orderContent;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
