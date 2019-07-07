package org.chen.architect.netty.chat.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-07 14:40
 */
public class NettyChatClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 接收到服务端消息 回调函数
     * @param ctx 上下文
     * @param msg 消息
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
