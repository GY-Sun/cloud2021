package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String hystrixOk(@PathVariable("id") Integer id){
        return paymentService.paymentinfo_ok(id);
    }

    @GetMapping("/payment/hystrix/error/{id}")
    public String hystrixError(@PathVariable("id") Integer id){
        return paymentService.paymentinfo_TimeOut(id);
    }

    //===服务熔断
    @GetMapping("/payment/hystrix/paymentCircuitBreaker/{id}")
    public String paymentCircuitBreaker(@PathVariable("id")Integer id){
        String s = paymentService.paymentCircuitBreaker(id);
        return s;
    }

}
