package org.chen.architect.netty.protobuf.server.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.chen.architect.netty.protobuf.server.handler.GoogleProtobufHandler;
import org.chen.architect.netty.protobuf.server.model.DataInfo;

/**
 * @author chensj
 * @version v1.0
 * @classDesc
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-15 21:45
 */
public class GoogleProtobufInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        // protobuf 处理器
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        // 需要指定转换的参数类型  最外层类名称
        pipeline.addLast(new ProtobufDecoder(DataInfo.Person.getDefaultInstance()));

        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());

        pipeline.addLast(new GoogleProtobufHandler());

    }
}
