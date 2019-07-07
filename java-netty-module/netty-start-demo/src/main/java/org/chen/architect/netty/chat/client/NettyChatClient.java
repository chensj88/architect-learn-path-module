package org.chen.architect.netty.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.chen.architect.netty.chat.client.initializer.NettyChatClientInitializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-07 14:36
 */
public class NettyChatClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        // 客户端 线程组 用于与服务端连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    // 指定workerGroup 处理信息
                    .handler(new NettyChatClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();

            Channel channel = channelFuture.channel();
            // 获取控制台输入内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // 死循环
            for(;;){
                // 每次按回车 就会发送消息
                channel.writeAndFlush(reader.readLine() +"\r\n");
            }

        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
