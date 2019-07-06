package org.chen.architect.netty.http.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.chen.architect.netty.base.Constants;

import java.net.URI;

/**
 * @author chensj
 * @version v1.0
 * @classDesc 用户自定义handler
 * 特定消息类型的handler
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 17:27
 * SimpleChannelInboundHandler  Inbound 进来请求处理
 *
 * 常规处理流程：
 *   handler Added 添加handler
 *      --> channel Registered 注册 channel
 *          --> channel Active  激活 channel
 *              --> channelRead0 请求处理
 *                  -->channel Inactive 待用 channel
 *                      --> channel Unregistered 解除注册 channel
 *                          --> handler Removed 移除 handler
 * 注意上面的这个处理流程并不是完全正确的
 * 使用curl是按照上面的流程执行，但是对于从浏览器访问这个则会存在一定的不一定性
 * 测试的时候发现：channel Inactive/Unregistered 和handler Removed没有执行
 * 主要的原因是
 * Netty并不是完全遵照servlet的定义来操作的，对于http 1.1协议上面存在一个keep-alive的属性，在这个属性规定的时间内，
 * 服务端不会将客户端的请求给切断。
 *  在servlet中，servlet容器会对这些进行判断，完成切断连接的操作
 *  在Netty中，就没有这些，这个需要开发人员确定，通过判断请求http协议版本和keep-alive时间，然后服务端主动结束连接
 * 所以才会出现上面这个不一致
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

        System.out.println("请求实例:"+msg.getClass());

        System.out.println("请求远程地址:"+ctx.channel().remoteAddress());
        // 异常处理
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println("请求方法类型：" + httpRequest.method().name());
            URI uri = new URI(httpRequest.uri());
            // 请求URL处理
            if (Constants.ICO_PATH.equals(uri.getPath())) {
                System.out.println("请求favicon.ico");
                return;
            }
            System.out.println("请求URI：" + new URI(httpRequest.uri()).getPath());
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

            // channel关闭
            // 判断请求http协议版本和keep-alive时间
            ctx.channel().close();
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Active");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler Added");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Unregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Read Complete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("user Event Triggered");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Writability Changed");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception Caught");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler Removed");
        super.handlerRemoved(ctx);
    }
}
