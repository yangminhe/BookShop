package cdu.yangminhe.bookshop.web;

import cdu.yangminhe.bookshop.pojo.Category;
import cdu.yangminhe.bookshop.pojo.Product;
import cdu.yangminhe.bookshop.pojo.Review;
import cdu.yangminhe.bookshop.pojo.User;
import cdu.yangminhe.bookshop.service.CategoryService;
import cdu.yangminhe.bookshop.service.ProductService;
import cdu.yangminhe.bookshop.service.ReviewService;
import cdu.yangminhe.bookshop.service.UserService;
import cdu.yangminhe.bookshop.util.AliYunOssUtil;
import cdu.yangminhe.bookshop.util.Result;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {
    final
    ReviewService reviewService;
    final
    AliYunOssUtil aliYunOssUtil;
    final
    ProductService productService;
    final
    CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService, AliYunOssUtil aliYunOssUtil, ReviewService reviewService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.aliYunOssUtil = aliYunOssUtil;
        this.reviewService = reviewService;
    }

    @GetMapping("products")
    public Page<Product> getList(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                  @RequestParam(value = "page-size", defaultValue = "5") int pageSize) throws Exception{
        Page<Product> page = productService.getList(pageNum-1,pageSize);
        return page;
    }

    /**
     * 查询某一分类下的所有商品
     * @param cid 分类id
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("categories/{id}/products")
    public Page<Product> getList(@PathVariable("id") int cid,
                                  @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                  @RequestParam(value = "page-size", defaultValue = "5") int pageSize) throws Exception{
        Page<Product> page = productService.getList(cid,pageNum-1,pageSize);
        return page;
    }


    @PostMapping("products")
    public Object add(Product form, MultipartFile file,@RequestParam("cid") int cid) throws Exception {
        String url = aliYunOssUtil.upload(file);
        form.setImage(url);
        Category c = categoryService.get(cid);
        form.setCategory(c);
        productService.add(form);
        return form;
    }

    @DeleteMapping("products/{id}")
    public String delete(@PathVariable("id") int id)  throws Exception {
        productService.delete(id);
        return null;
    }

    @PutMapping("products/{id}")
    public Object update(Product form,MultipartFile file) throws Exception {
        if(file != null) {
            String url = aliYunOssUtil.upload(file);
            form.setImage(url);
        }
        productService.update(form);
        return form;
    }

    @GetMapping("product/{id}")
    public Object product(@PathVariable("id") int id,HttpSession session) {
        Product product = productService.get(id);
        List<Review> reviews = reviewService.list(product);
        productService.setReviewNumber(product);
        User u = (User)session.getAttribute("user");
    
        Map<String,Object> map= new HashMap<>();
        map.put("product", product);
        map.put("reviews", reviews);
        map.put("user", u);
        return Result.success(map);
    }

    @PostMapping("review/add")
    public Object product(Review review, @RequestParam("pid") int pid, HttpSession session) throws Exception {
        // System.out.print(review);
        Product p = productService.get(pid);
        User u = (User)session.getAttribute("user");
        review.setCreateTime(new Date());
        review.setProduct(p);
        review.setUser(u);
        reviewService.add(review);
        // System.out.print(review);
        return Result.success(review);
    }
    
}
