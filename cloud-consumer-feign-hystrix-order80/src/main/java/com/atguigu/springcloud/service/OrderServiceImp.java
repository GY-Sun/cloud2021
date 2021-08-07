package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class OrderServiceImp implements OrderService{

    @Override
    public String hystrixOk(Integer id) {
        return "-------------hystrixOk,出错啦！--------------";
    }

    @Override
    public String hystrixError(Integer id) {
        return "---------------hystrixError,出错啦！--------------------";
    }
}
