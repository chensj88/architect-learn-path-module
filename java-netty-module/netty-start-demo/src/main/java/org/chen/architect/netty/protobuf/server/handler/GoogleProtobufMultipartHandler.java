package org.chen.architect.netty.protobuf.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.chen.architect.netty.protobuf.server.model.MyDataInfo;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-15 23:16
 */
public class GoogleProtobufMultipartHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();

        if(dataType == MyDataInfo.MyMessage.DataType.PersonType){
            MyDataInfo.Person person = msg.getPerson();
            System.out.println(person.getName());
            System.out.println(person.getId());
            System.out.println(person.getAddress());
            System.out.println(person.getAge());
        }else if(dataType == MyDataInfo.MyMessage.DataType.DogType){
            MyDataInfo.Dog dog = msg.getDog();
            System.out.println(dog.getName());
            System.out.println(dog.getId());
            System.out.println(dog.getAge());
        }else{
            MyDataInfo.Cat cat = msg.getCat();
            System.out.println(cat.getName());
            System.out.println(cat.getId());
            System.out.println(cat.getCity());
        }
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
