package bhz.dubbo.sample.provider.impl;

import bhz.dubbo.sample.provider.SampleService;

import java.util.ArrayList;
import java.util.List;

public class SampleServiceImpl implements SampleService {

    public String sayHello(String name) {
        return "Hello " + name;
    }

    public List getUsers() {
        List list = new ArrayList();
        User u1 = new User();
        u1.setName("jack");
        u1.setAge(20);
        u1.setSex("m");

        User u2 = new User();
        u2.setName("tom");
        u2.setAge(21);
        u2.setSex("m");

        User u3 = new User();
        u3.setName("rose");
        u3.setAge(19);
        u3.setSex("w");

        list.add(u1);
        list.add(u2);
        list.add(u3);
        return list;
    }
}
