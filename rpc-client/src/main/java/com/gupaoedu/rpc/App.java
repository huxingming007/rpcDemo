package com.gupaoedu.rpc;

import com.gupaoedu.rpc.discovery.IServiceDiscovery;
import com.gupaoedu.rpc.loadbalance.LoanBalance;
import com.gupaoedu.vip.IHelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        ApplicationContext context = new
                AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);
        LoanBalance bean = context.getBean(LoanBalance.class);
        context.getBean(IServiceDiscovery.class);
        IHelloService iHelloService = rpcProxyClient.clientProxy
                (IHelloService.class, "v2.0");
        for (int i = 0; i < 100; i++) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            System.out.println(iHelloService.sayHello("aaaa"));
        }

    }
}
