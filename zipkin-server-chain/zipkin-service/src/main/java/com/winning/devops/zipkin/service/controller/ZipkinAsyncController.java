package com.winning.devops.zipkin.service.controller;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.http.*;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.concurrent.Future;

/**
 * @author chensj
 * @title
 * @project zipkin-server-chain
 * @package com.winning.devops.zipkin.service.controller
 * @date: 2019-05-01 23:34
 */
@Api("zipkin brave async api")
@RestController
@RequestMapping("/zipkin/async")
public class ZipkinAsyncController {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ZipkinAsyncController.class);

    @Autowired
    private AsyncHttpClient asyncHttpClient;
    @Autowired
    private Brave brave;
    @Value("${spring.application.name}")
    private String serviceName;

    @ApiOperation("trace第一步")
    @RequestMapping("/{service}/test")
    public String test(@PathVariable String service) {
        logger.info("call {} instance {} method {}", service, serviceName, "test");
        RequestBuilder builder = null;
        Request request = null;
        Future<Response> response = null;
        RequestBuilder builder1 = null;
        Request request1 = null;
        Future<Response> response1 = null;
        String content = "";
        try {
            switch (service) {
                case "service1":
                    builder = new RequestBuilder();
                    builder.setUrl("http://localhost:8032/zipkin/async/service2/test");
                    request = builder.build();

                    clientRequestInterceptor(request);
                    response = asyncHttpClient.executeRequest(request);
                    clientResponseInterceptor(response.get());
                    content = response.get().getResponseBody();
                    break;
                case "service2":
                    builder = new RequestBuilder();
                    builder.setUrl("http://localhost:8033/zipkin/async/service3/test");
                    request = builder.build();

                    clientRequestInterceptor(request);
                    response = asyncHttpClient.executeRequest(request);
                    clientResponseInterceptor(response.get());

                    builder1 = new RequestBuilder();
                    builder1.setUrl("http://localhost:8034/zipkin/async/service4/test");
                    request1 = builder1.build();

                    clientRequestInterceptor(request1);
                    response1 = asyncHttpClient.executeRequest(request1);
                    clientResponseInterceptor(response1.get());
                    content = response.get().getResponseBody() + ":" + response1.get().getResponseBody();
                    break;
                case "service3":
                    content = "services3";
                    break;
                default:
                    content = "service4";
            }


            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 其实就是在做cs
     * @param request
     */
    private void clientRequestInterceptor(Request request) {
        brave.clientRequestInterceptor().handle(new HttpClientRequestAdapter(new HttpClientRequest() {

            @Override
            public URI getUri() {
                return URI.create(request.getUrl());
            }

            @Override
            public String getHttpMethod() {
                return request.getMethod();
            }

            @Override
            public void addHeader(String headerKey, String headerValue) {
                request.getHeaders().add(headerKey, headerValue);
            }
        }, new DefaultSpanNameProvider()));
    }

    /**
     * 其实就是在做cr
     * @param response
     */
    private void clientResponseInterceptor(Response response) {
        brave.clientResponseInterceptor().handle(new HttpClientResponseAdapter(new HttpResponse() {
            @Override
            public int getHttpStatusCode() {
                return response.getStatusCode();
            }
        }));
    }
}
