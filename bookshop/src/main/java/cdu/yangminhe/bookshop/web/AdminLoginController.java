package cdu.yangminhe.bookshop.web;

import cdu.yangminhe.bookshop.pojo.User;
import cdu.yangminhe.bookshop.util.Result;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping("/api")
public class AdminLoginController {
    @PostMapping("admin/login")
    public Object AdminLogin(@RequestBody User user){
        if (!Objects.equals("admin", user.getName()) ||
                !Objects.equals("123456", user.getPassword())){
            String message = "账号或密码错误";
            return Result.fail(message);
        }else{
            return Result.success();
        }
    }
}
