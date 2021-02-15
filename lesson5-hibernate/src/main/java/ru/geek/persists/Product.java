package ru.geek.persists;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal cost;

    @Column(length = 13, nullable = false)
    private Integer qty;

    @Column(length = 9)
    private String color;

    @OneToMany(mappedBy = "product")
    private List<LineItem> lineItems;




    public Product(String name, String description, BigDecimal cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public Product(String name, String description, BigDecimal cost, Integer qty, String color) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.qty = qty;
        this.color = color;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
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
