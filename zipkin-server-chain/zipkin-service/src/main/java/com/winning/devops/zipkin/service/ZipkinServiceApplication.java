package com.winning.devops.zipkin.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chensj
 * @title
 * @project zipkin-server-chain
 * @package com.winning.devops.zipkin.service.config
 * @date: 2019-05-01 20:58
 */
@SpringBootApplication
public class ZipkinServiceApplication {

    public static void main(String[] args){
        SpringApplication.run(ZipkinServiceApplication.class,args);
    }

}
