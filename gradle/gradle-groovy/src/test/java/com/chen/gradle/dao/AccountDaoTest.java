package com.chen.gradle.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chensj
 * @version V1.0
 * @classDesc
 * @email chensj@winning.com.cn
 * @date 2019-07-25 22:12
 */
public class AccountDaoTest {


    @Test
    public void accountTest(){
        // 获取spring 容器
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("beans.xml");
        // 获取bean
        AccountDao bean = applicationContext.getBean(AccountDao.class);
        // 执行方法
        bean.finaAll();
    }
}
