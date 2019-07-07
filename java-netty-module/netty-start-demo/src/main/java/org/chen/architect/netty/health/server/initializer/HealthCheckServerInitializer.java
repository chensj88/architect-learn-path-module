package org.chen.architect.netty.health.server.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.chen.architect.netty.health.server.handler.HealthCheckServerHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 21:27
 */
public class HealthCheckServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 客户端与服务端一旦连接成功，这个方法就会被调用
     * 先做handler add，channel register
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        // 一种解码器，空闲检测处理
        // 在一段时间未进行读、写或者读写操作的时候就会触发事件IdleStateEvent
        // 参数分别对应
        // 读空闲时间 5s
        // 写空闲时间 7s
        // 读写空闲时间 10s
        pipeline.addLast("IdleStateHandler",
                new IdleStateHandler(5,7,3, TimeUnit.SECONDS));

        // 自定义handler
        pipeline.addLast("HealthCheckServerHandler",new HealthCheckServerHandler());


    }
}
