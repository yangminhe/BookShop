package cdu.yangminhe.bookshop.web;

import cdu.yangminhe.bookshop.pojo.OrderItem;
import cdu.yangminhe.bookshop.pojo.User;
import cdu.yangminhe.bookshop.service.OrderItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {
    final
    OrderItemService orderItemService;
    public CartController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("cart")
    public Object cart(HttpSession session) {
        User user =(User)  session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user);
        return ois;
    }
}
