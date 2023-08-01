package com.project.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.project.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_global")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String payment_info_ok(@PathVariable("id") Long id) {
        return paymentHystrixService.payment_info_ok(id);
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    //@HystrixCommand(fallbackMethod = "payment_info_handle", commandProperties = {
    //        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    //})
    @HystrixCommand
    public String payment_info_timeout(@PathVariable("id") Long id) {
        int age = 10 / 0;
        return paymentHystrixService.payment_info_timeout(id);
    }

    public String payment_info_handle(@PathVariable("id") Long id) {
        return "消费端order,请求服务端出现超时错误";
    }

    //配置全局
    public String payment_global() {
        return "全局配置，服务器超时异常";
    }
}
