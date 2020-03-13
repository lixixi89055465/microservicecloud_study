package com.atguigu.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.Random;

public class RandomRule_ZY extends AbstractLoadBalancerRule {
    Random rand;

    public RandomRule_ZY() {
        rand = new Random();
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    int total = 0;
    int currentIndex = 0;

    private Server choose(ILoadBalancer loadBalancer, Object key) {
        if (loadBalancer == null) {
            return null;
        }
        Server server = null;
        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = loadBalancer.getReachableServers();
            List<Server> allList = loadBalancer.getAllServers();
            int serverCount = allList.size();
            if (serverCount == 0) {
                return null;
            }
//            int index = rand.nextInt(serverCount);
//            server = upList.get(index);
            if (total < 5) {
                server = upList.get(currentIndex);
                total++;
            } else {
                total = 0;
                currentIndex++;
                if (currentIndex >= upList.size()) {
                    currentIndex = 0;
                }
            }

            if (server == null) {
                Thread.yield();
                continue;
            }
            if (server.isAlive()) {
                return server;
            }
            server = null;
            Thread.yield();

        }
        return server;
    }
}
