package com.gupaoedu.rpc.loadbalance;

import java.util.List;

/**
 * @author huxingming
 * @date 2019/8/20-1:50 PM
 * @Description TODO
 */
public abstract class AbstractLoanBalance implements LoanBalance {


    @Override
    public String select(List<String> serviceNames) {
        if (serviceNames == null || serviceNames.isEmpty()) {
            return null;
        }
        if (serviceNames.size() == 1) {
            return serviceNames.get(0);
        }

        return doSelect(serviceNames);
    }

    public abstract String doSelect(List<String> serviceNames);
}
