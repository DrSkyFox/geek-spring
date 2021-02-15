package ru.geek.persists;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable = false)
    private Integer customerID;

    @OneToMany(mappedBy = "orders")
    private List<OrderContent> orderContents;

    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;

    public Orders() {
    }

    public Orders(Integer customerID) {
        this.customerID = customerID;
    }

    public Orders(Customer customer) {
        this.customer = customer;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }



    public List<OrderContent> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(List<OrderContent> orderContents) {
        this.orderContents = orderContents;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "ID=" + ID +
                ", customerID=" + customerID +
                '}';
    }
}
