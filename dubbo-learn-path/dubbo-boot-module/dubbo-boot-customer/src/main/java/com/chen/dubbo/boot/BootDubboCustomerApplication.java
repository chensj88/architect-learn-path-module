package com.chen.dubbo.boot;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project dubbo-learn-path
 * @email chenshijie1988@yeah.net
 * @date 2019-07-18 18:23
 */
@SpringBootApplication
@EnableDubbo
public class BootDubboCustomerApplication {
    public static void main(String[] args){
       SpringApplication.run(BootDubboCustomerApplication.class, args);
    }
}
