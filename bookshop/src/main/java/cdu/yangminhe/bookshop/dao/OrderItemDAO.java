package cdu.yangminhe.bookshop.dao;

import cdu.yangminhe.bookshop.pojo.Order;
import cdu.yangminhe.bookshop.pojo.OrderItem;
import cdu.yangminhe.bookshop.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDAO extends JpaRepository<OrderItem,Integer> {
    List<OrderItem> findByOrderOrderByIdDesc(Order order);
    List<OrderItem> findByUserAndOrderIsNull(User user);
}
