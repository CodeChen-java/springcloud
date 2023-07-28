package com.project.controller;

//import com.netflix.discovery.DiscoveryClient;

import com.project.entities.CommonResult;
import com.project.entities.Payment;
import com.project.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    //服务发现
    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("********插入结果:" + result);
        if(result > 0) {
            return new CommonResult(200, "插入数据库成功,serverPort:"+serverPort, result);
        }else {
            return new CommonResult(400, "插入数据库失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        System.out.println(paymentService);
        Payment payment = paymentService.getPaymentById(id);
        log.info("********查询结果:" + payment);
        if(payment != null) {
            return new CommonResult(200, "查询数据库成功,serverPort:"+serverPort, payment);
        }else {
            return new CommonResult(400, "查询数据库失败", null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service: services) {
            log.info("服务：" + service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance: instances) {
            log.info("服务id："+instance.getInstanceId()+"\t"+"服务主机："+instance.getHost()
            +"服务端口号："+instance.getPort()+"服务地址："+instance.getUri());
        }
        return this.discoveryClient;
    }
}
