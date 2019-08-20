package com.gupaoedu.rpc.discovery;


import com.google.common.collect.Maps;
import com.gupaoedu.rpc.loadbalance.LoanBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author huxingming
 * @date 2019/8/20-2:43 PM
 * @Description TODO
 */
@Service
public class ServiceDiscoveryWithZk implements IServiceDiscovery {

    /**
     * 本地存储的服务地址,key为服务名称，value为服务地址
     */
    private final Map<String, List<String>> serviceRepos = Maps.newConcurrentMap();

    private CuratorFramework client;

    {
        String connectString = "127.0.0.1:2181";
        client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("registry")
                .build();
        client.start();
    }

    @Resource(name = "randomLoanBalance")
    private LoanBalance loanBalance;


    @Override
    public String discovery(String serviceName) {

        String path = "/" + serviceName;
        if (null == serviceRepos.get(serviceName) || serviceRepos.get(serviceName).isEmpty()) {
            try {
                List<String> list = client.getChildren().forPath(path);
                serviceRepos.put(serviceName, list);
                registryWatch(serviceName, path);
            } catch (Exception e) {
                return null;
            }
        }
        return loanBalance.select(serviceRepos.get(serviceName));
    }

    private void registryWatch(final String serviceName, final String path) throws Exception {

        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                System.out.println("客户端收到节点变更通知：" + event.getType());
                serviceRepos.put(serviceName, client.getChildren().forPath(path));
            }
        });
    }
}
