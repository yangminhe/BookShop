package cdu.yangminhe.bookshop.dao;

import cdu.yangminhe.bookshop.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Integer> {
    User getByName(String name);
    User getByNameAndPassword(String name, String password);
}
