package com.atguigu.config;

import com.sun.corba.se.spi.orbutil.threadpool.NoSuchWorkQueueException;
import feign.Logger;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfig {
    Logger.Level getLevel(){
        return Logger.Level.FULL;
    }
}
