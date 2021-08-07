package com.atguigu.springcloud.service;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    //==========服务降级
    /**
     * 正常访问的方法
     */
    public String paymentinfo_ok(Integer id){
        return "线程池"+Thread.currentThread().getName()+"paymentinfo_ok，id:"+id;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="4000")})
    public String paymentinfo_TimeOut(Integer id){
       try {

            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //int i=10/0;
        return "线程池"+Thread.currentThread().getName()+"paymentinfo_TimeOut，id:"+id+"\t"+"O(∩_∩)O，耗费3秒";
    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return  Thread.currentThread().getName()+"服务出错了·请耐心等待"+id;
    }

    //==========服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "15"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败次数达到百分之多少后熔断

    })//在10秒的窗口期内最少接收十次请求，如果失败次数达到60%后熔断
    public String paymentCircuitBreaker(@PathVariable("id") Integer id)
    {
        if(id < 0)
        {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id)
    {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }
}
