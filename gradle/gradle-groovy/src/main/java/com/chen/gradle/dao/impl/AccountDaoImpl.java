package com.chen.gradle.dao.impl;

import com.chen.gradle.dao.AccountDao;

import java.util.List;

/**
 * @author chensj
 * @version V1.0
 * @classDesc
 * @email chensj@winning.com.cn
 * @date 2019-07-25 22:08
 */
public class AccountDaoImpl implements AccountDao {
    @Override
    public List finaAll() {
        System.out.println("数据加载完毕");
        return null;
    }
}
