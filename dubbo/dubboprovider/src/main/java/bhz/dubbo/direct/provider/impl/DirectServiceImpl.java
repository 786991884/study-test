package bhz.dubbo.direct.provider.impl;

import bhz.dubbo.direct.provider.DirectService;

public class DirectServiceImpl implements DirectService {

    public String direct() throws Exception {
        return "Direct Service";
    }

}
