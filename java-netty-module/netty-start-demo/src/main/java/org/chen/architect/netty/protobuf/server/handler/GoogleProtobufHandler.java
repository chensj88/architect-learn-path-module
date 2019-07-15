package org.chen.architect.netty.protobuf.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.chen.architect.netty.protobuf.server.model.DataInfo;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-15 21:49
 */
public class GoogleProtobufHandler extends SimpleChannelInboundHandler<DataInfo.Person> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Person msg) throws Exception {
        System.out.println(msg.getName());
        System.out.println(msg.getId());
        System.out.println(msg.getAddress());
        System.out.println(msg.getAge());
        DataInfo.Person person = DataInfo.Person.newBuilder().setAge(25).setId(23).setAddress("NYK")
                .setName("李四").build();
        ctx.channel().writeAndFlush(person);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client["+ctx.channel().remoteAddress() +"] channel 断开成功" );
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client["+ctx.channel().remoteAddress() +"] handler 移除成功" );
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client["+ctx.channel().remoteAddress() +"] channel 建立成功" );
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client["+ctx.channel().remoteAddress() +"] handler 添加成功" );
    }


}
