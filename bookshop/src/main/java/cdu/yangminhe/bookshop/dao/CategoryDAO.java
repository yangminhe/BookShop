package cdu.yangminhe.bookshop.dao;

import cdu.yangminhe.bookshop.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category,Integer> {
}
