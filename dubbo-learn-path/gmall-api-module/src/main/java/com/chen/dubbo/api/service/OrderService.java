package com.chen.dubbo.api.service;

/**
 * @author chensj
 * @version v1.0
 * @classDesc 订单服务
 * @project dubbo-learn-path
 * @email chenshijie1988@yeah.net
 * @date 2019-07-16 21:58
 */
public interface OrderService {
    /**
     * 初始化订单
     * @param userId 用户订单
     */
    public void initOrder(String userId);
}
