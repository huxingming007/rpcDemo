package com.gupaoedu.rpc;

import com.gupaoedu.rpc.discovery.IServiceDiscovery;
import com.gupaoedu.rpc.loadbalance.LoanBalance;
import com.gupaoedu.vip.IHelloService;
import com.gupaoedu.vip.IPaymentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        /*RpcProxyClient rpcProxyClient=new RpcProxyClient();

        IHelloService iHelloService=rpcProxyClient.clientProxy
                (IHelloService.class,"localhost",8080);

        String result=iHelloService.sayHello("Mic");
        System.out.println(result);*/

        ApplicationContext context = new
                AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);
        LoanBalance bean = context.getBean(LoanBalance.class);
        context.getBean(IServiceDiscovery.class);
        IHelloService iHelloService = rpcProxyClient.clientProxy
                (IHelloService.class, "v2.0");

//        IPaymentService iPaymentService=rpcProxyClient.clientProxy(IPaymentService.class,
//                "localhost",8080);

        String aaaa = iHelloService.sayHello("aaaa");
        System.out.println(aaaa);
    }
}
