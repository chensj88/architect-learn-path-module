package com.chen.dubbo.boot.controller;

import com.chen.dubbo.api.bean.UserAddress;
import com.chen.dubbo.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project dubbo-learn-path
 * @email chenshijie1988@yeah.net
 * @date 2019-07-18 18:30
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/initOrder")
    public List<UserAddress> initOrder(@RequestParam("uid") String userId){
        return orderService.initOrder(userId);
    }
}
