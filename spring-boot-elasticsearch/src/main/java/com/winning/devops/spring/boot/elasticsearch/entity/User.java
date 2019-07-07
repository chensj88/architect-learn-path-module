package com.winning.devops.spring.boot.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author chensj
 * @title  elasticsearch 对象
 * @project spring-boot-elasticsearch
 * @package com.winning.devops.spring.boot.elasticsearch.entity
 * @date 2019-06-08 22:03
 * @{code @Document(indexName = "userIndex",type = "user")}
 * 指定elasticsearch的index和type
 */
@Data
@Document(indexName = "userindex",type = "user")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private Integer age;
    private Integer sex;
}
