package com.chen.dubbo.api.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project dubbo-learn-path
 * @email chenshijie1988@yeah.net
 * @date 2019-07-16 21:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress implements Serializable {

    /**
     * 主键 序号
     */
    private Integer id;
    /**
     * 用户地址
     */
    private String userAddress;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 电话号码
     */
    private String phoneNum;
    /**
     * 是否默认地址  Y 是  N否
     */
    private String isDefault;

}
