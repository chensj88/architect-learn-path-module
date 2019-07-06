package org.chen.architect.netty.first.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author chensj
 * @version v1.0
 * @classDesc 用户自定义处理
 * 用于在Netty server启动的时候作为childHandler提供
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 17:13
 */
public class NettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 这个方法在注册Channel后将调用此方法。
     * 方法返回后，此实例将从Channel的ChannelPipeline中删除。
     *
     * @param ch channel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 获取管道
        // 这个类似一个拦截器组，在各个方法中做不同的事情
        ChannelPipeline pipeline = ch.pipeline();
        // 在处理最后增加一个处理
        // 第一个参数为 handler名称
        // 第二个参数为 handler
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());

    }
}
