package com.gupaoedu.rpc.loadbalance;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @author huxingming
 * @date 2019/8/20-1:57 PM
 * @Description TODO
 */
@Component
public class RandomLoanBalance extends AbstractLoanBalance {

    @Override
    public String doSelect(List<String> serviceNames) {

        int size = serviceNames.size();
        Random random = new Random();
        return serviceNames.get(random.nextInt(size));
    }
}
