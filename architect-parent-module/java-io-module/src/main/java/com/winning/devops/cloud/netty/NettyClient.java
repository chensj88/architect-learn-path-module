package com.winning.devops.cloud.netty;

import com.winning.devops.cloud.io.utils.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author chensj
 * @version V1.0
 * @classDesc 基于Netty封装socket客户端 <br>
 *  {@link com.winning.devops.cloud.io.socket.nio.SocketClientNioModule} 的Netty版本<br>
 * @email chensj@winning.com.cn
 * @date 2019-07-05 11:11
 */
public class NettyClient {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
       Bootstrap bootstrap = new Bootstrap();
        // Socket 启动的线程
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                });
        Channel channel = bootstrap.connect(InetAddress.getLocalHost(), Constants.PORT).channel();
        while (true){
            channel.writeAndFlush("Netty Client @"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SSS") +" : hello world");
            TimeUnit.SECONDS.sleep(3);
        }
    }
}
