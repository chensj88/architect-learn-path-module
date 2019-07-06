package org.chen.architect.netty.socket.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 22:03
 */
public class NettySocketClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 接收到消息后 回调函数
     * @param ctx 上下文
     * @param msg 传输内容
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("服务端地址：" + ctx.channel().remoteAddress() + "--> 接收到服务端消息: " + msg);
        System.out.println("服务端消息接收成功，1s后开始继续通信");
        TimeUnit.SECONDS.sleep(1);
        ctx.channel().writeAndFlush("请求服务端 @ "+
                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * 通道激活 回调函数
     * @param ctx 上下文
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")+" : 与服务端通道建立成功，1s后开始通信");
        TimeUnit.SECONDS.sleep(1);
        // 触发服务端与客户端通信
        ctx.channel().writeAndFlush("与服务端通道建立成功，开始通信");
    }
}
