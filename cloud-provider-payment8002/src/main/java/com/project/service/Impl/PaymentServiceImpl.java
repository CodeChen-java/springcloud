package com.project.service.Impl;

import com.project.dao.PaymentMapper;
import com.project.entities.Payment;
import com.project.service.PaymentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentDao;

    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    public Payment getPaymentById(@Param("id") Long id) {
        System.out.println("paymentdao="+paymentDao);
        return paymentDao.getPaymentById(id);
    }
}
