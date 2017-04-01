package com.parkingcloud.gateway.controller;

import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by caryc on 2017/3/29.
 */
@RestController
public class TestController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("services")
    public List<String> getServices(){
        return discoveryClient.getServices();
    }

    @GetMapping("services/{id}")
    public List<ServiceInstance> getServiceUrl(@PathVariable("id") String serviceId){
        return discoveryClient.getInstances(serviceId);
        /*
        ServiceInstance si = loadBalancerClient.choose(serviceId);
        if (si==null){
            return "No service found";
        }
        return si.getUri().toString();
        */
    }

}
