package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverport;

    @Resource
    DiscoveryClient discoveryClient;

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment)
    {
        int result = paymentService.create(payment);
        log.info("*****插入操作返回结果:" + result);

        if(result > 0)
        {
            return new CommonResult(200,"插入数据库成功,serverport:"+serverport,result);
        }else{
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
    {
        Payment payment = paymentService.getPaymentByIdS(id);
        log.info("*****查询结果:{发发发}",payment);
        if (payment != null) {
            return new CommonResult(200,"查询成功,serverport"+serverport,payment);
        }else{
            return new CommonResult(444,"没有对应记录,查询ID: "+id,null);
        }
    }
    @GetMapping("/discover")
    public Object getDiscover(){
        List<String> services = discoveryClient.getServices();
        String description = discoveryClient.description();
        log.info(description);
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        return instances;
    }
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB()
    {
        return serverport;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeOut()
    {
        System.out.println("*****paymentFeignTimeOut from port: "+serverport);
        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        return serverport;
    }


}
