package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persists.Product;
import ru.geekbrains.persists.ProductRepository;

import javax.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =  new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        ProductRepository repository = new ProductRepository(entityManagerFactory);



//        System.out.println("Insert data into DB");

//        repository.productSaveOrUpdate(new Product("Lays", "Chips", 80));
//        repository.productSaveOrUpdate(new Product("J7", "Juice", 120));
//        repository.productSaveOrUpdate(new Product("Qudracopter Maverick", "Toy", 80000));

        System.out.println("Find all products");
        System.out.println(repository.findAll());

        System.out.println("Change costs of product");
        Product product = repository.findById(2l);
        product.setCost(230);
        repository.productSaveOrUpdate(product);

        System.out.println("Find all products after change");
        System.out.println(repository.findAll());

        System.out.println("Remove product from DB");
        repository.delete(3l);

        System.out.println("Find all products after delete");
        System.out.println(repository.findAll());

        repository.close();

    }
}

//

//INSERT
//        entityManager.getTransaction().begin();
//        User user = new User("user2", "password2", "email12@email.cc");
//        User user1 = new User("user3", "password3", "3@email.cc");
//        User user2 = new User("user4", "password4", "e42@email.cc");
//        entityManager.persist(user);
//        entityManager.persist(user1);
//        entityManager.persist(user2);
//        entityManager.getTransaction().commit();
//        entityManager.close();

//        //SELECT
//        User user = entityManager.find(User.class, 1L);
//        System.out.println(user);
//
//        //HQL, JOQL язык запросов
//        System.out.println("All users");
//        List<User> userList = entityManager.createQuery("from User", User.class).getResultList();
//        System.out.println(userList);
//
//        System.out.println("Only user3");
//        Object user3 = entityManager.createQuery("from User u where u.username = :username")
//                .setParameter("username", "user3")
//                .getSingleResult();
//        System.out.println(user3);
//
//        //SQL
//        userList = entityManager.createNativeQuery("select * from users", User.class)
//                .getResultList();
//        System.out.println(userList);
//
//        entityManager.createNamedQuery("userByName")
//                .setParameter("username", "user2")
//                .getSingleResult();
//UPDATE
//        User user = entityManager.find(User.class, 1l);
//        entityManager.getTransaction().begin();
//        user.setPassword("asdasdsadsadasd");
//        entityManager.getTransaction().commit();
//
//        user = entityManager.find(User.class, 1l);
//        System.out.println(user);
//        entityManager.close();

//DELETE
//        entityManager.getTransaction().begin();
//        entityManager.remove(Objects.requireNonNull(entityManager.find(User.class, 2l)));
//        entityManager.getTransaction().commit();
//
//        //jpql
//        entityManager.createQuery("delete from User where username=:username")
//                .setParameter("username", "user2")
//                .executeUpdate();
//
//        System.out.println(Arrays.asList(entityManager.createQuery("from users", User.class).getResultList()));
//
//        entityManager.close();