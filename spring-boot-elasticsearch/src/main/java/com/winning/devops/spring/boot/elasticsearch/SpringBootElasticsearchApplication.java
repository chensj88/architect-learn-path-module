package com.winning.devops.spring.boot.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * spring boot entry
 * @author chensj
 * @EnableElasticsearchRepositories 使用elasticsearch
 */
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.winning.devops.spring.boot.elasticsearch.repository")
public class SpringBootElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootElasticsearchApplication.class, args);
    }

}
