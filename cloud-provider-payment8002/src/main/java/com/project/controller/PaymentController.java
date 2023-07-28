package com.project.controller;

import com.project.entities.CommonResult;
import com.project.entities.Payment;
import com.project.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

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
}
