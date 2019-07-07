package com.winning.devops.zipkin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * @author chensj
 * @title
 * @project zipkin-server-chain
 * @package PACKAGE_NAME
 * @date: 2019-05-01 22:30
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerApplication {
    public static void main(String[] args){
        SpringApplication.run(ZipkinServerApplication.class,args);
    }

}
