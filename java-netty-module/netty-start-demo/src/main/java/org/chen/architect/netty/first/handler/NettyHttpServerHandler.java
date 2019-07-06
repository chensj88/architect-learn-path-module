package org.chen.architect.netty.first.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author chensj
 * @version v1.0
 * @classDesc 用户自定义handler
 * 特定消息类型的handler
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 17:27
 */
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 每次处理HttpObject类型消息的时候都会调用这个方法
     * 也就是读取这个类型的请求，并返回响应，也就是处理结果
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 直接启动后使用 curl 访问会出现异常
        // io.netty.channel.DefaultChannelPipeline onUnhandledInboundException
        //  警告: An exceptionCaught() event was fired, and it reached at the tail of the pipeline.
        //  It usually means the last handler in the pipeline did not handle the exception.
        //  java.io.IOException: 你的主机中的软件中止了一个已建立的连接。
        System.out.println(ReflectionToStringBuilder.toString(ctx, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(ReflectionToStringBuilder.toString(msg, ToStringStyle.MULTI_LINE_STYLE));
        // 异常处理
        if(msg instanceof HttpRequest){
            // 构建响应内容
            ByteBuf content =
                    Unpooled.copiedBuffer("Hello World, this response from netty",
                            CharsetUtil.UTF_8);
            // 构建响应
            FullHttpResponse response =
                    // 指定协议、状态码、响应内容
                    new DefaultFullHttpResponse(
                            HttpVersion.HTTP_1_1,
                            HttpResponseStatus.OK,
                            content);

            // 设置消息头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 返回响应结果
            ctx.writeAndFlush(response);
        }

    }
}
