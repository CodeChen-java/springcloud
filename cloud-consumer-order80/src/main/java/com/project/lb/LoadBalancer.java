package com.project.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 自定义的轮训算法
 */
public interface LoadBalancer {
    ServiceInstance intances(List<ServiceInstance> serviceInstances);
}
