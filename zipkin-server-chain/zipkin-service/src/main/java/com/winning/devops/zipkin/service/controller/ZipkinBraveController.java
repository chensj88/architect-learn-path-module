package com.winning.devops.zipkin.service.controller;

/**
 * @author chensj
 * @title
 * @project zipkin-server-chain
 * @package com.winning.devops.zipkin.service.controller
 * @date: 2019-05-01 21:10
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Api("zipkin brave api")
@RestController
@RequestMapping("/zipkin")
public class ZipkinBraveController {

    private static final Logger LOG = LoggerFactory.getLogger(ZipkinBraveController.class);
    @Autowired
    private OkHttpClient okHttpClient;

    @Value("${spring.application.name}")
    private String  serviceName;

    @ApiOperation("trace")
    @RequestMapping("/{service}/test")
    public String test(@PathVariable String service) throws Exception {

        LOG.info("call {} instance {} method {}",service,serviceName,"test");
        //100ms
        Thread.sleep(100);
        String content = "";
        Request request = null;
        Response response = null;
        Request request1 = null;
        Response response1 = null;
        /*
         * 1、执行execute()的前后，会执行相应的拦截器（cs,cr）
         * 2、请求在被调用方执行的前后，也会执行相应的拦截器（sr,ss）
         */
        switch (service){
            case "service1":
                request =  new Request.Builder().url("http://localhost:8032/zipkin/service2/test").build();
                response = okHttpClient.newCall(request).execute();
                content = response.body().string();
                break;
            case "service2":
                request =  new Request.Builder().url("http://localhost:8033/zipkin/service3/test").build();
                response = okHttpClient.newCall(request).execute();
                request1 =  new Request.Builder().url("http://localhost:8034/zipkin/service4/test").build();
                response1 = okHttpClient.newCall(request1).execute();
                content = response.body().string() +":"+ response1.body().string();
                break;
            case "service3":
                content = "services3";
                break;
            default:
                content = "service4";
        }



        return content;
    }

}