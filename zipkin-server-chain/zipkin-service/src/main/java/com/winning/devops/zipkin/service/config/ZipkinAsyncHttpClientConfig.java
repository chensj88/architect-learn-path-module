package com.winning.devops.zipkin.service.config;

/**
 * @author chensj
 * @title
 * @project zipkin-server-chain
 * @package com.winning.devops.zipkin.service.config
 * @date: 2019-05-01 20:49
 */

import com.ning.http.client.AsyncHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zipkin配置
 * @author chensj
 */
@Configuration
@ConditionalOnClass(AsyncHttpClient.class)
@AutoConfigureAfter(ZipkinBasicConfig.class)
public class ZipkinAsyncHttpClientConfig {

    @Bean
    public AsyncHttpClient asyncHttpClient(){
        return new AsyncHttpClient();
    }
}
