package com.project.service.Impl;

import com.project.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements PaymentHystrixService {
    @Override
    public String payment_info_ok(Long id) {
        return "PaymentServiceImpl -- payment_info_ok";
    }

    @Override
    public String payment_info_timeout(Long id) {
        return "PaymentServiceImpl -- payment_info_timeout";
    }
}
