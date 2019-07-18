package com.chen.dubbo.boot.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chen.dubbo.api.bean.UserAddress;
import com.chen.dubbo.api.service.OrderService;
import com.chen.dubbo.api.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project dubbo-learn-path
 * @email chenshijie1988@yeah.net
 * @date 2019-07-16 21:59
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * dubbo依赖引用
     */
    @Reference
    UserService userService;
    @Override
    public List<UserAddress> initOrder(String userId) {
        System.out.println("userId:"+userId);
    //    1. 查询用户地址信息
        List<UserAddress> addressList = userService.getUserAddressList(userId);
        System.out.println(addressList);
        for (UserAddress address : addressList) {
            System.out.println(address.getUserAddress());
        }
        return addressList;
    }
}
