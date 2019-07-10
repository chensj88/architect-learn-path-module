package org.chen.architect.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.chen.architect.netty.websocket.initializer.WebSocketNettyServerInitializer;

import java.net.InetSocketAddress;

/**
 * @author chensj
 * @version V1.0
 * @classDesc
 * @email chensj@winning.com.cn
 * @date 2019-07-10 21:17
 */
public class WebSocketNettyServer {
    public static void main(String[] args) throws InterruptedException {
       EventLoopGroup boss = new NioEventLoopGroup();
       EventLoopGroup worker = new NioEventLoopGroup();

       try {
           ServerBootstrap serverBootstrap = new ServerBootstrap();

           serverBootstrap.group(boss,worker)
                   .channel(NioServerSocketChannel.class)
                   .handler(new LoggingHandler(LogLevel.INFO))
                   .childHandler(new WebSocketNettyServerInitializer());

           ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8080)).sync();

           channelFuture.channel().closeFuture().sync();

       }finally {
           boss.shutdownGracefully();
           worker.shutdownGracefully();
       }
    }
}
