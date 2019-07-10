package org.chen.architect.netty.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @author chensj
 * @version V1.0
 * @classDesc 文本帧(frame) WebSocket 服务处理
 * @email chensj@winning.com.cn
 * @date 2019-07-10 21:39
 */
public class TextWebSocketNettyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到消息: "+ msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间："+ LocalDateTime.now()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added： " + ctx.channel().id().asLongText());
        ctx.channel().writeAndFlush("服务端计划开始推送消息");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("hander removed " + ctx.channel().id().asLongText());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("异常发生");
        ctx.close();
    }
}
