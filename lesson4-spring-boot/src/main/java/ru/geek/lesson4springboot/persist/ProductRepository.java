package ru.geek.lesson4springboot.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findProductByNameLike(String productName);


    @Query("select p from Product p " +
            "where (p.name like :productname or :productname is null) and " +
            "      (p.cost >= :minCost or :minCost is null) and " +
            "      (p.cost <= :maxCost or :maxCost is null)")
    List<Product> findWithFilter(@Param("productname") String productFilter,
                                 @Param("minCost") Double minCost,
                                 @Param("maxCost") Double maxCost);
}
