package com.chen.dubbo.service.impl;

import com.chen.dubbo.api.bean.UserAddress;
import com.chen.dubbo.api.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project dubbo-learn-path
 * @email chenshijie1988@yeah.net
 * @date 2019-07-16 21:51
 */
public class UserServiceImpl implements UserService {
    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress address1 = new UserAddress(1,"北京市","1","李四","13012345678","Y");
        UserAddress address2 = new UserAddress(2,"上海市","1","李四","13012345678","N");
        UserAddress address3 = new UserAddress(3,"南京市","1","李四","13012345678","N");
        return Arrays.asList(address1,address2,address3);
    }
}
