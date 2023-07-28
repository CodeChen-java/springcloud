package com.project.controller;

import com.project.entities.CommonResult;
import com.project.entities.Payment;
import com.project.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    //使用RestTemplate实现服务调用
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private LoadBalancer loadBalancer;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    //测试getForEntity方法
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getForEntity(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> paymentEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/"+id, CommonResult.class);
        if(paymentEntity.getStatusCode().is2xxSuccessful()) {
            log.info(String.valueOf(paymentEntity.getStatusCode()));
            return paymentEntity.getBody();
        }else {
            return new CommonResult<>(450, "操作失败");
        }
    }

    @GetMapping("/consumer/payment/lb")
    public String consumerLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.intances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }
}
