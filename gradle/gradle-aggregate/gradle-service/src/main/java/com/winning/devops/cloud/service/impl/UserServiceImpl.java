package com.winning.devops.cloud.service.impl;

import com.winning.devops.cloud.dao.UserDao;
import com.winning.devops.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chensj
 * @version V1.0
 * @classDesc
 * @email chensj@winning.com.cn
 * @date 2019-07-25 23:24
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public String save() {
        return getClass().getName()+" : " + userDao.save();
    }
}
