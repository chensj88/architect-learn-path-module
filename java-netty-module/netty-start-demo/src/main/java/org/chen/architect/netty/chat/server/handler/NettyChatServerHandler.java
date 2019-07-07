package org.chen.architect.netty.chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.chen.architect.netty.base.Constants;

import java.util.Date;

/**
 * @author chensj
 * @version v1.0
 * @classDesc 处理连接信息
 * 连接成功：
 * added handler 增加 通知上线
 * read0 广播通知其他客户端
 * remove handler 移除 通知下线
 * 服务器将接受的信息 转发给其他各个客户端，
 * 客户端： 自己发的，返回自己
 * 其他客户端： 返回客户端名称
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-06 21:31
 */
public class NettyChatServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 保存已连接channel
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 请求处理 回调函数
     *
     * @param ctx 上下文，包含URI、方法类型等
     * @param msg 接收到请求对象
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 获取channel
        Channel channel = ctx.channel();
        // 从channelGroup(继承Set)中获取出已连接channel 判断是否为自己
        channelGroup.forEach(item -> {
            String sendMessage = null;
            // 判断客户端是不是自己发送的，不是自己则 返回远程地址
            if (item != channel) {
                sendMessage = String.format("[%s @ %s ] : %s \n",
                        item.remoteAddress(),
                        DateFormatUtils.format(new Date(), Constants.DEFAULT_DATETIME_PATTERN)
                        ,msg);
            }else{
                // 是自己则 返回自己
                sendMessage = String.format("[%s @ %s ] : %s \n",
                       "自己",
                        DateFormatUtils.format(new Date(), Constants.DEFAULT_DATETIME_PATTERN)
                        ,msg);
            }
            item.writeAndFlush(sendMessage);
        });
        System.out.println(String.format("[%s @ %s ] : %s \n",
                channel.remoteAddress(),
                DateFormatUtils.format(new Date(), Constants.DEFAULT_DATETIME_PATTERN)
                , msg));

    }

    /**
     * 连接成功 回调函数
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 通知已连接客户端
        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 已连接, 加入会话\n");
        // 保存已连接的channel
        channelGroup.add(channel);
    }

    /**
     * 连接断开 回调函数
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 通知已连接客户端
        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 断开连接, 已离开 \n");
        // 注意下面代码可以写、或者不写都是可以的，
        // Netty 会自动断开连接
        //channelGroup.remove(channel);
    }

    /**
     * channel 活动状态 回调函数
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 通知已连接客户端
        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 上线了 \n");
    }

    /**
     * channel 待使用状态 回调函数
     *
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 通知已连接客户端
        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 下线了 \n");
    }

    /**
     * 异常回调方法
     *
     * @param ctx   上下文
     * @param cause 异常
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
