package ru.geek;

import org.hibernate.cfg.Configuration;
import ru.geek.persists.Customer;
import ru.geek.persists.OrderContent;
import ru.geek.persists.Orders;
import ru.geek.persists.Product;
import ru.geek.repository.CustomerRepository;
import ru.geek.repository.OrderContentRepository;
import ru.geek.repository.OrderRepository;
import ru.geek.repository.ProductRepository;
import ru.geek.repository.interfaces.DAOInterface;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;


public class Main06 {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =  new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        DAOInterface<Product> productRepository = new ProductRepository(entityManagerFactory);
        DAOInterface<Orders> ordersRep = new OrderRepository(entityManagerFactory);
        DAOInterface<Customer> customerRepository = new CustomerRepository(entityManagerFactory);
        DAOInterface<OrderContent> orderContentDAOInterface = new OrderContentRepository(entityManagerFactory);

//        product_init(productRepository);
//        cutomer_init(customerRepository);


        System.out.println("Products");
        for (Product product: productRepository.findAll()
             ) {
            System.out.println(product);
        }
        System.out.println("Customers");
        for (Customer customer: customerRepository.findAll()
             ) {
            System.out.println(customer);
        }
        //create order


//        ordersRep.saveOrUpdate(new Orders(customerRepository.findById(3)));
//
//        List<OrderContent> contentList = new ArrayList<>();
//
//        List<Product> products = new ArrayList<>();
//
//        products.add(productRepository.findById(2));
//        products.add(productRepository.findById(4));
//        products.add(productRepository.findById(2));
//        products.add(productRepository.findById(6));
//
//        contentList.add(new OrderContent(ordersRep.findById(1), products ));
//
//
//
//        orderContentDAOInterface.saveOrUpdate(new OrderContent(ordersRep.findById(1), products));

        productRepository.findById(2).getOrderContent().forEach(orderContent -> System.out.println(orderContent.getOrder().getCustomerID()));





    }

    private static void product_init(DAOInterface<Product> productRepository) {
        try {
            System.out.println("Insert data into DB");
            productRepository.saveOrUpdate(new Product("Lays", "Chips", 80));
            productRepository.saveOrUpdate(new Product("J7", "Juice", 120));
            productRepository.saveOrUpdate(new Product("Qudracopter Maverick", "Toy", 80000));
            productRepository.saveOrUpdate(new Product("Harry Potter and Azbakan", "Book", 600));
            productRepository.saveOrUpdate(new Product("Geforce 1080ti", "videocard", 110000));
            productRepository.saveOrUpdate(new Product("Asus ROG", "sticker", 8));
            productRepository.saveOrUpdate(new Product("Riot Points 1000", "Game Valute", 1000));
            productRepository.saveOrUpdate(new Product("Clock", "clock", 140));
            productRepository.saveOrUpdate(new Product("Tea", "products", 50));
            productRepository.saveOrUpdate(new Product("Windows 10", "Software", 7800));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cutomer_init(DAOInterface<Customer> customerRepository) {
        try {
            customerRepository.saveOrUpdate(new Customer("Stefan"));
            customerRepository.saveOrUpdate(new Customer("Albert"));
            customerRepository.saveOrUpdate(new Customer("Ella"));
            customerRepository.saveOrUpdate(new Customer("Judy"));
            customerRepository.saveOrUpdate(new Customer("Gilbert"));
            customerRepository.saveOrUpdate(new Customer("Mari"));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
