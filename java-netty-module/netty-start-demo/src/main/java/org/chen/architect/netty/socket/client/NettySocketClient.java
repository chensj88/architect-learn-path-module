package org.chen.architect.netty.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.chen.architect.netty.socket.client.initializer.NettySocketClientInitializer;

/**
 * @author chensj
 * @version v1.0
 * @classDesc Netty Socket Client
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 21:53
 */
public class NettySocketClient {

    public static void main(String[] args) throws InterruptedException {
        // 客户端 线程组 用于与服务端连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    // 指定workerGroup 处理信息
                    .handler(new NettySocketClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();

            channelFuture.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
