package bhz.dubbo.dependency.provider.impl;

import bhz.dubbo.dependency.provider.DependencyService;
import bhz.dubbo.sample.provider.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dependencyServiceImpl")
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = bhz.dubbo.dependency.provider.DependencyService.class, protocol = {"dubbo"}, retries = 0)
public class DependencyServiceImpl implements DependencyService {

    //注入SampleService
    @Autowired
    private SampleService sampleService;

    public String dependency() throws Exception {
        //这里 我们可能需要调用SampleService，也可能不需要...
        System.out.println(sampleService.sayHello("jack"));
        return "dependency exec";
    }

}
