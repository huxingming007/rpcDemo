package com.gupaoedu.rpc;


import com.gupaoedu.rpc.discovery.IServiceDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;


@Component
public class RpcProxyClient {

    @Autowired
    private IServiceDiscovery serviceDiscovery;

    public <T> T clientProxy(final Class<T> interfaceCls, final String version) {

        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls}, new RemoteInvocationHandler(serviceDiscovery, version));
    }
}
