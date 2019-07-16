package com.chen.dubbo.api.service;


import com.chen.dubbo.api.bean.UserAddress;

import java.util.List;

/**
 * @author chensj
 * @version v1.0
 * @classDesc 用户服务接口
 * @project dubbo-learn-path
 * @email chenshijie1988@yeah.net
 * @date 2019-07-16 21:49
 */
public interface UserService {
    /**
     * 根据用户名获取地址信息
     * @param userId 用户名
     * @return list
     */
    public List<UserAddress> getUserAddressList(String userId);
}
