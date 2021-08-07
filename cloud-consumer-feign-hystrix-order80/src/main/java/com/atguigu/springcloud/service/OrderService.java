package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = OrderServiceImp.class)
public interface OrderService {
    @GetMapping("/payment/hystrix/ok/{id}")
    public String hystrixOk(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/error/{id}")
    public String hystrixError(@PathVariable("id") Integer id);
}
