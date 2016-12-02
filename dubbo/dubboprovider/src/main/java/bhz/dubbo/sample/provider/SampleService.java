package bhz.dubbo.sample.provider;

import java.util.List;

public interface SampleService {

    String sayHello(String name);

    List getUsers();

}