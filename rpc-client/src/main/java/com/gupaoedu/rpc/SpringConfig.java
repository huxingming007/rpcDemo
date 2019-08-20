package com.gupaoedu.rpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = "com.gupaoedu.rpc")
public class SpringConfig {

//    @Bean(name="rpcPRoxyClient")
//    public RpcProxyClient proxyClient(){
//        return new RpcProxyClient();
//    }
}
