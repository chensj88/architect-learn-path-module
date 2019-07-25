package com.winning.devops.cloud.dao.impl;

import com.winning.devops.cloud.dao.UserDao;
import org.springframework.stereotype.Component;

/**
 * @author chensj
 * @version V1.0
 * @classDesc
 * @email chensj@winning.com.cn
 * @date 2019-07-25 23:29
 */
@Component
public class UserDaoImpl implements UserDao {
    @Override
    public String save() {
        System.out.println(getClass().getName()+":save");
        return getClass().getName()+":save";
    }
}
