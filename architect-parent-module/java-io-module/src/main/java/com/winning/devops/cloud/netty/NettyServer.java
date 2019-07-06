package com.winning.devops.cloud.netty;

import com.winning.devops.cloud.io.utils.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author chensj
 * @version V1.0
 * @classDesc 使用Netty创建SocketServer <br>
 * 是{@link com.winning.devops.cloud.io.socket.nio.SocketServerNioServer}借助Netty实现的版本 <br>
 * @email chensj@winning.com.cn
 * @date 2019-07-05 10:58
 */
public class NettyServer {

    public static void main(String[] args) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 接受新连接线程，主要负责创建新连接
        // 与SocketServerNioServer中serverSelector线程对应
        NioEventLoopGroup boots = new NioEventLoopGroup();
        // 负责读取数据的线程，主要用于读取数据以及业务逻辑处理
        // 与SocketServerNioServer中clientSelector线程对应
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boots, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nsh) throws Exception {
                        nsh.pipeline().addLast(new StringDecoder());
                        nsh.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                                        String msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                }).bind(Constants.PORT);

    }
}
