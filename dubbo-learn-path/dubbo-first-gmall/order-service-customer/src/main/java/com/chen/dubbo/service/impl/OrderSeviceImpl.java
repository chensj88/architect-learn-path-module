package com.chen.dubbo.service.impl;

import com.chen.dubbo.api.bean.UserAddress;
import com.chen.dubbo.api.service.OrderSerice;
import com.chen.dubbo.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderSeviceImpl implements OrderSerice {

    @Autowired
    UserService userService;
    @Override
    public void initOrder(String userId) {
        System.out.println("userId:"+userId);
    //    1. 查询用户地址信息
        List<UserAddress> addressList = userService.getUserAddressList(userId);
        System.out.println(addressList);
        for (UserAddress address : addressList) {
            System.out.println(address.getUserAddress());
        }
    }
}
