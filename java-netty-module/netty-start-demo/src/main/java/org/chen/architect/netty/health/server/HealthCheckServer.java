package org.chen.architect.netty.health.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.chen.architect.netty.health.server.initializer.HealthCheckServerInitializer;

/**
 * @author chensj
 * @version v1.0
 * @classDesc Netty Socket Server
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 21:18
 */
public class HealthCheckServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // handler 用于处理bossGroup相关信息
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // childHandler 用于处理workerGroup相关信息
                    .childHandler(new HealthCheckServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8080)
                    // 表示会一直等待
                    .sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
