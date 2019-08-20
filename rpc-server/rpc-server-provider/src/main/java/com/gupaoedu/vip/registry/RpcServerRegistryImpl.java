package com.gupaoedu.vip.registry;

import com.gupaoedu.vip.GpRpcServer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author huxingming
 * @date 2019/8/20-10:25 AM
 * @Description TODO
 */
@Component
public class RpcServerRegistryImpl implements IRpcServerRegistry {

    @Autowired
    GpRpcServer gpRpcServer;

    private CuratorFramework client;

    {
        String connectString = "10.200.20.231:2181";
        client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("registry")
                .build();
        client.start();
    }

    @Override
    public void registry(String serviceName) {
        try {
            String path = "/" + serviceName + "/" + InetAddress.getLocalHost().getHostAddress() + ":" + gpRpcServer.getPort();
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
