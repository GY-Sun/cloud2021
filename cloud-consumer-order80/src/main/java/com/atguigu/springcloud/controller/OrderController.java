package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import com.netflix.ribbon.proxy.annotation.Http;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.elasticsearch.ElasticSearchRestHealthContributorAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

  //  public static final String PAYMENT_URL="http://localhost:8001";
    public static final String PAYMENT_URL= "Http://CLOUD-PAYMENT-SERVICE";
    @Resource
    RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    DiscoveryClient discoveryClient;

  @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class );
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

  @GetMapping("/consumer/payment/getEntity/{id}")
  public CommonResult<Payment> getEntity(@PathVariable Long id) {

    ResponseEntity<CommonResult> forEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    if(forEntity.getStatusCode().is2xxSuccessful()) {
      return forEntity.getBody();
    }else {
      return new CommonResult<Payment>(444,"没有数据",null);
    }
  }

  @GetMapping("/consumer/payment/createEntity")
  public CommonResult<Payment> createEntity(Payment payment){
      return restTemplate.postForEntity(PAYMENT_URL+"/payment/create", payment, CommonResult.class).getBody();
  }

  @GetMapping("/consumer/payment/lb")
  public String getPaymentLB()
  {
    List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

    if(instances == null || instances.size()<=0) {
      return null;
    }
    ServiceInstance serviceInstance = loadBalancer.instances(instances);
    URI uri = serviceInstance.getUri();

    return restTemplate.getForObject(uri+"/payment/lb",String.class);
  }

  public void gg(){
    log.info("我最帅啊");
  }
}
