package com.winning.devops.zipkin.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chensj
 * @title zipkin 参数加载
 * @project zipkin-server-chain
 * @package com.winning.devops.zipkin.service.config
 * @date: 2019-05-01 21:38
 */
@Data
@Component
@ConfigurationProperties(prefix = "zipkin")
public class ZipkinProperties {

    private String serviceName;
    private String endpointUrl;
    private int connectTimeout;
    private int readTimeout;
    private int flushInterval;
    private boolean compressionEnabled;

}
