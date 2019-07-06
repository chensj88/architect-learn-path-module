package org.chen.architect.netty.socket.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 21:31
 */
public class NettySocketServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 请求处理 回调函数
     *
     * @param ctx 上下文，包含URI、方法类型等
     * @param msg 接收到请求对象
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("客户端地址："+ctx.channel().remoteAddress() + "-->" + msg);
        // 返回消息
        ctx.channel().writeAndFlush("收到Client消息 @" +
                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * 异常回调方法
     * @param ctx 上下文
     * @param cause 异常
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
