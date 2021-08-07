package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@RestController
public class OrderConsulController {

    @Autowired
    RestTemplate restTemplate;

    public static final String CONSUL_URL="http://consul-provider-payment";

    @GetMapping("/consumer/payment/consul")
    public String getConsul(){
        String forObject = restTemplate.getForObject(CONSUL_URL+"/payment/consul", String.class);
        return forObject;
    }

}
