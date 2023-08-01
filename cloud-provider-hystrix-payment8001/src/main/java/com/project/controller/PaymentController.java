package com.project.controller;

import com.project.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    
    @Value("${server.port}")
    private String serverPort;
    
    @GetMapping("/payment/hystrix/ok/{id}")
    public String payment_info_ok(@PathVariable("id") Long id) {
        return paymentService.payment_info_ok(id);
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String payment_info_timeout(@PathVariable("id") Long id) {
        return paymentService.payment_info_timeout(id);
    }

    //服务熔断
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuit(@PathVariable("id") Long id) {
        return paymentService.paymentCircuitBreaker(id);
    }
}
