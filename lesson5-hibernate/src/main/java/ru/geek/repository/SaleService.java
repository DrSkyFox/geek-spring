package ru.geek.repository;

import ru.geek.persists.Customer;
import ru.geek.persists.LineItem;
import ru.geek.persists.Product;
import ru.geek.persists.User;
import ru.geek.repository.interfaces.DAOInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sound.sampled.Line;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class SaleService extends MyRepository implements DAOInterface<LineItem> {

    public SaleService(EntityManagerFactory factory) {
        super(factory);
    }

    @Override
    public List<LineItem> findAll() {
        return executeForEntityManager(entityManager ->
                entityManager.createQuery("from LineItem c", LineItem.class).getResultList());
    }

    @Override
    public LineItem findById(long id) {
        return executeForEntityManager(entityManager -> entityManager.find(LineItem.class, id));
    }

    @Override
    public void saveOrUpdate(LineItem lineItem) {
        if(lineItem == null) {
            return;
        }
        if(lineItem.getId() != null) {
            update(lineItem);
        } else {
            insert(lineItem);
        }
    }

    private void insert(LineItem lineItem) {
        executeInTransaction(entityManager -> {
            entityManager.persist(lineItem);
        });
    }

    private void update(LineItem lineItem) {
        executeInTransaction(entityManager -> {
            entityManager.refresh(lineItem);
        });
    }

    public void sellProducts(Customer customer, List<Product> products) {

        List<LineItem> lineItems = new ArrayList<>();

        for (Product product: products
             ) {
//            Customer user, Product product, BigDecimal price, Integer qty, String color
            lineItems.add(new LineItem(customer, product, product.getCost(), product.getQty(), product.getColor()));
        }
        executeInTransaction(entityManager -> {
            lineItems.forEach(entityManager::persist);
        });
    }

    public List<Product> getBoughtProductByCustomer(long id) {
        Customer customer = executeForEntityManager(entityManager -> entityManager.find(Customer.class, id));
        List<Product> products = new ArrayList<>();
        for (LineItem lineItem: customer.getLineItems()
             ) {
            products.add(lineItem.getProduct());
        }
        return products;
    }

    public List<Customer> getCutomersBoughtProductById(long id) {
        Product product = executeForEntityManager(entityManager -> entityManager.find(Product.class, id));
        List<Customer> customers = new ArrayList<>();
        for (LineItem lineItem: product.getLineItems()
        ) {
            customers.add(lineItem.getUser());
        }
        return customers;
    }


    @Override
    public void delete(long id) {
        executeInTransaction(entityManager -> {
            LineItem  lineItem= entityManager.find(LineItem.class, id);
            if(lineItem != null) {
                entityManager.remove(lineItem);
            }
        });
    }



    @Override
    protected <R> R executeForEntityManager(Function<EntityManager, R> function) {
        return super.executeForEntityManager(function);
    }

    @Override
    protected void executeInTransaction(Consumer<EntityManager> consumer) {
        super.executeInTransaction(consumer);
    }
}
