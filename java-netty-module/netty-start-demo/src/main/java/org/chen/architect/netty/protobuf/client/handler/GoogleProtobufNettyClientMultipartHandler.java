package org.chen.architect.netty.protobuf.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.chen.architect.netty.protobuf.client.model.DataInfo;
import org.chen.architect.netty.protobuf.client.model.MyDataInfo;

import java.util.Random;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-15 22:05
 */
public class GoogleProtobufNettyClientMultipartHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

        DataInfo.Person person = DataInfo.Person.newBuilder().setAge(25).setId(22).setAddress("NYK")
                .setName("张三").build();
        ctx.channel().writeAndFlush(person);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server[" + ctx.channel().remoteAddress() + "] channel 断开成功");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server[" + ctx.channel().remoteAddress() + "] handler 移除成功");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server[" + ctx.channel().remoteAddress() + "] channel 建立成功");
        MyDataInfo.MyMessage message = null;
        int random = new Random().nextInt(3);
        // 构建对象
        if (random == 0) {
            message = MyDataInfo.MyMessage.newBuilder()
                    // 指定数据类型
                    .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                    .setPerson(
                            MyDataInfo
                                    .Person
                                    .newBuilder()
                                    .setAge(25)
                                    .setId(22)
                                    .setAddress("NYK")
                                    .setName("张三")
                                    .build())
                    .build();

            ctx.channel().writeAndFlush(message);
        } else if (random == 1) {
            message = MyDataInfo.MyMessage.newBuilder()
                    // 指定数据类型
                    .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                    .setDog(
                            MyDataInfo
                                    .Dog
                                    .newBuilder()
                                    .setAge(3)
                                    .setId(23)
                                    .setName("小狗")
                                    .build())
                    .build();


        } else {
            message = MyDataInfo.MyMessage.newBuilder()
                    // 指定数据类型
                    .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                    .setCat(
                            MyDataInfo
                                    .Cat
                                    .newBuilder()
                                    .setId(24)
                                    .setName("小猫")
                                    .setCity("北京")
                                    .build())
                    .build();
        }


        // 从客户端发送给服务端
        ctx.channel().writeAndFlush(message);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server[" + ctx.channel().remoteAddress() + "] handler 添加成功");
    }
}
