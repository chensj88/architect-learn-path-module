package com.chen.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author chensj
 * @version v1.0
 * @classDesc 主程序
 * @project dubbo-learn-path
 * @email chenshijie1988@yeah.net
 * @date 2019-07-16 22:36
 */
public class ProviderApplication {

    public static void main(String[] args) throws IOException {
        // 创建IOC容器
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("provider.xml");
        // 启动IOC容器
        applicationContext.start();
        // 阻塞不停止
        System.in.read();

    }
}
