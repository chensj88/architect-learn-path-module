package com.winning.devops.spring.boot.elasticsearch.controller;

import com.winning.devops.spring.boot.elasticsearch.entity.User;
import com.winning.devops.spring.boot.elasticsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * $END$
 *
 * @author chensj
 * @title
 * @date 2019-06-08 22:11
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 用户新增
     * @param user
     */
    @PostMapping(value = "add")
    public User add(@RequestBody User user){
        return userRepository.save(user);
    }

    @PostMapping(value = "/query/{id}")
    public User query(@PathVariable String id){
        return  userRepository.findById(id).get();
    }
}
