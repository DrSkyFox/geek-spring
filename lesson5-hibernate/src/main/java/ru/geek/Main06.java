package ru.geek;

import org.hibernate.cfg.Configuration;
import ru.geek.persists.Customer;
import ru.geek.persists.Product;
import ru.geek.repository.CustomerRepository;
import ru.geek.repository.ProductRepository;
import ru.geek.repository.SaleService;
import ru.geek.repository.interfaces.DAOInterface;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Main06 {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        DAOInterface<Product> productRepository = new ProductRepository(entityManagerFactory);
        DAOInterface<Customer> customerRepository = new CustomerRepository(entityManagerFactory);


//        productRepository.saveOrUpdate(new Product("test","test",new BigDecimal(100), 123213, "red"));
//        productRepository.saveOrUpdate(new Product("test2","test",new BigDecimal(200),  123, "green"));
//        productRepository.saveOrUpdate(new Product("test3","test",new BigDecimal(300),  1254643, "blue"));
//        productRepository.saveOrUpdate(new Product("test4","test",new BigDecimal(400),  193, "black"));
//
//        customerRepository.saveOrUpdate(new Customer("Alfred"));
//        customerRepository.saveOrUpdate(new Customer("Mary"));
//        customerRepository.saveOrUpdate(new Customer("Santa"));
//        customerRepository.saveOrUpdate(new Customer("Clint"));
//
        SaleService saleService = new SaleService(entityManagerFactory);
//
//        List<Product> productList = new ArrayList<>();
//        productList.add(productRepository.findById(2));
//        productList.add(productRepository.findById(1));
//
//        saleService.sellProducts(customerRepository.findById(3), productList);


        System.out.println(saleService.getBoughtProductByCustomer(3));

        System.out.println(saleService.getCutomersBoughtProductById(2));


    }
}
