package com.chen.dubbo;

import com.chen.dubbo.api.service.OrderSerice;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project dubbo-learn-path
 * @email chenshijie1988@yeah.net
 * @date 2019-07-16 23:11
 */
public class CustomerApplication {

    public static void main(String[] args) throws IOException {
       ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");

        OrderSerice bean = applicationContext.getBean(OrderSerice.class);
        bean.initOrder("1");
        System.out.println("调用结束");
        System.in.read();

    }
}
