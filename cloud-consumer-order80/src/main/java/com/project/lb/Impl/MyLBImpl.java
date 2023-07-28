package com.project.lb.Impl;

import com.project.lb.LoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLBImpl implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        }while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("第"+next +"次访问");
        return next;
    }

    @Override
    public ServiceInstance intances(List<ServiceInstance> serviceInstances) {
        int idx = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(idx);
    }
}
