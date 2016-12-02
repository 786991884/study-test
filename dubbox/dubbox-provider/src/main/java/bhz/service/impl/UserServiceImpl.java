package bhz.service.impl;

import bhz.entity.User;
import bhz.service.UserService;
import org.springframework.stereotype.Service;


//这里是spring的注解
@Service("userService")
//这个是dubbo的注解（同时提供dubbo本地，和rest方式）
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = bhz.service.UserService.class, protocol = {"rest", "dubbo"}, retries = 0)
public class UserServiceImpl implements UserService {

    public void testget() {
        //http://localhost:8888/provider/userService/testget
        System.out.println("测试...get");
    }


    public User getUser() {
        User user = new User();
        user.setId("1001");
        user.setName("张三");
        return user;
    }


    public User getUser(Integer id) {
        System.out.println("测试传入int类型的id: " + id);
        User user = new User();
        user.setId("1001");
        user.setName("张三");
        return user;
    }


    public User getUser(Integer id, String name) {

        System.out.println("测试俩个参数：");
        System.out.println("id: " + id);
        System.out.println("name: " + name);
        User user = new User();
        user.setId("1001");
        user.setName("张三");
        return user;
    }


    public void testpost() {
        System.out.println("测试...post");
    }


    public User postUser(User user) {
        System.out.println(user.getName());
        System.out.println("测试...postUser");
        User user1 = new User();
        user1.setId("1001");
        user1.setName("张三");
        return user1;
    }


    public User postUser(String id) {
        System.out.println(id);
        System.out.println("测试...get");
        User user = new User();
        user.setId("1001");
        user.setName("张三");
        return user;
    }


}
