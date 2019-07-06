package org.chen.architect.netty.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.chen.architect.netty.first.initializer.NettyHttpServerInitializer;

/**
 * @author chensj
 * @version v1.0
 * @classDesc Netty 服务端
 * 客户端发送一个请求过来，返回一个hello world 回去
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 16:53
 * Netty定义流程
 *  --> 定义主从两个线程组
 *      --> 定义一个Netty Server
 *          --> 定义Initializer
 *            --> 定义Handler
 *              --> 定义 请求和响应
 *          -->指定handler
 *       -->指定Initializer
 *    --> 绑定端口
 */
public class NettyHttpServer {

    public static void main(String[] args) throws InterruptedException {
        // 建立两个线程组
        // 轮询组别： 主 从客户端接收连接 转发给worker
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 轮询组别： 从 获取转发过来的数据 处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            // Netty 提供服务端启动类 简化Netty服务器启动
            // 用于启动Netty服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 启动类参数设置
            serverBootstrap
                    // 设置老板与员工 事件循环组
                    // 用于处理 ServerChannel和Channel传输过来的IO和Event
                    .group(bossGroup, workerGroup)
                    // 指定channel 使用反射方式注册
                    .channel(NioServerSocketChannel.class)
                    // 指定channel对应handler初始化类
                    // 在这个handler初始化类中指定使用那些handler、这些handler顺序等
                    .childHandler(new NettyHttpServerInitializer());
            // 开启监听端口
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            // 关闭端口监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
