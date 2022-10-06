package cdu.yangminhe.bookshop.dao;

import cdu.yangminhe.bookshop.pojo.Order;
import cdu.yangminhe.bookshop.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order,Integer> {
    public List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String status);
}
