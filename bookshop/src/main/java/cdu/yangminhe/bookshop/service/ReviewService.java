package cdu.yangminhe.bookshop.service;

import cdu.yangminhe.bookshop.dao.ReviewDAO;
import cdu.yangminhe.bookshop.pojo.Product;
import cdu.yangminhe.bookshop.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    final
    ReviewDAO reviewDAO;
    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public void add(Review review) {
        reviewDAO.save(review);
    }

    public List<Review> list(Product product){
        List<Review> result =  reviewDAO.findByProductOrderByIdDesc(product);
        return result;
    }

    public int getCount(Product product) {
        return reviewDAO.countByProduct(product);
    }

 
}
