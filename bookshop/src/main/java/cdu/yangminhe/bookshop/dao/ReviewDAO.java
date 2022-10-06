package cdu.yangminhe.bookshop.dao;

import cdu.yangminhe.bookshop.pojo.Product;
import cdu.yangminhe.bookshop.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDAO extends JpaRepository<Review,Integer> {
    List<Review> findByProductOrderByIdDesc(Product product);
    int countByProduct(Product product);
}
