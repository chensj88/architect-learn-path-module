package org.chen.architect.netty.http.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import org.chen.architect.netty.http.handler.NettyHttpServerHandler;

/**
 * @author chensj
 * @version v1.0
 * @classDesc NettyHttpServer 初始化参数配置
 * 主要是将自定义handler注册到Netty中
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
        // HttpServerCodec 对http请求信息和响应信息进行编解码
        pipeline.addLast("httpServerCodec", new HttpServerCodec());

        // 设置自定义处理器
        pipeline.addLast("nettyHttpServerHandler", new NettyHttpServerHandler());

    }
}
