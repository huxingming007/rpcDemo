package com.gupaoedu.rpc;

import com.gupaoedu.rpc.discovery.IServiceDiscovery;
import com.gupaoedu.vip.RpcRequest;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {

    private String version;

    private IServiceDiscovery serviceDiscovery;

    public RemoteInvocationHandler(IServiceDiscovery iServiceDiscovery, String version) {
        this.version = version;
        this.serviceDiscovery = iServiceDiscovery;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //请求会进入到这里
        System.out.println("come in");
        //请求数据的包装
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion(version);
        //远程通信
        String serviceName = rpcRequest.getClassName();
        if (!StringUtils.isEmpty(version)) {
            serviceName = serviceName + "-" + version;
        }

        String select = serviceDiscovery.discovery(serviceName);
        String[] split = select.split(":");
        RpcNetTransport netTransport = new RpcNetTransport(split[0], Integer.parseInt(split[1]));
        Object result = netTransport.send(rpcRequest);

        return result;
    }
}
