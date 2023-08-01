package com.project.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 服务降级和熔断
 */
@Service
public class PaymentService {

    public String payment_info_ok(Long id) {
        return "线程池："+ Thread.currentThread().getName() +"  payment_info_ok, id:" + id;
    }


    @HystrixCommand(fallbackMethod = "payment_info_handle", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String payment_info_timeout(Long id) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "线程池："+ Thread.currentThread().getName() +"  payment_timeout_ok, id:" + id;
    }

    //设置兜底方法 当超时时调用此方法
    public String payment_info_handle(Long id) {
        return "线程池："+ Thread.currentThread().getName() +"  系统繁忙，请稍后再试:" + id +"o(╥﹏╥)o";
    }

    /**
     * 服务熔断
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value="true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败率达到多少之后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Long id) {
        if( id < 0) {
            throw new RuntimeException("id不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t 调用成功，流水号：" + serialNumber;
    }
    public String paymentCircuitFallback(@PathVariable("id") Long id) {
        return "id不能为负数， 请稍后再试！";
    }
}
