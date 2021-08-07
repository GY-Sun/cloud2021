package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@DefaultProperties(defaultFallback = "fallback")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String OK(@PathVariable("id")Integer id){
        return orderService.hystrixOk(id);
    }
    @GetMapping("/consumer/payment/hystrix/error/{id}")
   // @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
    //        @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
  //  })
    public String hystrixError(@PathVariable("id") Integer id){
        return orderService.hystrixError(id);
    }

    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id)
    {
        return "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    }
    @GetMapping("/consumer/payment/hystrix/TimeOut/{id}")
  //  @HystrixCommand  //默认超时1秒
    public String hystrixTimeOut(@PathVariable("id") Integer id){
        return orderService.hystrixError(id);
    }
    public String fallback(){
        return "请稍后再试";
    }
}
