package com.winning.devops.zipkin.service.config;

/**
 * @author chensj
 * @title
 * @project zipkin-server-chain
 * @package com.winning.devops.zipkin.service.config
 * @date: 2019-05-01 20:49
 */

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.okhttp.BraveOkHttpRequestResponseInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zipkin配置
 * @author chensj
 */
@Configuration
@ConditionalOnClass(OkHttpClient.class)
@AutoConfigureAfter(ZipkinBasicConfig.class)
public class ZipkinOkHttpConfig {



    /**
     * 构建client实例，添加拦截器，设置cs（client send），cr（client receive）拦截器
     * @param brave
     * @return
     */
    @Bean
    public OkHttpClient okHttpClient(Brave brave){
        /**
         * 设置cs、cr拦截器
         */
        return new OkHttpClient.Builder()
                .addInterceptor(new BraveOkHttpRequestResponseInterceptor(brave.clientRequestInterceptor(),
                        brave.clientResponseInterceptor(),
                        new DefaultSpanNameProvider()))
                .build();
    }

}
