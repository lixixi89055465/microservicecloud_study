package com.atguigu.springcloud.cfgbeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean {// boot --> spring  applicationContext.xml

    @Bean
    @LoadBalanced// Spring Cloud Ribbon 是基于Netflix Ribbon实现的一套客户端负载均衡的工具。
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IRule myRule() {
//        return new RoundRobinRule();  //达到的目的，用我们重新选择的随机算法替代默认的轮询。
//        return new RandomRule();//随机轮询
        return new RetryRule();//随机轮询
    }
}


