package com.winning.devops.zipkin.service.config;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.EmptySpanCollectorMetricsHandler;
import com.github.kristofa.brave.Sampler;
import com.github.kristofa.brave.SpanCollector;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.HttpSpanCollector;
import com.github.kristofa.brave.mysql.MySQLStatementInterceptorManagementBean;
import com.github.kristofa.brave.servlet.BraveServletFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chensj
 * @title zipkin 基础配置
 * @project zipkin-server-chain
 * @package com.winning.devops.zipkin.service.config
 * @date: 2019-05-01 23:31
 */
@Configuration
public class ZipkinBasicConfig {
    @Autowired
    private ZipkinProperties zipkinProperties;
    /**
     *  HttpSpanCollector：span信息收集器
     * @return
     */
    @Bean
    public SpanCollector spanCollector() {
        HttpSpanCollector.Config spanConfig = HttpSpanCollector.Config.builder()
                //默认false，span在transport之前是否会被gzipped。
                .compressionEnabled(zipkinProperties.isCompressionEnabled())
                //5s，默认10s
                .connectTimeout(zipkinProperties.getConnectTimeout())
                //1s
                .flushInterval(zipkinProperties.getFlushInterval())
                //5s，默认60s
                .readTimeout(zipkinProperties.getReadTimeout())
                .build();
        return HttpSpanCollector.create(zipkinProperties.getEndpointUrl(),
                spanConfig,
                new EmptySpanCollectorMetricsHandler());
    }

    /**
     * 基本实例，注意传入的参数是serviceName
     * @param spanCollector
     * @return
     */
    @Bean
    public Brave brave(SpanCollector spanCollector) {
        //指定serviceName
        Brave.Builder builder = new Brave.Builder(zipkinProperties.getServiceName());
        builder.spanCollector(spanCollector);
        //采集率
        builder.traceSampler(Sampler.create(1));
        return builder.build();
    }

    /**
     * 设置sr（server receive），ss（server send）拦截器
     * @param brave
     * @return
     */
    @Bean
    public BraveServletFilter braveServletFilter(Brave brave) {
        /**
         * 设置sr、ss拦截器
         */
        return new BraveServletFilter(brave.serverRequestInterceptor(),
                brave.serverResponseInterceptor(),
                new DefaultSpanNameProvider());
    }

    @Bean
    public MySQLStatementInterceptorManagementBean mySQLStatementInterceptorManagementBean(Brave brave) {
        return new MySQLStatementInterceptorManagementBean(brave.clientTracer());
    }
}
